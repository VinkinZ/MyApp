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
 * Description: 环境信息表适配
 */
public class CursorAdapterEnv extends CursorAdapter {
    private int num;
    private View view;

    public CursorAdapterEnv(Context context, Cursor c, int num) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        this.num = num;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        switch (num){
            case 4:
                view = LayoutInflater.from(context).inflate(R.layout.adpt_env4, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(context).inflate(R.layout.adpt_env3, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.adpt_env2, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.adpt_env1, parent, false);
                break;
        }
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView i_sj2 = view.findViewById(R.id.i_sj2);
        TextView i_t01 = view.findViewById(R.id.i_t01);
        TextView i_h01 = view.findViewById(R.id.i_h01);
        TextView i_v01 = view.findViewById(R.id.i_v01);
        TextView i_t02 = view.findViewById(R.id.i_t02);
        TextView i_h02 = view.findViewById(R.id.i_h02);
        TextView i_v02 = view.findViewById(R.id.i_v02);
        TextView i_t03 = view.findViewById(R.id.i_t03);
        TextView i_h03 = view.findViewById(R.id.i_h03);
        TextView i_v03 = view.findViewById(R.id.i_v03);
        TextView i_t04 = view.findViewById(R.id.i_t04);
        TextView i_h04 = view.findViewById(R.id.i_h04);
        TextView i_v04 = view.findViewById(R.id.i_v04);

        i_sj2.setText(cursor.getString(cursor.getColumnIndex(Item.UPDATE_TIME)));
        i_t01.setText(cursor.getString(cursor.getColumnIndex(Item.TEMPERATURE01)));
        i_h01.setText(cursor.getString(cursor.getColumnIndex(Item.HUMIDITY01)));
        i_v01.setText(cursor.getString(cursor.getColumnIndex(Item.VOC01)));
        i_t02.setText(cursor.getString(cursor.getColumnIndex(Item.TEMPERATURE02)));
        i_h02.setText(cursor.getString(cursor.getColumnIndex(Item.HUMIDITY02)));
        i_v02.setText(cursor.getString(cursor.getColumnIndex(Item.VOC02)));
        i_t03.setText(cursor.getString(cursor.getColumnIndex(Item.TEMPERATURE03)));
        i_h03.setText(cursor.getString(cursor.getColumnIndex(Item.HUMIDITY03)));
        i_v03.setText(cursor.getString(cursor.getColumnIndex(Item.VOC03)));
        i_t04.setText(cursor.getString(cursor.getColumnIndex(Item.TEMPERATURE04)));
        i_h04.setText(cursor.getString(cursor.getColumnIndex(Item.HUMIDITY04)));
        i_v04.setText(cursor.getString(cursor.getColumnIndex(Item.VOC04)));

    }

}
