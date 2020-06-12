package com.sinano.rfidrccs.ui.frag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseCabFragment;
import com.sinano.rfidrccs.business.AccessReagent;
import com.sinano.rfidrccs.business.AccessReagentToxic;
import com.sinano.rfidrccs.business.RFIDScan;
import com.sinano.rfidrccs.comm.Cmd;
import com.sinano.rfidrccs.comm.SerialPortSTM32;
import com.sinano.rfidrccs.utils.DialogShow;
import com.sinano.rfidrccs.utils.VoicePlayer;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/3
 * Description: 取还试剂、试剂详情查询在父类中完成
 */
public class CabFrag1 extends BaseCabFragment {
    private Handler handler;
    public static boolean doorOpened;

    public CabFrag1() {
        super.cabOrderNum = 1;
        System.out.println("1号试剂柜类型：" + cabType);
    }


    @Override
    protected void initData() {

    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void doBusiness() {
        super.doBusiness();
        // 由handler主导的UI变化
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x10:
                        btnOpenDoor.setBackgroundResource(R.drawable.icon_opened);  // 更改图标为开门
                        SerialPortSTM32.sendOrder(Cmd.OPEN_DOOR_1);  // 发送开门指令
                        EventBus.getDefault().post("change_opt", "opt");  // 更改操作提示
                        // 循环开门，直到门开
                        while (true) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (doorOpened) {
                                doorOpened = false;
                                break;
                            } else {
                                SerialPortSTM32.sendOrder(Cmd.OPEN_DOOR_1);
                            }
                        }
                        EventBus.getDefault().post("notEnable", "door_disabled");  // 开门按键、柜体按键、管理员专用按键不使能
                        break;
                    case 0x11:
                        btnOpenDoor.setBackgroundResource(R.drawable.btnopen_bg);  // 更改图标为关门
                        break;
                    case 0x12:
                        // 判断柜体类型，取还试剂流程
                        if (null != cabType) {
                            switch (cabType) {
                                case "有毒称重型":
                                    DialogShow dialogShow = new DialogShow(CabFrag1.this.getActivity());
                                    dialogShow.showToxicOptDialog(handler);
                                    break;
                                case "有毒不称重型":
                                    btnOpenDoor.setOnClickListener(new AccessReagentToxic(CabFrag1.this.getActivity(), handler));
                                    break;
                                default:
                                    btnOpenDoor.setOnClickListener(new AccessReagent(CabFrag1.this.getActivity(), handler));
                                    break;
                            }
                        }
                    case 0x13:
                        VoicePlayer.playVoice(CabFrag1.this.getActivity(), R.raw.door_not_close_warning);  // 未及时关门
                }
            }
        };


        // 取还试剂
        if (null != cabType) {
            switch (cabType) {
                case "有毒称重型":
                    DialogShow dialogShow = new DialogShow(CabFrag1.this.getActivity());
                    dialogShow.showToxicOptDialog(handler);
                    break;
                case "有毒不称重型":
                    btnOpenDoor.setOnClickListener(new AccessReagentToxic(CabFrag1.this.getActivity(), handler));
                    break;
                default:
                    btnOpenDoor.setOnClickListener(new AccessReagent(CabFrag1.this.getActivity(), handler));
                    break;
            }
        }

    }

    // 收到关门信号，开始扫描标签，SerialPortSTM32中传过来
    @Subscriber(tag = "get_rfid_1",mode = ThreadMode.MAIN)
    public void getRfid(String string){
        handler.sendEmptyMessage(0x11);
        if (!cabType.equals("普通型")) {
            rfidScan.scan();
        }
    }
}
