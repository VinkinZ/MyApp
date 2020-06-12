package com.sinano.rfidrccs.business;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.myapplication.R;
import com.sinano.rfidrccs.comm.Cmd;
import com.sinano.rfidrccs.comm.SerialPortSTM32;
import com.sinano.rfidrccs.threads.GetWeighRFIDThread;
import com.sinano.rfidrccs.utils.DialogShow;
import com.sinano.rfidrccs.utils.StaticVariable;
import com.sinano.rfidrccs.utils.VoicePlayer;

import org.simple.eventbus.EventBus;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/4 0004
 * Description: 称重RFID，点击试剂称重后执行
 */
public class RFIDScanWeigh implements View.OnClickListener {
    private DialogShow dialogShow;
    private Context context;
    private Thread thread;

    public RFIDScanWeigh(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        // 按键使能与不使能
        if (getClass().getSimpleName().equals("AdmActivity1")) {
            EventBus.getDefault().post("update","adm1_btncz_disable");
            EventBus.getDefault().post("update","adm1_btndoor_disable");
            StaticVariable.optType = "ruku";
        } else if (getClass().getSimpleName().equals("MainActivity")) {
            EventBus.getDefault().post("update","main_btncz_disable");
            EventBus.getDefault().post("update","main_btndoor_disable");
        }
        SerialPortSTM32.sendOrder(Cmd.GET_WEIGH_RFID);  // 称重RFID扫描

        @SuppressLint("HandlerLeak")
        final Handler thisHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x00:
                        GetWeighRFIDThread.interrupt(thread);
                        dialogShow = new DialogShow(context);
                        dialogShow.showDialog("警告", "称重有误，请重新操作", "我知道了");
                        VoicePlayer.playVoice(context, R.raw.weigh_wrong);
                        EventBus.getDefault().post("warning","main_entry_warning");
                        EventBus.getDefault().post("update","main_btncz_disable");
                        EventBus.getDefault().post("update","main_btndoor_enable");
                        EventBus.getDefault().post("update","main_opt");
                        break;
                    case 0x01:
                        dialogShow = new DialogShow(context);
                        dialogShow.showDialog("警告", "取出试剂与称重试剂不匹配\n请归还后重新操作", "我知道了");
                        VoicePlayer.playVoice(context, R.raw.weigh_not_match_retry);
                        break;
                }
            }
        };

        // 播放语音
        VoicePlayer.playVoice(context, R.raw.scaning_weigh_rfid);
        // 开启线程
        GetWeighRFIDThread getWeighRFIDThread = new GetWeighRFIDThread(context, thisHandler);
        thread = new Thread(getWeighRFIDThread, "获取称重试剂标签");
        thread.start();
    }
}
