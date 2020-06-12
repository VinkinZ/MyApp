package com.sinano.rfidrccs.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.sinano.rfidrccs.db.Item;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 人员管理表适配
 */
public class CursorAdapterPerson extends CursorAdapter {

    public CursorAdapterPerson(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return  LayoutInflater.from(context).inflate(R.layout.adpt_person, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView pm_dw = view.findViewById(R.id.pm_dw);
        TextView pm_bm = view.findViewById(R.id.pm_bm);
        TextView pm_xm = view.findViewById(R.id.pm_xm);
        TextView pm_ARP = view.findViewById(R.id.pm_ARP);
        TextView pm_js = view.findViewById(R.id.pm_js);
        TextView pm_qxjb = view.findViewById(R.id.pm_qxjb);
        TextView pm_aqfz = view.findViewById(R.id.pm_aqfz);

        pm_dw.setText(cursor.getString(cursor.getColumnIndex(Item.UNIT)));
        pm_bm.setText(cursor.getString(cursor.getColumnIndex(Item.DEPARTMENT_NAME)));
        pm_xm.setText(cursor.getString(cursor.getColumnIndex(Item.USER_NAME)));
        pm_ARP.setText(cursor.getString(cursor.getColumnIndex(Item.ARP)));
        pm_js.setText(cursor.getString(cursor.getColumnIndex(Item.ROLE_NAME)));
        pm_qxjb.setText(cursor.getString(cursor.getColumnIndex(Item.AUTH_LEVEL)));
        pm_aqfz.setText(cursor.getString(cursor.getColumnIndex(Item.SAFE_SCORE)));
    }

}