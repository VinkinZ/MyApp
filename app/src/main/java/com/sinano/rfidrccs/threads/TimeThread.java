package com.sinano.rfidrccs.threads;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.ui.act.MainActivity;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/3
 * Description: 实时时间显示
 */
public class TimeThread extends Thread {
    private TextView timeText;
    private static final int Key = 0;

    public TimeThread(TextView textView) {
        this.timeText = textView;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                Thread.sleep(1000);
                Message msg = new Message();
                msg.what = Key;
                mHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Key:
                    long sysTime = System.currentTimeMillis();
                    Date date = new Date(sysTime);
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss EEE");
                    timeText.setText(sdf.format(date));
                    break;
                default:
                    break;
            }
        }
    };
}
