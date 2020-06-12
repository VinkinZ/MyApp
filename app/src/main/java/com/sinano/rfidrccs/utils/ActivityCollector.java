package com.sinano.rfidrccs.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * [Activity收集工具类]
 * Created by Vinkin on 2020/5/15
 */

public class ActivityCollector {
    public static List<Activity> activitys = new ArrayList<Activity>();

    /**
     * 向List中添加活动
     * @param activity activity
     */
    public static void addActivity(Activity activity) {
        activitys.add(activity);
        System.out.println("ActivityCollector：" + activitys.size());
    }

    /**
     * 从List中移除活动
     * @param activity activity
     */
    public static void removeActivity(Activity activity) {
        activitys.remove(activity);
        System.out.println("ActivityCollector：" + activitys.size());
    }

    /**
     * 将List中存储的活动全部销毁掉
     */
    private static void finishAll() {
        for (Activity activity : activitys) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应用程序
     */
    public static void appExit(Context context) {
        try {
            ActivityCollector.finishAll();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception ignored) {}
    }
}
