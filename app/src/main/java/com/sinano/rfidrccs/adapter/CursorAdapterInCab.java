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
 * Description: 在柜试剂表适配
 */
public class CursorAdapterInCab extends CursorAdapter {

    public CursorAdapterInCab(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adpt_clist1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView c_sjmc1 = view.findViewById(R.id.c_sjmc1);
        TextView c_sjlx1 = view.findViewById(R.id.c_sjlx1);
        TextView c_gg1 = view.findViewById(R.id.c_gg1);
        TextView c_zjl1 = view.findViewById(R.id.c_zjl1);
        TextView c_hh1 = view.findViewById(R.id.c_hh1);
        TextView c_ccl1 = view.findViewById(R.id.c_ccl1);
        TextView c_zt1 = view.findViewById(R.id.c_zt1);
        TextView c_scqyr1 = view.findViewById(R.id.c_scqyr1);

        c_sjmc1.setText(cursor.getString(cursor.getColumnIndex(Item.REAGENT_NAME)));
        c_sjlx1.setText(cursor.getString(cursor.getColumnIndex(Item.REAGENT_TYPE)));
        c_gg1.setText(cursor.getString(cursor.getColumnIndex(Item.SPECIFICATION)));
        c_zjl1.setText(cursor.getString(cursor.getColumnIndex(Item.MASTER_METERING)));
        c_hh1.setText(cursor.getString(cursor.getColumnIndex(Item.ITEM_NUMBER)));
        c_ccl1.setText(cursor.getString(cursor.getColumnIndex(Item.RESIDUAL_AMOUNT)));
        c_zt1.setText(cursor.getString(cursor.getColumnIndex(Item.REAGENT_FLAG)));
        c_scqyr1.setText(cursor.getString(cursor.getColumnIndex(Item.USER_NAME)));

    }
}
