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
 * Description: 未入库试剂表适配
 */
public class CursorAdapterReagentNotEntry extends CursorAdapter {

    public CursorAdapterReagentNotEntry(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adpt_reagent1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView i_sjmc1 = view.findViewById(R.id.i_sjmc1);
        TextView i_gg1 = view.findViewById(R.id.i_gg1);
        TextView i_zjl1 = view.findViewById(R.id.i_zjl1);
        TextView i_hh1 = view.findViewById(R.id.i_hh1);
        TextView i_gh1 = view.findViewById(R.id.i_gh1);
        TextView i_zt1 = view.findViewById(R.id.i_zt1);

        i_sjmc1.setText(cursor.getString(cursor.getColumnIndex(Item.REAGENT_NAME)));
        i_gg1.setText(cursor.getString(cursor.getColumnIndex(Item.SPECIFICATION)));
        i_zjl1.setText(cursor.getString(cursor.getColumnIndex(Item.MASTER_METERING)));
        i_hh1.setText(cursor.getString(cursor.getColumnIndex(Item.ITEM_NUMBER)));
        i_gh1.setText(cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE)));
        i_zt1.setText(cursor.getString(cursor.getColumnIndex(Item.REAL_STATUS_CONTENT)));
    }
}
