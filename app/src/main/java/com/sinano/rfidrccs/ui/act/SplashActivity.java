package com.sinano.rfidrccs.ui.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.myapplication.R;
import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.bean.CabinetBean;
import com.sinano.rfidrccs.db.CabinetTable;
import com.sinano.rfidrccs.db.EnvironmentTable;
import com.sinano.rfidrccs.db.HelperTable;
import com.sinano.rfidrccs.db.OperationTable;
import com.sinano.rfidrccs.bean.PcBean;
import com.sinano.rfidrccs.db.PcTable;
import com.sinano.rfidrccs.db.ReagentEntryTable;
import com.sinano.rfidrccs.db.ReagentTable;
import com.sinano.rfidrccs.db.SQLiteManager;
import com.sinano.rfidrccs.bean.PersonBean;
import com.sinano.rfidrccs.db.PersonTable;
import com.sinano.rfidrccs.bean.RoleBean;
import com.sinano.rfidrccs.db.RoleTable;
import com.sinano.rfidrccs.db.TimeTable;
import com.sinano.rfidrccs.db.WarningTable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/18
 * Description: 启动界面
 */
public class SplashActivity extends BaseActivity {
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        splashThread();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        initSQL();

    }

    private void initSQL() {
        CabinetTable tCab = new CabinetTable();
        EnvironmentTable tEnv = new EnvironmentTable();
        OperationTable tOpt = new OperationTable();
        PcTable tPC = new PcTable();
        PersonTable tPer = new PersonTable();
        ReagentTable tReg = new ReagentTable();
        ReagentEntryTable tRegEty = new ReagentEntryTable();
        RoleTable tRole = new RoleTable();
        TimeTable tTime = new TimeTable();
        WarningTable tWarn = new WarningTable();
        HelperTable tHelp = new HelperTable();

        SQLiteManager.getInstance().initDB(getApplicationContext(), "SRCS.db");

        SQLiteManager.getInstance().registerTable(tCab);
        SQLiteManager.getInstance().registerTable(tEnv);
        SQLiteManager.getInstance().registerTable(tOpt);
        SQLiteManager.getInstance().registerTable(tPC);
        SQLiteManager.getInstance().registerTable(tPer);
        SQLiteManager.getInstance().registerTable(tReg);
        SQLiteManager.getInstance().registerTable(tRegEty);
        SQLiteManager.getInstance().registerTable(tRole);
        SQLiteManager.getInstance().registerTable(tTime);
        SQLiteManager.getInstance().registerTable(tWarn);
        SQLiteManager.getInstance().registerTable(tHelp);

        SQLiteManager.getInstance().open();
        $Log("数据库初始化，已打开，可使用！");


        SharedPreferences preferences = getSharedPreferences("RSCS", Context.MODE_PRIVATE);
        if (preferences.getBoolean("firststart", true)) {

            tHelp.insertHelperSql();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firststart", false);
            editor.apply();
        }

        PersonBean personBean = new PersonBean("E0E01E000A4ECA653D01","中科院苏州纳米所","01", "00安全员","1111",3,500,1);
        tPer.insert(tPer.getInsertContentValues(personBean));

        RoleBean roleBean = new RoleBean(3,"系统管理员",1);
        tRole.insert(tRole.getInsertContentValues(roleBean));

        PcBean pcBean = new PcBean("ip", "123", "www.baidu.com", "www.google.com");
        tPC.insert(tPC.getInsertContentValues(pcBean));

                CabinetBean containerBean1 = new CabinetBean("实验室A","1","普通型",1,1);
        CabinetBean containerBean2 = new CabinetBean("实验室A","2","普通型",1,1);
        CabinetBean containerBean3 = new CabinetBean("实验室A","3","普通型",1,1);
        CabinetBean containerBean4 = new CabinetBean("实验室A","4","普通型",1,1);
        tCab.insert(tCab.getInsertContentValues(containerBean1));
        tCab.insert(tCab.getInsertContentValues(containerBean2));
        tCab.insert(tCab.getInsertContentValues(containerBean3));
        tCab.insert(tCab.getInsertContentValues(containerBean4));

        $Log("数据库初始化完成！");
        showShortToast("初始化数据库");
    }

    @Override
    protected void doBusiness() {
    }

    private void splashThread() {
        final Thread st = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
        st.start();
    }
}
