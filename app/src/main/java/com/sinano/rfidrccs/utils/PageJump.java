package com.sinano.rfidrccs.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * [页面跳转工具类]
 * Created by Vinkin on 2020/5/15
 */

public class PageJump {

    /**
     * startActivity
     * @param activity 当前页
     * @param cls 跳转页
     */
    public static void pageJump(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }


    public static void pageJump(Activity activity, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * startActivity
     * @param view 按键
     * @param activity 当前页
     * @param cls 跳转页
     */
    public static void pageJump(View view, final Activity activity, final Class<?> cls) {
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, cls);
                    activity.startActivity(intent);
                }
            });
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * startActivity
     * @param view 按键
     * @param activity 当前页
     * @param cls 跳转页
     * @param bundle 绑定的数据
     */
    public static void pageJump(View view, final Activity activity, final Class<?> cls, final Bundle bundle) {
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, cls);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            });
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * startActivityForResult
     * @param view 按键
     * @param activity 当前页
     * @param cls 跳转页
     * @param bundle 绑定的数据
     * @param requestCode 请求码
     */
    public static void pageJumpToForResult(View view, final Activity activity, final Class<?> cls,
                                         final Bundle bundle, final int requestCode) {
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, cls);
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent,requestCode);
                }
            });
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * startActivityForResult
     * @param activity 按键
     * @param cls 当前页
     * @param requestCode 请求码
     */
    public static void pageJumpToForResult(View view, final Activity activity, final Class<?> cls, final int requestCode) {
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, cls);
                    activity.startActivityForResult(intent,requestCode);
                }
            });
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}