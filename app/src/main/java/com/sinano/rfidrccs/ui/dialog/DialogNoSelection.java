package com.sinano.rfidrccs.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseDialog;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/31 19:06
 * Description: 没有按键的弹窗
 */
public class DialogNoSelection extends BaseDialog {
    private TextView tvDiaTitle, tvDiaMsg;
    private String title, msg;

    public DialogNoSelection(Context context) {
        super(context);
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog0;
    }

    @Override
    protected void initView() {
        tvDiaTitle = findViewById(R.id.tvDiaTitle);
        tvDiaMsg = findViewById(R.id.tvDiaMsg);
    }

    @Override
    protected void initData() {
        if (null != title){
            tvDiaTitle.setText(title);
        }
        if (null != msg){
            tvDiaMsg.setText(msg);
        }
    }

    @Override
    protected void initEvent() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

