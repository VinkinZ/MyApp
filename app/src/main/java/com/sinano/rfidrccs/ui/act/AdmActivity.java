package com.sinano.rfidrccs.ui.act;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.db.HelperTable;
import com.sinano.rfidrccs.db.Item;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/15
 * Description: 管理员界面
 */
public class AdmActivity extends BaseActivity {
    private ImageButton btnA2m, btnRk, btnBf, btnSx, btnMrhc, btnUrl, btnMhq;
    private Spinner spinMax;
    private TextView tvHcsj;
    private String dbMaxOut;
    private int maxOutIndex, hour, min;
    private HelperTable tHelper;

    /**
     * 加载layout
     * @return layout
     */
    @Override
    protected int initLayout() {
        return R.layout.activity_adm;
    }

    /**
     * 加载控件: img，btn，spinner，tv
     */
    @Override
    protected void initView() {
        ImageView imgzdqy = findViewById(R.id.imgZDQY);
        imgzdqy.setBackgroundResource(R.drawable.btnsd);

        btnA2m = findView(R.id.btnA2M);
        btnA2m.setBackgroundResource(R.drawable.btn2main_bg);

        btnRk = findView(R.id.btnRK);
        btnRk.setBackgroundResource(R.drawable.btnsd_bg);

        btnBf = findViewById(R.id.btnYBF);
        btnBf.setBackgroundResource(R.drawable.btnsd_bg);

        btnSx = findView(R.id.btnSX);
        btnSx.setBackgroundResource(R.drawable.btnsd_bg);

        spinMax = findView(R.id.spinZDQY);

        btnMrhc = findView(R.id.btnMRHC);
        btnMrhc.setBackgroundResource(R.drawable.btnsd_bg);
        tvHcsj = findView(R.id.tvHCSJ);

        btnUrl = findView(R.id.btnURL);
        btnUrl.setBackgroundResource(R.drawable.btnsd_bg);

        btnMhq = findView(R.id.btnMHQ);
        btnMhq.setBackgroundResource(R.drawable.btnsd_bg);

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        tHelper = new HelperTable();

        dbMaxOut = tHelper.queryInHelper(Item.MAX_AMOUNT);

        String checkTime = tHelper.queryInHelper(Item.CHECK_TIME);
        tvHcsj.setText(checkTime);
        hour = Integer.parseInt(checkTime.substring(0, 2));
        min = Integer.parseInt(checkTime.substring(3, 5));
    }

    /**
     * 业务:
     * 1、页面跳转
     * 2、试剂报废
     * 3、设置单次取用试剂最大量
     * 4、设置每日试剂核查时间
     */
    @Override
    protected void doBusiness() {
        pageJump(btnA2m, MainActivity.class);
        pageJump(btnRk, AdmActivity1.class);
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==0x001){
                    pageJump(btnSx, AdmActivity3.class);
                }

            }
        };

        pageJump(btnUrl, AdmActivity6.class);
        pageJump(btnMhq, AdmActivity7.class);

        setMaxOut(amountToIndex(dbMaxOut));

        btnMrhc.setOnClickListener(new onSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                setCheckTime();
            }
        });


    }

    /**
     * 设置单次最大取用量并写入数据库
     * @param selected 索引
     */
    private void setMaxOut(int selected) {
        String[] maxOutItem = getResources().getStringArray(R.array.maxout);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.myspinner, maxOutItem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMax.setAdapter(adapter);
        spinMax.setSelection(selected, true);
        spinMax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.red));
                tv.setGravity(Gravity.CENTER);
                String newMaxOut = tv.getText().toString();
                ContentValues cv = tHelper.getContentValues(Item.MAX_AMOUNT, newMaxOut);
                tHelper.updateOneByItem(Item.SETTING, "设置", cv);
                showShortToast("当前设置试剂单次最大取用量：" +  newMaxOut);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 得到最大取用量索引
     * @param amount 数据库中读到的最大取用量
     * @return 索引
     */
    private int amountToIndex(String amount) {
        switch (amount) {
            case "3":
                maxOutIndex = 0;
                break;
            case "4":
                maxOutIndex = 1;
                break;
            case "5":
                maxOutIndex = 2;
                break;
            case "6":
                maxOutIndex = 3;
                break;
            case "7":
                maxOutIndex = 4;
                break;
            case "8":
                maxOutIndex = 5;
                break;
            case "9":
                maxOutIndex = 6;
                break;
            case "10":
                maxOutIndex = 7;
                break;
        }
        return maxOutIndex;
    }

    /**
     * 设置每日核查时间并写入数据库
     */
    private void setCheckTime() {
        final TimePickerDialog tpDialog = new TimePickerDialog(AdmActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker tp, int hourOfDay, int minute) {
                if (minute < 10) {
                    if (hourOfDay < 10){
                        tvHcsj.setText("0" + hourOfDay + ":" + "0" + minute);
                    }
                    else {
                        tvHcsj.setText(hourOfDay + ":" + "0" + minute);
                    }
                } else if (hourOfDay < 10) {
                    tvHcsj.setText("0" + hourOfDay + ":" + minute);
                } else {
                    tvHcsj.setText(hourOfDay + ":" + minute);
                }
                String newCheckTime = tvHcsj.getText().toString();
                ContentValues cv = tHelper.getContentValues(Item.CHECK_TIME, newCheckTime);
                tHelper.updateOneByItem(Item.SETTING,"设置", cv);
                showShortToast("当前设置试剂每日核查时间：" +  newCheckTime);
            }
        }, hour, min, true);
        tpDialog.setCanceledOnTouchOutside(true);
        tpDialog.show();

        // 弹窗消失
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                tpDialog.dismiss();
                t.cancel();
            }
        }, 1000 * 60);
    }

}
