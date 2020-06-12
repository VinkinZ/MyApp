package com.sinano.rfidrccs.ui.act;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.business.DetailQuery;
import com.sinano.rfidrccs.business.RFIDScanWeigh;

import java.lang.ref.WeakReference;
import java.util.Timer;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/19
 * Description: 主界面
 */
public class MainActivity extends BaseActivity {
    private ImageButton btnSjcx, btnHjxx, btnFxcx, btnRygl,  btnSysc, btnGlyzy, btnCz, btnExp;
    private ImageView imgFan;
    private Switch switchFan;
    public static String controllerCode;
    public static boolean sendEnabled;
    public static Timer tTakeNoWeigh1, tTakeNoWeigh2;
    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btnSjcx = findView(R.id.btnSJCX);
        btnSjcx.setBackgroundResource(R.drawable.btnsjcx_bg);
        btnHjxx = findView(R.id.btnHJXX);
        btnHjxx.setBackgroundResource(R.drawable.btnhjxx_bg);
        btnFxcx = findView(R.id.btnFXCX);
        btnFxcx.setBackgroundResource(R.drawable.btnfxcx_bg);
        btnRygl = findView(R.id.btnRYGL);
        btnRygl.setBackgroundResource(R.drawable.btnrygl_bg);
        btnSysc = findView(R.id.btnSYSC);
        btnSysc.setBackgroundResource(R.drawable.btnsysc_bg);
        btnGlyzy = findView(R.id.btnGLYZY);
        btnGlyzy.setBackgroundResource(R.drawable.btnglyzy_bg);
        imgFan =findView(R.id.imgFan);
        imgFan.setBackgroundResource(R.drawable.fan);
        switchFan = findView(R.id.switchFan);
        btnCz = findView(R.id.btnCZ);
        btnCz.setBackgroundResource(R.drawable.btncz_bg);
        btnExp = findView(R.id.btnExp);
        btnExp.setBackgroundResource(R.drawable.btnexp_bg);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void doBusiness() {
        btnSjcx.setOnClickListener(new DetailQuery(MainActivity.this, "试剂查询", ReagentActivity.class));
        btnHjxx.setOnClickListener(new DetailQuery(MainActivity.this, "查看柜体环境信息", EnvActivity.class));
        btnRygl.setOnClickListener(new DetailQuery(MainActivity.this, "查看人员列表", PersonActivity.class));
        btnFxcx.setOnClickListener(new DetailQuery(MainActivity.this, "风险查询", RiskActivity.class));
        btnSysc.setOnClickListener(new DetailQuery(MainActivity.this, "查看使用手册", GuidanceActivity.class));
        btnGlyzy.setOnClickListener(new DetailQuery(MainActivity.this, "管理员专用", AdmActivity.class, true));

        btnCz.setOnClickListener(new RFIDScanWeigh());
    }

    private static class MainHandler extends Handler {
        private final WeakReference<MainActivity> mActivty;
        private Class<BaseActivity> clz;

        public MainHandler(MainActivity activity, Class<BaseActivity> clz){
            mActivty = new WeakReference<>(activity);
            this.clz = clz;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity mainActivity = mActivty.get();
            if (null != mainActivity) {
                switch (msg.what) {
                    case 0x10:
                        Intent intent = new Intent(mainActivity, clz);
                        mainActivity.startActivity(intent);
                        break;
                        //风机开关
                }
            }
        }
    }

}
