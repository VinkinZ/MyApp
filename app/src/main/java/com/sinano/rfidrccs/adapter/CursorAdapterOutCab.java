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
 * Description: 不再柜试剂表适配
 */
public class CursorAdapterOutCab extends CursorAdapter {

    public CursorAdapterOutCab(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adpt_clist2, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView c_zyr2 = view.findViewById(R.id.c_zyr2);
        TextView c_sjmc2 = view.findViewById(R.id.c_sjmc2);
        TextView c_sjlx2 = view.findViewById(R.id.c_sjlx2);
        TextView c_gg2 = view.findViewById(R.id.c_gg2);
        TextView c_zjl2 = view.findViewById(R.id.c_zjl2);
        TextView c_hh2 = view.findViewById(R.id.c_hh2);
        TextView c_ghs2 = view.findViewById(R.id.c_ghs2);
        TextView c_qysj2 = view.findViewById(R.id.c_qysj2);

        c_zyr2.setText(cursor.getString(cursor.getColumnIndex(Item.USER_NAME)));
        c_sjmc2.setText(cursor.getString(cursor.getColumnIndex(Item.REAGENT_NAME)));
        c_sjlx2.setText(cursor.getString(cursor.getColumnIndex(Item.REAGENT_TYPE)));
        c_gg2.setText(cursor.getString(cursor.getColumnIndex(Item.SPECIFICATION)));
        c_zjl2.setText(cursor.getString(cursor.getColumnIndex(Item.MASTER_METERING)));
        c_hh2.setText(cursor.getString(cursor.getColumnIndex(Item.ITEM_NUMBER)));
        c_ghs2.setText(cursor.getString(cursor.getColumnIndex(Item.SUPPLIER)));
        c_qysj2.setText(cursor.getString(cursor.getColumnIndex(Item.OPERATE_TIME)));
    }
}
