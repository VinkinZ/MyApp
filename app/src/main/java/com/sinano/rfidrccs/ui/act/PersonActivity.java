package com.sinano.rfidrccs.ui.act;

import android.database.Cursor;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.R;
import com.sinano.rfidrccs.adapter.CursorAdapterPerson;
import com.sinano.rfidrccs.base.BaseActivity;
import com.sinano.rfidrccs.db.PersonTable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/19
 * Description: 人员管理
 */
public class PersonActivity extends BaseActivity {
    private ImageButton btnP2m;
    private ListView listPerson;
    @Override
    protected int initLayout() {
        return R.layout.activity_person;
    }

    @Override
    protected void initView() {
        btnP2m = findView(R.id.btnP2M);
        btnP2m.setBackgroundResource(R.drawable.btn2main_bg);
        listPerson = findViewById(R.id.listPerson);
    }

    @Override
    protected void initData() {
        PersonTable personTable = new PersonTable();
        Cursor cursor = personTable.cursorAdapterPerson();
        CursorAdapterPerson cursorAdapterPersonnel = new CursorAdapterPerson(this,cursor);
        listPerson.setAdapter(cursorAdapterPersonnel);
    }

    @Override
    protected void doBusiness() {
        pageJump(btnP2m, MainActivity.class);
    }
}
