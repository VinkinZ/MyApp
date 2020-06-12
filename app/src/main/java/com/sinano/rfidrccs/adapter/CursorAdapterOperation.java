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
 * Description: 操作记录表适配
 */
public class CursorAdapterOperation extends CursorAdapter {

    public CursorAdapterOperation(Context context, Cursor c){
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    /**
     * newView表示创建适配器控件中每个item对应的view对象
     * @param context 上下文
     * @param cursor 数据源cursor对象
     * @param parent 当前item的父布局
     * @return 每项item的view对象
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adpt_mlist1, parent, false);
    }

    /**
     * 通过newView()方法确定每个item展示的view对象，在bindView中对布局中的控件进行填充
     * @param view 由newView()方法返回的每项view对象
     * @param context 上下文
     * @param cursor 数据源cursor对象
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView m_rqsj1 = view.findViewById(R.id.m_rqsj1);
        TextView m_lyry1 = view.findViewById(R.id.m_lyry1);
        TextView m_sjpm1 = view.findViewById(R.id.m_sjpm1);
        TextView m_gh1 = view.findViewById(R.id.m_gh1);
        TextView m_zt1 = view.findViewById(R.id.m_zt1);
        TextView m_yl1 = view.findViewById(R.id.m_yl1);
        TextView m_sfcf1 = view.findViewById(R.id.m_sfcf1);

        m_rqsj1.setText(cursor.getString(cursor.getColumnIndex(Item.OPERATE_TIME)));
        m_lyry1.setText(cursor.getString(cursor.getColumnIndex(Item.USER_NAME)));
        m_sjpm1.setText(cursor.getString(cursor.getColumnIndex(Item.REAGENT_NAME)));
        m_gh1.setText(cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE)));
        m_zt1.setText(cursor.getString(cursor.getColumnIndex(Item.OPERATE_STATE_CONTENT)));
        m_yl1.setText(cursor.getString(cursor.getColumnIndex(Item.REAL_WEIGHT)));
        m_sfcf1.setText(cursor.getString(cursor.getColumnIndex(Item.MISPLACE)));
    }
}
