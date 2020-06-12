package com.sinano.rfidrccs.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.db.SQLiteManager;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/18
 * Description: 屏保
 */
public class ScreenSaverActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void doBusiness() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                SQLiteManager.getInstance().open();
                $Log("数据库已打开，可使用！");
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

}
