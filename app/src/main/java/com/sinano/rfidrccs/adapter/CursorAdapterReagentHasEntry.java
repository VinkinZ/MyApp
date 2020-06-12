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
 * Description: 已入库试剂表适配
 */
public class CursorAdapterReagentHasEntry extends CursorAdapter {

    public CursorAdapterReagentHasEntry(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adpt_reagent2, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView i_sjmc2 = view.findViewById(R.id.i_sjmc2);
        TextView i_gg2 = view.findViewById(R.id.i_gg2);
        TextView i_zjl2 = view.findViewById(R.id.i_zjl2);
        TextView i_ccl2 = view.findViewById(R.id.i_ccl2);
        TextView i_gh2 = view.findViewById(R.id.i_gh2);
        TextView i_zt2 = view.findViewById(R.id.i_zt2);
        TextView i_scqyr2 = view.findViewById(R.id.i_scqyr2);

        i_sjmc2.setText(cursor.getString(cursor.getColumnIndex(Item.REAGENT_NAME)));
        i_gg2.setText(cursor.getString(cursor.getColumnIndex(Item.SPECIFICATION)));
        i_zjl2.setText(cursor.getString(cursor.getColumnIndex(Item.MASTER_METERING)));
        i_ccl2.setText(cursor.getString(cursor.getColumnIndex(Item.RESIDUAL_AMOUNT)));
        i_gh2.setText(cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE)));
        i_zt2.setText(cursor.getString(cursor.getColumnIndex(Item.REAL_STATUS_CONTENT)));
        i_scqyr2.setText(cursor.getString(cursor.getColumnIndex(Item.USER_NAME)));
    }
}
