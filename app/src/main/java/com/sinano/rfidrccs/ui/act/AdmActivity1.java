package com.sinano.rfidrccs.ui.act;

import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/15
 * Description: 试剂入库
 */
public class AdmActivity1 extends BaseActivity {
    private ImageButton btnRk1, btnRk2, btnRk3, btnRk4, btnCz;
    private TextView tvOpt;

    /**
     * 加载layout
     * @return layout
     */
    @Override
    protected int initLayout() {
        return R.layout.activity_adm1;
    }

    /**
     * 加载控件: btn，tv
     */
    @Override
    protected void initView() {
        btnRk1 = findView(R.id.btnRK1);
        btnRk1.setBackgroundResource(R.drawable.btnc01_bg);

        btnRk2 = findView(R.id.btnRK2);
        btnRk2.setBackgroundResource(R.drawable.btnc02_bg);

        btnRk3 = findView(R.id.btnRK3);
        btnRk3.setBackgroundResource(R.drawable.btnc03_bg);

        btnRk4 = findView(R.id.btnRK4);
        btnRk4.setBackgroundResource(R.drawable.btnc04_bg);

        btnCz = findView(R.id.btnCZ);
        btnCz.setBackgroundResource(R.drawable.btncz_bg);

        tvOpt = findViewById(R.id.tvOpt);
        tvOpt.setSelected(true);

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }

    /**
     * 业务:
     * 1、试剂入库
     * 2、试剂称重
     */
    @Override
    protected void doBusiness() {

    }
}