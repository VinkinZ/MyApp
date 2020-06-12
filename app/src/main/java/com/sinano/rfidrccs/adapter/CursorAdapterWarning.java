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
 * Description: 预警表适配
 */
public class CursorAdapterWarning extends CursorAdapter {

    public CursorAdapterWarning(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adpt_mlist2, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView m_yjry2 = view.findViewById(R.id.m_yjry2);
        TextView m_yjxx2 = view.findViewById(R.id.m_yjxx2);
        TextView m_sj2 = view.findViewById(R.id.m_sj2);

        m_yjry2.setText(cursor.getString(cursor.getColumnIndex(Item.USER_NAME)));
        m_yjxx2.setText(cursor.getString(cursor.getColumnIndex(Item.WARNING_ID_CONTENT)));
        m_sj2.setText(cursor.getString(cursor.getColumnIndex(Item.WARNING_TIME)));


    }
}
