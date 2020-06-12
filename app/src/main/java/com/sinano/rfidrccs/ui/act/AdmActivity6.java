package com.sinano.rfidrccs.ui.act;

import android.content.ContentValues;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.CallSuper;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.PcTable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/15
 * Description: URL设置
 */
public class AdmActivity6 extends BaseActivity {
    private EditText etFireURL, etURL;
    private ImageButton btnSaveFireURL, btnSaveURL;
    private final PcTable tPc = new PcTable();

    /**
     * 加载layout
     * @return layout
     */
    @Override
    protected int initLayout() {
        return R.layout.activity_adm6;
    }


    /**
     * 加载控件: btn
     */
    @Override
    protected void initView() {

        etFireURL = findView(R.id.etMHQURL);

        btnSaveFireURL = findView(R.id.btnBCMQHURL);
        btnSaveFireURL.setBackgroundResource(R.drawable.btnbc_bg);

        etURL = findView(R.id.etURL);

        btnSaveURL = findView(R.id.btnBCURL);
        btnSaveURL.setBackgroundResource(R.drawable.btnbc_bg);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        etFireURL.setText(tPc.queryPcUrl(Item.FIRE_URL));
        etURL.setText(tPc.queryPcUrl(Item.URL));
    }

    /**
     * 业务:
     * 1、管理平台URL设置并写入数据库
     * 2、灭火器URL设置并写入数据库
     */
    @Override
    protected void doBusiness() {

        btnSaveFireURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strFireURL = etFireURL.getText().toString();
                ContentValues cv = tPc.getContentValues(Item.FIRE_URL, strFireURL);
                tPc.updateOneByItem(Item.IP, "ip", cv);
                showShortToast("已保存灭火器配置源网址：" + strFireURL);
            }
        });

        btnSaveURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strURL = etURL.getText().toString();
                ContentValues cv = tPc.getContentValues(Item.URL, strURL);
                tPc.updateOneByItem(Item.IP, "ip", cv);
                showShortToast("已保存安全管理平台源网址：" + strURL);
            }
        });
    }

    /**
     * 点击非编辑区域收起键盘，获取点击事件
     * @param ev 事件
     * @return true or false
     */
    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() ==  MotionEvent.ACTION_DOWN ) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(null != view ? view.getWindowToken() : null);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断当前是否需要隐藏
     * @param v 当前控件
     * @param ev 事件
     * @return true or false
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     * @param token token
     */
    private void hideSoftInput(IBinder token) {
        if (null != token) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
