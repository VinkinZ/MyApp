package com.sinano.rfidrccs.ui.act;

import android.content.ContentValues;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.db.CabinetTable;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.SQLiteManager;
import com.sinano.rfidrccs.utils.ActivityCollector;
import com.sinano.rfidrccs.utils.CabinetType;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/15
 * Description: 试剂柜属性设置
 */
public class AdmActivity3 extends BaseActivity {
    private static final String TAG = "AdmActivity3";
    private long time = 0;
    private ImageButton btnExit;
    private CabinetTable tCab = new CabinetTable();
    private Spinner spin1, spin2, spin3, spin4;

    /**
     * 加载layout
     * @return layout
     */
    @Override
    protected int initLayout() {
        return R.layout.activity_adm3;
    }

    /**
     * 加载控件: btn，spinner
     */
    @Override
    protected void initView() {
        btnExit = findView(R.id.btnExit);
        btnExit.setBackgroundResource(R.drawable.btnexit_bg);

        spin1 = findViewById(R.id.spin1);
        spin2 = findViewById(R.id.spin2);
        spin3 = findViewById(R.id.spin3);
        spin4 = findViewById(R.id.spin4);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }

    /**
     * 业务:
     * 1、试剂柜属性配置
     * 2、退出系统
     */
    @Override
    protected void doBusiness() {
        setCabType(spin1,"1", tCab.queryCabinetType("1"));
        setCabType(spin2,"2", tCab.queryCabinetType("2"));
        setCabType(spin3,"3", tCab.queryCabinetType("3"));
        setCabType(spin4,"4", tCab.queryCabinetType("4"));

        btnExit.setOnClickListener(new onMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                exit();
            }
        });
    }

    /**
     * 配置试剂柜属性并写入数据库
     * @param spin 需要点击的Spinner控件
     * @param cabOrderNum 需要配置的试剂柜号
     */
    private void setCabType(Spinner spin, final String cabOrderNum, String cabTypeFromDB) {
        String[] sxItem = getResources().getStringArray(R.array.cabsx);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.myspinner, sxItem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setSelection(CabinetType.getCabIndex(cabTypeFromDB), true);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setGravity(Gravity.CENTER);
                String newType = tv.getText().toString();
                ContentValues cv = tCab.getContentValues(Item.CABINET_TYPE, newType);
                tCab.updateOneByItem(Item.CABINET_CODE, cabOrderNum, cv);
                showShortToast("设置" + cabOrderNum + "号试剂柜属性：" + newType);
                switch (tv.getText().toString()){
                    case "普通型":
                        tv.setText("普通型试剂柜");
                        tv.setTextColor(Color.parseColor("#b6b2b2"));
                        break;
                    case "阻燃型":
                        tv.setText("阻燃型试剂柜");
                        tv.setTextColor(Color.parseColor("#feff03"));
                        break;
                    case "抗腐蚀型":
                        tv.setText("抗腐蚀型试剂柜");
                        tv.setTextColor(Color.parseColor("#6c8c8b"));
                        break;
                    case "防爆型":
                        tv.setText("防爆型试剂柜");
                        tv.setTextColor(Color.parseColor("#fd0000"));
                        break;
                    case "防爆无浓度型":
                        tv.setText("防爆无浓度型试剂柜");
                        tv.setTextColor(Color.parseColor("#fd0000"));
                        break;
                    case "有毒称重型":
                        tv.setText("有毒称重型试剂柜");
                        tv.setTextColor(Color.parseColor("#225fa9"));
                        break;
                    case "有毒不称重型":
                        tv.setText("有毒不称重型试剂柜");
                        tv.setTextColor(Color.parseColor("#225fa9"));
                        break;
                    case "无配置":
                        tv.setText("未配置柜体属性");
                        tv.setTextColor(Color.parseColor("#000000"));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 双击退出系统
     */
    private void exit() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            showToast("再点击一次退出应用程序");
        } else {
            $Log("即将退出系统！");
            SQLiteManager.getInstance().close();
            Log.i(TAG, "数据库已关闭，不可用！ ");
            ActivityCollector.appExit(this);
        }
    }

}
