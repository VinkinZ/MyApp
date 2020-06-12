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

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/3 0003
 * Description: 取还有毒不称重试剂，双刷卡
 */
public class AccessReagentToxic implements View.OnClickListener {
    private Context context;
    private Handler handler;
    private Thread thread1,thread2;
    private DialogShow dialogShow;

    public AccessReagentToxic(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    /**
     * 有毒不称重双刷卡
     * @param v 按键
     */
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
                        GetCardNumThread.interrupt(thread1);
                        GetCardNumThread.interrupt(thread2);
                        break;
                    // 用户刷卡成功事件
                    case 0x01:
                        VoicePlayer.playVoice(context, R.raw.card_successful_door_open);
                        dialogShow.closeOneSectDialog();
                        SerialPortIC.sendSerialPortIC(Cmd.IC_OK);
                        RFIDScan.isToxic = true;
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
                    // 刷第二张卡
                    case 0x04:
                        VoicePlayer.playVoice(context, R.raw.card_adm);
                        SerialPortIC.sendSerialPortIC(Cmd.IC_OK);
                        GetCardNumThread.interrupt(thread1);
                        thread2.start();
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
        dialogShow.showDialog("正在存取试剂", "请刷卡\n当前试剂柜单次最大取用量：1\n请于取用当天" +
                tHelper.queryInHelper(Item.CHECK_TIME) + "前归还试剂", "取消", thisHandler);
        // 开启线程
        GetCardNumThread getCardNumThread1 = new GetCardNumThread(thisHandler, 1);
        thread1 = new Thread(getCardNumThread1, "获取卡号线程");
        thread1.start();
        // 线程等待
        GetCardNumThread getCardNumThread2 = new GetCardNumThread(thisHandler, 2);
        thread2 = new Thread(getCardNumThread2, "获取管理员或安全员卡号线程");
    }
}
