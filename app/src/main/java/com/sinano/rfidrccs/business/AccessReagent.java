package com.sinano.rfidrccs.business;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.myapplication.R;
import com.sinano.rfidrccs.comm.Cmd;
import com.sinano.rfidrccs.comm.SerialPortIC;
import com.sinano.rfidrccs.db.HelperTable;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.threads.GetCardNumThread;
import com.sinano.rfidrccs.ui.act.MainActivity;
import com.sinano.rfidrccs.utils.DialogShow;
import com.sinano.rfidrccs.utils.VoicePlayer;

import org.simple.eventbus.EventBus;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/3
 * Description: 取还普通试剂
 */
public class AccessReagent implements View.OnClickListener{
    private Context context;
    private Handler handler;
    private Thread thread;
    private DialogShow dialogShow;


    public AccessReagent(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void onClick(View v) {

        @SuppressLint("HandlerLeak")
        final Handler thisHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    // 用户点击取消事件或超时未刷卡
                    case 0x00:
                        dialogShow.closeOneSectDialog();
                        GetCardNumThread.interrupt(thread);
                        break;
                    // 用户刷卡成功事件
                    case 0x01:
                        VoicePlayer.playVoice(context, R.raw.card_successful_door_open);
                        dialogShow.closeOneSectDialog();
                        SerialPortIC.sendSerialPortIC(Cmd.IC_OK);
                        EventBus.getDefault().post("update", "main_opt");
                        if (MainActivity.sendEnabled) {
                            handler.sendEmptyMessage(0x10);
                        } else {
                            try {
                                Thread.sleep(1200);
                                handler.sendEmptyMessage(0x10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    // 用户分值为0
                    case 0x02:
                        VoicePlayer.playVoice(context, R.raw.card_failed_for_zero_score);
                        dialogShow.closeOneSectDialog();
                        SerialPortIC.sendSerialPortIC(Cmd.IC_NOTOK);
                        break;
                    // 用户无权限
                    case 0x03:
                        VoicePlayer.playVoice(context, R.raw.card_failed_for_no_authority);
                        dialogShow.closeOneSectDialog();
                        SerialPortIC.sendSerialPortIC(Cmd.IC_NOTOK);
                        break;
                    default:
                        break;
                }
            }
        };

        // 刷卡语音
        VoicePlayer.playVoice(context, R.raw.card);
        // 刷卡弹窗
        HelperTable tHelper = new HelperTable();
        dialogShow = new DialogShow(context);
        dialogShow.showDialog("正在存取试剂", "请刷卡\n当前试剂柜单次最大取用量：" +
                                tHelper.queryInHelper(Item.MAX_AMOUNT) + "\n请于取用当天" +
                                tHelper.queryInHelper(Item.CHECK_TIME) + "前归还试剂",
                                "取消", thisHandler);
        // 开启线程
        GetCardNumThread getCardNumThread = new GetCardNumThread(thisHandler, 1);
        thread = new Thread(getCardNumThread, "获取卡号线程");
        thread.start();
    }
}
