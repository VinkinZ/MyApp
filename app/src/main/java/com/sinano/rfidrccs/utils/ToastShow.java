package com.sinano.rfidrccs.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * [Toast工具类]
 * Created by Vinkin on 2020/5/17
 */

public class ToastShow {
    private static Toast toast = null;

    /**
     * 显示提示 toast
     * @param msg 提示信息，包括线程中调用可能出现的异常处理
     */
    @SuppressLint("ShowToast")
    public static void showToast(Context context, String msg) {
        try {
            if (null == toast) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            // 解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    /**
     * 显示提示 toast
     * @param msg 短提示信息
     */
    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示提示 toast
     * @param msg 长提示信息
     */
    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg,Toast.LENGTH_LONG).show();
    }
}
