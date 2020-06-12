package com.sinano.rfidrccs.ui.act;

import android.widget.ImageButton;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/18
 * Description: 风险查询
 */
public class RiskActivity extends BaseActivity {
    private ImageButton btnR2m;

    @Override
    protected int initLayout() {
        return R.layout.activity_risk;
    }

    @Override
    protected void initView() {
        btnR2m = findView(R.id.btnR2M);
        btnR2m.setBackgroundResource(R.drawable.btn2main_bg);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void doBusiness() {
        pageJump(btnR2m, MainActivity.class);
    }
}
