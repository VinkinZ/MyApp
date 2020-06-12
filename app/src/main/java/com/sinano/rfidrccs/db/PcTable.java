package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.sinano.rfidrccs.bean.PcBean;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.SQLiteManager;
import com.sinano.rfidrccs.db.Table;
import com.sinano.rfidrccs.db.TableOpt;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 上位机信息表操作
 */
public class PcTable extends Table implements TableOpt {
    private static final String TAG = "PcTable";

    public PcTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = Table.PC_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.IP, Item.ITEM_TYPE_TEXT));
        this.items.add(new Item(Item.MASK, Item.ITEM_TYPE_TEXT));
        this.items.add(new Item(Item.URL, Item.ITEM_TYPE_TEXT));
        this.items.add(new Item(Item.FIRE_URL, Item.ITEM_TYPE_TEXT));
        this.items.add(new Item(Item.SEND_FLAG, Item.ITEM_TYPE_INTEGER_D0));
    }

    @Override
    public ContentValues getInsertContentValues(Object bean) {
        PcBean pcBean = (PcBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.IP, pcBean.getIp());
        contentValues.put(Item.MASK, pcBean.getMask());
        contentValues.put(Item.URL, pcBean.getUrl());
        contentValues.put(Item.FIRE_URL, pcBean.getFireUrl());
        return contentValues;
    }

    /**
     * 查询URL
     * @param strURL URL类型
     * @return URL
     */
    public String queryPcUrl(String strURL) {
        Cursor cursor = null;
        try {
            if(strURL.equals(Item.URL)) {
                cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.URL + " from " + tableName + " where " + Item.IP + "=?", new String[]{"ip"});
                if (null != cursor && cursor.moveToFirst()) {
                    Log.d(TAG, "queryPcUrl: URL" + cursor.getString(cursor.getColumnIndex(Item.URL)));
                    return cursor.getString(cursor.getColumnIndex(Item.URL));
                }
            }else if(strURL.equals(Item.FIRE_URL)){
                cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.FIRE_URL + " from " + tableName + " where " + Item.IP + "=?", new String[]{"ip"});
                if (null != cursor && cursor.moveToFirst()) {
                    Log.d(TAG, "queryPcUrl: fireURL " + cursor.getString(cursor.getColumnIndex(Item.FIRE_URL)));
                    return cursor.getString(cursor.getColumnIndex(Item.FIRE_URL));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != cursor){
                cursor.close();
            }
        }
        return null;
    }

}
