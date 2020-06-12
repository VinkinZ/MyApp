package com.sinano.rfidrccs.business;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.myapplication.R;
import com.sinano.rfidrccs.comm.Cmd;
import com.sinano.rfidrccs.comm.SerialPortIC;
import com.sinano.rfidrccs.db.HelperTable;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.threads.GetCardNumThread;
import com.sinano.rfidrccs.ui.act.MainActivity;
import com.sinano.rfidrccs.utils.DialogShow;
import com.sinano.rfidrccs.utils.StaticVariable;
import com.sinano.rfidrccs.utils.VoicePlayer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/3
 * Description: 取还有毒称重试剂
 */
public class AccessReagentToxicWeigh {
    private Context context;
    private Handler handler;
    private Thread thread1, thread2;
    private DialogShow dialogShow;
    public static Timer tReturnNoWeigh;

    public AccessReagentToxicWeigh(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    /**
     * 点击取用，双刷卡，取完称重（试剂称重按键）
     */
    public void onQuYong() {

        @SuppressLint("HandlerLeak")
        Handler thisHandler = new Handler() {
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
                        if (null != thread1 && !thread1.isInterrupted()) {
                            thread1.interrupt();
                        }
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
        dialogShow.showDialog("取用有毒试剂", "请刷卡\n当前试剂柜单次最大取用量：1\n请于取用当天" +
                        tHelper.queryInHelper(Item.CHECK_TIME) + "前归还试剂", "取消", thisHandler);
        // 开启线程
        GetCardNumThread getCardNumThread1 = new GetCardNumThread(thisHandler, 1);
        thread1 = new Thread(getCardNumThread1, "获取卡号线程");
        thread1.start();
        // 线程等待
        GetCardNumThread getCardNumThread2 = new GetCardNumThread(thisHandler, 2);
        thread2 = new Thread(getCardNumThread2, "获取管理员或安全员卡号线程");
    }

    /**
     * 点击归还，刷卡
     */
    public void weighReagent() {

        @SuppressLint("HandlerLeak")
        Handler thisHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    // 用户点击取消事件或超时未刷卡
                    case 0x00:
                        dialogShow.closeOneSectDialog();
                        GetCardNumThread.interrupt(thread1);
                        break;
                    // 用户刷卡成功事件
                    case 0x01:
                        dialogShow.closeOneSectDialog();
                        VoicePlayer.playVoice(context, R.raw.card_successful_and_weigh);
                        dialogShow.showWeighingDialog();  // 得到重量后关闭，由SerialPortWeigh--->MainActivity
                        SerialPortIC.sendSerialPortIC(Cmd.IC_OK);
                        // 称重40秒计时
                        tReturnNoWeigh = new Timer();
                        tReturnNoWeigh.schedule(new TimerTask() {
                            public void run() {
                                dialogShow.closeOneSectDialog();
                                if (StaticVariable.optType.equals("huan") &&
                                    (StaticVariable.reagentWeight <= 15 || StaticVariable.weighRfid.length() == 0)) {
                                    VoicePlayer.playVoice(context, R.raw.not_weigh_in_time);
                                }
                                tReturnNoWeigh.cancel();
                            }
                        }, 1000 * 40);
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
        dialogShow = new DialogShow(context);
        dialogShow.showDialog("归还有毒试剂", "请刷卡", "取消", thisHandler);
        // 开启线程
        GetCardNumThread getCardNumThread1 = new GetCardNumThread(thisHandler, 1);
        thread1 = new Thread(getCardNumThread1, "获取卡号线程");
        thread1.start();
    }

    public void admOpenDoor() {
        @SuppressLint("HandlerLeak")
        Handler thisHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }
}
