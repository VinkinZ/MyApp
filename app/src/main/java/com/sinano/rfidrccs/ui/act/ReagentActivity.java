package com.sinano.rfidrccs.ui.act;

import android.widget.ImageButton;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/19
 * Description: 试剂查询
 */
public class ReagentActivity extends BaseActivity {
    private ImageButton btnR2m, btnWrk, btnYrk, btnYbf;

    @Override
    protected int initLayout() {
        return R.layout.activity_reagent;
    }

    @Override
    protected void initView() {
        btnR2m = findView(R.id.btnR2M);
        btnR2m.setBackgroundResource(R.drawable.btn2main_bg);
        btnWrk = findView(R.id.btnWRK);
        btnWrk.setBackgroundResource(R.drawable.btnsd_bg);
        btnYrk = findView(R.id.btnYRK);
        btnYrk.setBackgroundResource(R.drawable.btnsd_bg);
        btnYbf = findView(R.id.btnYBF);
        btnYbf.setBackgroundResource(R.drawable.btnsd_bg);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void doBusiness() {
        pageJump(btnR2m, MainActivity.class);
        pageJump(btnWrk, ReagentActivity1.class);
        pageJump(btnYrk, ReagentActivity2.class);
        pageJump(btnYbf, ReagentActivity3.class);
    }
}
