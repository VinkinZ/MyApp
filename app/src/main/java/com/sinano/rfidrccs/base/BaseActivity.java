package com.sinano.rfidrccs.base;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.sinano.rfidrccs.ui.act.AdmActivity;
import com.sinano.rfidrccs.ui.act.AdmActivity1;
import com.sinano.rfidrccs.ui.act.AdmActivity3;
import com.sinano.rfidrccs.ui.act.AdmActivity6;
import com.sinano.rfidrccs.ui.act.AdmActivity7;
import com.sinano.rfidrccs.ui.act.MainActivity;
import com.sinano.rfidrccs.ui.act.ScreenSaverActivity;
import com.sinano.rfidrccs.ui.act.SplashActivity;
import com.sinano.rfidrccs.ui.act.ReagentActivity;
import com.sinano.rfidrccs.ui.act.ReagentActivity1;
import com.sinano.rfidrccs.ui.act.ReagentActivity2;
import com.sinano.rfidrccs.ui.act.ReagentActivity3;
import com.sinano.rfidrccs.threads.TimeThread;
import com.sinano.rfidrccs.utils.ActivityCollector;
import com.sinano.rfidrccs.utils.AppUtil;
import com.sinano.rfidrccs.utils.CountTimer;

/**
 * [Activity基本类]
 * Created by Vinkin on 2020/5/15
 */

public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private String APP_NAME;
    private boolean isDebug;
    private Toast toast;
    private Context context;
    private CountTimer countTimer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        $Log(TAG + "-->onCreate()");
        ActivityCollector.addActivity(this);
        context = this;
        isDebug = true;
        APP_NAME = AppUtil.getAppName(context);
        setContentView(initLayout());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initCountTimer();
        initView();
        initData();
        doBusiness();
    }

    /**
     * 日志信息打印
     * @param msg 日志信息
     */
    public void $Log(String msg) {
        if (isDebug) {
            Log.d(APP_NAME, msg);
        }
    }

    /**
     * 显示提示 toast
     * @param msg 提示信息，包括线程中调用可能出现的异常处理
     */
    @SuppressLint("ShowToast")
    public void showToast(String msg) {
        try {
            if (null == toast) {
                toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            // 处理在子线程中调用Toast的异常
            Looper.prepare();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    /**
     * 显示提示 toast
     * @param msg 短提示信息
     */
    public void showShortToast(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示提示 toast
     * @param msg 长提示信息
     */
    public void showLongToast(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
    }

    /**
     * 封装findViewById
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findView(int resid) {
        return (T) super.findViewById(resid);
    }

    /**
     * 自定义字体，需要下载字体资源
     * @param tv 目标
     * @param font 字体
     */
    public void setFont(TextView tv, String font) {
        Typeface tf = Typeface.createFromAsset(this.getAssets(), "fonts/" + font + ".ttf");
        tv.setTypeface(tf);
    }

    /**
     * 初始化布局
     * @return 布局id
     */
    protected abstract int initLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 设置数据
     */
    protected abstract void initData();

    /**
     * 业务
     */
    protected abstract void doBusiness();

    /**
     * 页面加载
     * @param layoutResID layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        if (!context.getClass().equals(ScreenSaverActivity.class) && !context.getClass().equals(SplashActivity.class) ) {
            TextView tvTime = findView(R.id.tvTime);
            new TimeThread(tvTime).start();
        }

        if (context.getClass().equals(AdmActivity1.class) || context.getClass().equals(AdmActivity3.class) ||
            context.getClass().equals(AdmActivity6.class) || context.getClass().equals(AdmActivity7.class)) {

            ImageView imgslt = findViewById(R.id.imgSLT);
            imgslt.setBackgroundResource(R.drawable.img_slt);
            ImageView imgsd = findViewById(R.id.imgSD);
            imgsd.setBackgroundResource(R.drawable.img_sd);

            ImageButton btna2a = findView(R.id.btnA2A);
            btna2a.setBackgroundResource(R.drawable.btnback_bg);
            pageJump(btna2a, AdmActivity.class);
        }

        if (context.getClass().equals(ReagentActivity1.class) || context.getClass().equals(ReagentActivity2.class) ||
            context.getClass().equals(ReagentActivity3.class)) {
            ImageButton btnR2r = findView(R.id.btnR2R);
            btnR2r.setBackgroundResource(R.drawable.btnback_bg);
            pageJump(btnR2r, ReagentActivity.class);
        }
    }

    /**
     * 页面加载
     * @param view view
     */
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    /**
     * 页面跳转
     * @param view 按键
     * @param cls 跳转页
     */
    public void pageJump(View view, final Class<?> cls) {
        try {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, cls);
                    context.startActivity(intent);
                    /*if (!context.getClass().equals(MainActivity.class)) {
                        finish();
                    }*/
                }
            });
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    public abstract class onSingleClickListener implements View.OnClickListener {
        // 两次点击按钮之间的间隔，目前为1000ms
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View view) {
            if ((System.currentTimeMillis() - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = System.currentTimeMillis();
                onSingleClick(view);
            }
        }
    }

    /**
     * 同一按钮在短时间内可重复响应点击事件
     */
    public abstract class onMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }

    /**
     * 初始化CountTimer，设置操作界面倒计时为5分钟，屏保倒计时为10分钟
     */
    public void initCountTimer() {
        if (context.getClass().equals(ScreenSaverActivity.class) || context.getClass().equals(SplashActivity.class)) {
            countTimer = null;
        } else if (context.getClass().equals(MainActivity.class)) {
            countTimer = new CountTimer(1000 * 60 * 10, 1000, this);
        } else {
            countTimer = new CountTimer(1000 * 60 * 5, 1000, this);
        }
    }

    /**
     * 开始计时
     */
    public void timeStart(){
        if (null != countTimer) {
            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    countTimer.start();
                }
            });
        }
    }

    /**
     * 主要的方法，重写dispatchTouchEvent
     * @param ev 事件
     * @return true of false
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (null != countTimer) {
            switch (ev.getAction()){
                case MotionEvent.ACTION_UP:
                    countTimer.start();
                    break;
                default:
                    countTimer.cancel();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 页面恢复
     */
    @Override
    protected void onResume() {
        timeStart();
        super.onResume();
        $Log(TAG + "--->onResume()");
    }

    /**
     * 页面挂起
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (null != countTimer){
            countTimer.cancel();
        }
        $Log(TAG + "--->onPause()");
    }

    /**
     * 页面销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != countTimer) {
            countTimer.cancel();
        }
        $Log(TAG + "--->onDestroy()");
        // activity管理
        ActivityCollector.removeActivity(this);
    }
}

