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
 * Description: 灭火器表适配
 */
public class CursorAdapterFire extends CursorAdapter {

    public CursorAdapterFire(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cell, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView mhq = view.findViewById(R.id.mhq);

        mhq.setText(cursor.getString(cursor.getColumnIndex(Item.FIRE_EXTINGUISHER_NAME)));
    }
}
