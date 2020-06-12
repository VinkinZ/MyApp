package com.sinano.rfidrccs.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseDialog;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/31 20:10
 * Description: 两个按键的弹窗
 */
public class DialogTwoSelection extends BaseDialog {
    private TextView tvDiaTitle, tvDiaMsg;
    private Button btnDiaN, btnDiaP;
    private String title, msg, strN, strP;
    private onNegativeOnclickListener negativeOnclickListener;
    private onPositiveOnclickListener positiveOnclickListener;

    public DialogTwoSelection(Context context) {
        super(context);
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog2;
    }

    @Override
    protected void initView() {
        tvDiaTitle = findViewById(R.id.tvDiaTitle);
        tvDiaMsg = findViewById(R.id.tvDiaMsg);
        btnDiaN = findViewById(R.id.btnDiaN);
        btnDiaP = findViewById(R.id.btnDiaP);
    }

    @Override
    protected void initData() {
        if (null != title) {
            tvDiaTitle.setText(title);
        }
        if (null != msg) {
            tvDiaMsg.setText(msg);
        }
        if (null != strN) {
            btnDiaN.setText(strN);
        }
        if (null != strP) {
            btnDiaP.setText(strP);
        }
    }

    @Override
    protected void initEvent() {
        btnDiaN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != negativeOnclickListener){
                    negativeOnclickListener.onNegativeClick();
                }
            }
        });

        btnDiaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != positiveOnclickListener){
                    positiveOnclickListener.onPositiveClick();
                }
            }
        });
    }


    public interface onNegativeOnclickListener {
        void onNegativeClick();
    }

    public interface onPositiveOnclickListener {
        void onPositiveClick();
    }

    public void setNegativeOnclickListener(String str, DialogTwoSelection.onNegativeOnclickListener onNegativeOnclickListener){
        if (null != str){
            strN = str;
        }
        this.negativeOnclickListener = onNegativeOnclickListener;
    }

    public void setPositiveOnclickListener(String str, DialogTwoSelection.onPositiveOnclickListener onPositiveOnclickListener){
        if (null != str){
            strP = str;
        }
        this.positiveOnclickListener = onPositiveOnclickListener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStrN() {
        return strN;
    }

    public void setStrN(String strN) {
        this.strN = strN;
    }

    public String getStrP() {
        return strP;
    }

    public void setStrP(String strP) {
        this.strP = strP;
    }
}
