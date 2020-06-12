package com.sinano.rfidrccs.ui.act;

import android.widget.ImageButton;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/19
 * Description: 环境信息查询
 */
public class EnvActivity extends BaseActivity {
    private ImageButton btnE2m;

    @Override
    protected int initLayout() {
        return R.layout.activity_env;
    }

    @Override
    protected void initView() {
        btnE2m = findView(R.id.btnE2M);
        btnE2m.setBackgroundResource(R.drawable.btn2main_bg);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void doBusiness() {
        pageJump(btnE2m, MainActivity.class);
    }
}
