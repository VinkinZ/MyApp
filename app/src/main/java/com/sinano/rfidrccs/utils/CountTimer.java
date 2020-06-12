package com.sinano.rfidrccs.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

import com.sinano.rfidrccs.ui.act.MainActivity;
import com.sinano.rfidrccs.ui.act.ScreenSaverActivity;
import com.sinano.rfidrccs.db.SQLiteManager;

public class CountTimer extends CountDownTimer {
    private static final String TAG = "CountTimer";
    private Context context;

    /**
     * 构造方法
     * @param millisInFuture 计时时间
     * @param countDownInterval 计时间隔
     * @param context context
     */
    public CountTimer(long millisInFuture, long countDownInterval, Context context) {
        super(millisInFuture, countDownInterval);
        this.context = context;
    }

    /**
     * 计时结束
     */
    @Override
    public void onFinish() {
        if (context instanceof Activity) {
            if (context.getClass().equals(MainActivity.class)) {
                SQLiteManager.getInstance().close();
                Log.i(TAG, "数据库已关闭，不可用！ ");
                context.startActivity(new Intent(context, ScreenSaverActivity.class));
            } else {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        }
    }

    /**
     * 计时过程
     */
    @Override
    public void onTick(long millisUntilFinished) {
        String TAG = context.getClass().getSimpleName();
        Log.i(TAG, "onTick ---> millisUntilFinished" + millisUntilFinished / 1000);
    }
}

