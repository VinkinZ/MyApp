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
 * Description: 风险表适配
 */
public class CursorAdapterRisk extends CursorAdapter {

    public CursorAdapterRisk(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adpt_risk,parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView rq_sj = view.findViewById(R.id.rq_sj);
        TextView rq_xgry = view.findViewById(R.id.rq_xgry);
        TextView rq_fxlx = view.findViewById(R.id.rq_fxlx);
        TextView rq_fxjb = view.findViewById(R.id.rq_fxjb);

        rq_sj.setText(cursor.getString(cursor.getColumnIndex(Item.WARNING_TIME)));
        rq_xgry.setText(cursor.getString(cursor.getColumnIndex(Item.USER_NAME)));
        rq_fxlx.setText(cursor.getString(cursor.getColumnIndex(Item.WARNING_ID_CONTENT)));
        rq_fxjb.setText(cursor.getString(cursor.getColumnIndex(Item.WARNING_LEVEL)));


    }
}
