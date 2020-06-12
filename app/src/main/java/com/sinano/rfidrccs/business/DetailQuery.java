package com.sinano.rfidrccs.business;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.comm.Cmd;
import com.sinano.rfidrccs.comm.SerialPortIC;
import com.sinano.rfidrccs.threads.GetCardNumThread;
import com.sinano.rfidrccs.ui.act.MainActivity;
import com.sinano.rfidrccs.utils.DialogShow;
import com.sinano.rfidrccs.utils.PageJump;
import com.sinano.rfidrccs.utils.ToastShow;
import com.sinano.rfidrccs.utils.VoicePlayer;

import org.simple.eventbus.EventBus;

import java.lang.ref.WeakReference;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/3
 * Description: 查看详情，界面跳转
 */
public class DetailQuery implements View.OnClickListener {
    private Context context;
    private String string;
    private Class<?> cls;
    private boolean isAdm = false;
    private Bundle bundle = null;
    private DialogShow dialogShow;
    private Thread thread;
    private Handler handler;

    public DetailQuery(Context context, String string, Class<?> cls, Handler handler, boolean isAdm) {
        this.context = context;
        this.handler = handler;
        this.string = string;
        this.cls = cls;
        this.isAdm = isAdm;
    }


    public DetailQuery(Context context, String string, Class<?> cls) {
        this.context = context;
        this.string = string;
        this.cls = cls;
    }

    public DetailQuery(Context context, String string, Class<?> cls, boolean isAdm) {
        this.context = context;
        this.string = string;
        this.cls = cls;
        this.isAdm = isAdm;
    }

    public DetailQuery(Context context, String string, Class<?> cls, Bundle bundle) {
        this.context = context;
        this.string = string;
        this.cls = cls;
        this.bundle = bundle;
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
                    // 用户刷卡成功事件(测试不用handler实现页面跳转)
                    case 0x01:
                        VoicePlayer.playVoice(context, R.raw.card_successful);
                        dialogShow.closeOneSectDialog();
                        SerialPortIC.sendSerialPortIC(Cmd.IC_OK);
                        if (null != bundle) {
                            PageJump.pageJump((Activity) context, cls, bundle);
                        } else {
                            PageJump.pageJump((Activity) context, cls);
                        }
                        /*if (MainActivity.sendEnabled) {
                            handler.sendEmptyMessage(0x00);
                        } else {
                            try {
                                Thread.sleep(1200);
                                handler.sendEmptyMessage(0x05);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }*/
                        GetCardNumThread.interrupt(thread);
                        break;
                    // 用户分值为0
                    case 0x02:
                        VoicePlayer.playVoice(context, R.raw.card_failed_for_zero_score);
                        dialogShow.closeOneSectDialog();
                        SerialPortIC.sendSerialPortIC(Cmd.IC_NOTOK);
                        GetCardNumThread.interrupt(thread);
                        break;
                    // 用户无权限
                    case 0x03:
                        VoicePlayer.playVoice(context, R.raw.card_failed_for_no_authority);
                        dialogShow.closeOneSectDialog();
                        SerialPortIC.sendSerialPortIC(Cmd.IC_NOTOK);
                        GetCardNumThread.interrupt(thread);
                        break;
                    default:
                        break;
                }
            }
        };


        // 语音
        VoicePlayer.playVoice(context, R.raw.card);
        // 弹窗
        dialogShow = new DialogShow(context);
        dialogShow.showDialog(string, "请刷卡");
        // 开启线程
        if (isAdm) {
            GetCardNumThread getCardNumThread = new GetCardNumThread(thisHandler, 3);
            thread = new Thread(getCardNumThread, "获取管理员卡号线程");
            thread.start();
        } else {
            GetCardNumThread getCardNumThread = new GetCardNumThread(thisHandler, 1);
            thread = new Thread(getCardNumThread, "获取卡号线程");
            thread.start();
        }
    }
}


