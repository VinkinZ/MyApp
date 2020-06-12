package com.sinano.rfidrccs.business;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.myapplication.R;
import com.sinano.rfidrccs.comm.Cmd;
import com.sinano.rfidrccs.comm.SerialPortSTM32;
import com.sinano.rfidrccs.threads.GetScrapRFIDThread;
import com.sinano.rfidrccs.threads.GetWeighRFIDThread;
import com.sinano.rfidrccs.utils.DialogShow;
import com.sinano.rfidrccs.utils.VoicePlayer;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/9
 * Description: 报废RFID，点击试剂报废后执行
 */
public class RFIDScanScrap implements View.OnClickListener {
    private DialogShow dialogShow;
    private Context context;
    private Thread thread;

    public RFIDScanScrap(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        SerialPortSTM32.sendOrder(Cmd.GET_WEIGH_RFID);
        @SuppressLint("HandlerLeak")
        final Handler thisHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x00) {
                    dialogShow = new DialogShow(context);
                    dialogShow.showDialog("警告", "未读到报废试剂标签\n请重新执行报废操作", "我知道了");
                    VoicePlayer.playVoice(context, R.raw.scrap_null);
                    thread.interrupt();
                }
            }
        };

        // 播放语音
        VoicePlayer.playVoice(context, R.raw.scaning_scrap_rfid);
        // 开启线程
        GetScrapRFIDThread getScrapRFIDThread = new GetScrapRFIDThread(context, thisHandler);
        thread = new Thread(getScrapRFIDThread, "获取报废试剂标签");
        thread.start();
    }

}
