package com.sinano.rfidrccs.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30 15:07
 * Description: 弹窗基类
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        setCanceledOnTouchOutside(false);

        initView();
        initData();
        initEvent();
        new DialogDownTimer().start();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    // 继承CountDownTimer类
    class DialogDownTimer extends CountDownTimer {
        DialogDownTimer() {
            // 设置时间40秒
            super(1000 * 40, 1000);
        }

        // 重写CountDownTimer的两个方法
        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            BaseDialog.this.dismiss();
        }
    }
}
