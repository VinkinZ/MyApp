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
 * Description: 报废试剂表适配
 */
public class CursorAdapterReagentDiscard extends CursorAdapter {

    public CursorAdapterReagentDiscard(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adpt_reagent3, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView hwd_sjmc = view.findViewById(R.id.hwd_sjmc);
        TextView hwd_hh = view.findViewById(R.id.hwd_hh);
        TextView hwd_ghs = view.findViewById(R.id.hwd_ghs);
        TextView hwd_rgsj = view.findViewById(R.id.hwd_rgsj);
        TextView hwd_gh = view.findViewById(R.id.hwd_gh);
        TextView hwd_zjl = view.findViewById(R.id.hwd_zjl);
        TextView hwd_czry = view.findViewById(R.id.hwd_czry);
        TextView hwd_bfsj = view.findViewById(R.id.hwd_bfsj);
        TextView hwd_zt = view.findViewById(R.id.hwd_zt);

        hwd_sjmc.setText(cursor.getString(cursor.getColumnIndex(Item.REAGENT_NAME)));
        hwd_hh.setText(cursor.getString(cursor.getColumnIndex(Item.ITEM_NUMBER)));
        hwd_ghs.setText(cursor.getString(cursor.getColumnIndex(Item.SUPPLIER)));
        hwd_rgsj.setText(cursor.getString(cursor.getColumnIndex(Item.ENTRY_TIME)));
        hwd_gh.setText(cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE)));
        hwd_zjl.setText(cursor.getString(cursor.getColumnIndex(Item.MASTER_METERING)));
        hwd_czry.setText(cursor.getString(cursor.getColumnIndex(Item.USER_NAME)));
        hwd_bfsj.setText(cursor.getString(cursor.getColumnIndex(Item.OPERATE_TIME)));
        hwd_zt.setText(cursor.getString(cursor.getColumnIndex(Item.REAL_STATUS_CONTENT)));
    }
}
