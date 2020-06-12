package com.sinano.rfidrccs.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseDialog;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/01 20:10
 * Description: 两个按键，两个编辑栏的弹窗
 */
public class DialogTwoSelection_ extends BaseDialog {
    private TextView tvDiaTitle, tvDia1, tvDia2;
    private EditText editDia1, editDia2;
    private Button btnDiaN, btnDiaP;
    private String title, strN, strP, str1, str2, hint1, hint2;
    private DialogTwoSelection.onNegativeOnclickListener negativeOnclickListener;
    private DialogTwoSelection.onPositiveOnclickListener positiveOnclickListener;

    public DialogTwoSelection_(Context context) {
        super(context);
    }

    @Override
    protected int initLayout() {
        return R.layout.dialog2_;
    }

    @Override
    protected void initView() {
        tvDiaTitle = findViewById(R.id.tvDiaTitle);
        tvDia1 = findViewById(R.id.tvDia1);
        tvDia2 = findViewById(R.id.tvDia2);
        editDia1 = findViewById(R.id.editDia1);
        editDia2 = findViewById(R.id.editDia2);
        btnDiaN = findViewById(R.id.btnDiaN);
        btnDiaP = findViewById(R.id.btnDiaP);
    }

    @Override
    protected void initData() {
        if (null != title) {
            tvDiaTitle.setText(title);
        }
        if (null != str1) {
            tvDia1.setText(str1);
        }
        if (null != str2) {
            tvDia2.setText(str2);
        }
        if (null != strN) {
            btnDiaN.setText(strN);
        }
        if (null != strP) {
            btnDiaP.setText(strP);
        }
        if (null != hint1) {
            editDia1.setHint(hint1);
        }
        if (null != hint2) {
            editDia2.setHint(hint2);
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

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getHint1() {
        return hint1;
    }

    public void setHint1(String hint1) {
        this.hint1 = hint1;
    }

    public String getHint2() {
        return hint2;
    }

    public void setHint2(String hint2) {
        this.hint2 = hint2;
    }

    public EditText getEditDia1() {
        return editDia1;
    }

    public void setEditDia1(EditText editDia1) {
        this.editDia1 = editDia1;
    }

    public EditText getEditDia2() {
        return editDia2;
    }

    public void setEditDia2(EditText editDia2) {
        this.editDia2 = editDia2;
    }
}
