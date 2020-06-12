package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.SQLiteManager;
import com.sinano.rfidrccs.db.Table;
import com.sinano.rfidrccs.db.TableOpt;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 更新时间表操作
 */
public class TimeTable extends Table implements TableOpt {
    private static final String TAG = "TimeTable";

    public TimeTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = Table.TIME_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.TIME_UPDATE, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.PERSON_UPDATE_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.REAGENT_UPDATE_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.OPERATION_UPDATE_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.ENVIRONMENT_UPDATE_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.WARNING_UPDATE_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.WARNING_ID_UPDATE_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.CABINET_UPDATE_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.REAGENT1_DOWNLOAD_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.REAGENT2_DOWNLOAD_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.REAGENT3_DOWNLOAD_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.REAGENT4_DOWNLOAD_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.PERSON_DOWNLOAD_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
    }


    @Override
    public ContentValues getInsertContentValues(Object bean) {
        return null;
    }

    /**
     * 查询某个表更新时间
     * @param item 字段
     * @return 时间
     */
    public String queryTime(String item) {
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, new String[]{item}, Item.TIME_UPDATE +
                "=?", new String[]{"timeUpdate"}, null, null, null)) {
            if (null != cursor && cursor.moveToFirst()) {
                String updateTime = cursor.getString(cursor.getColumnIndex(item));
                Log.d(TAG, "queryTime: " + item + " " + updateTime);
                return updateTime;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
