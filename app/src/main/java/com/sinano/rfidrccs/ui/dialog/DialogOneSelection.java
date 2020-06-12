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
 * Date: 2020/5/31 20:09
 * Description: 单个按键的弹窗
 */
public class DialogOneSelection extends BaseDialog {
    private TextView tvDiaTitle, tvDiaMsg;
    private Button btnDiaN;
    private String title, msg, strN;
    private onNegativeOnclickListener negativeOnclickListener;

    public DialogOneSelection(Context context) {
        super(context);
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog1;
    }

    @Override
    protected void initView() {
        tvDiaTitle = findViewById(R.id.tvDiaTitle);
        tvDiaMsg = findViewById(R.id.tvDiaMsg);
        btnDiaN = findViewById(R.id.btnDiaN);
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
    }

    public interface onNegativeOnclickListener {
        void onNegativeClick();
    }

    public void setNegativeOnclickListener(String str, DialogOneSelection.onNegativeOnclickListener onNegativeOnclickListener){
        if (null != str){
            strN = str;
        }
        this.negativeOnclickListener = onNegativeOnclickListener;
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
}
