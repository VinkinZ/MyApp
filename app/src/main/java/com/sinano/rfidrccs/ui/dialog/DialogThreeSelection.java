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
 * Date: 2020/5/31 20:11
 * Description: 三个按键的弹窗
 */
public class DialogThreeSelection extends BaseDialog {
    private TextView tvDiaTitle, tvDiaMsg;
    private Button btnDiaN, btnDiaFirst, btnDiaSecond;
    private String title, msg, strN, strFirst, strSecond;
    private onNegativeOnclickListener negativeOnclickListener;
    private onFirstOnclickListener firstOnclickListener;
    private onSecondOnclickListener secondOnclickListener;


    public DialogThreeSelection(Context context) {
        super(context);
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog3;
    }

    @Override
    protected void initView() {
        tvDiaTitle = findViewById(R.id.tvDiaTitle);
        tvDiaMsg = findViewById(R.id.tvDiaMsg);
        btnDiaN = findViewById(R.id.btnDiaN);
        btnDiaFirst = findViewById(R.id.btnDiaFirst);
        btnDiaSecond = findViewById(R.id.btnDiaSecond);
    }

    @Override
    protected void initData() {
        if (null != title) {
            tvDiaTitle.setText(title);
        }
        if (null != msg) {
            tvDiaMsg.setText(msg);
        }
        if (null != strFirst) {
            btnDiaFirst.setText(strFirst);
        }
        if (null != strSecond) {
            btnDiaSecond.setText(strSecond);
        }
        if (null != strN) {
            btnDiaN.setText(strN);
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

        btnDiaFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != firstOnclickListener){
                    firstOnclickListener.onFirstClick();
                }
            }
        });

        btnDiaSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != secondOnclickListener){
                    secondOnclickListener.onSecondClick();
                }
            }
        });

    }

    public interface onNegativeOnclickListener {
        void onNegativeClick();
    }

    public void setNegativeOnclickListener(String str, DialogThreeSelection.onNegativeOnclickListener onNegativeOnclickListener){
        if (null != str){
            strN = str;
        }
        this.negativeOnclickListener = onNegativeOnclickListener;
    }

    public interface onFirstOnclickListener {
        void onFirstClick();
    }

    public void setFirstOnclickListener(String str, DialogThreeSelection.onFirstOnclickListener onFirstOnclickListener){
        if (null != str){
            strFirst = str;
        }
        this.firstOnclickListener = onFirstOnclickListener;
    }

    public interface onSecondOnclickListener {
        void onSecondClick();
    }

    public void setSecondOnclickListener(String str, DialogThreeSelection.onSecondOnclickListener onSecondOnclickListener){
        if (null != str){
            strSecond = str;
        }
        this.secondOnclickListener = onSecondOnclickListener;
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

    public String getStrFirst() {
        return strFirst;
    }

    public void setStrFirst(String strFirst) {
        this.strFirst = strFirst;
    }
}
