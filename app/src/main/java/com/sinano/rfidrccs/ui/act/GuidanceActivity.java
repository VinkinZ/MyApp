package com.sinano.rfidrccs.ui.act;

import android.widget.ImageButton;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/19
 * Description: 使用手册
 */
public class GuidanceActivity extends BaseActivity {
    private ImageButton btnG2m;

    @Override
    protected int initLayout() {
        return R.layout.activity_guidance;
    }

    @Override
    protected void initView() {
        btnG2m = findView(R.id.btnG2M);
        btnG2m.setBackgroundResource(R.drawable.btn2main_bg);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void doBusiness() {
        pageJump(btnG2m, MainActivity.class);
    }
}
