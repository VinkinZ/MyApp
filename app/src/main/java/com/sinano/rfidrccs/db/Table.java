package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;

/**
 * Author: Vinkin
 * Email:zwj96812@163.com
 * Date: 2020/5/24
 * Description: 表父类
 */
public class Table implements TableOpt{
    private final String TAG = this.getClass().getSimpleName();
    protected String tableName;
    protected String keyItem;
    protected ArrayList<Item> items = new ArrayList<>();

    public Table() {

    }

    public Table(String tableName, String keyItem) {
        this.tableName = tableName;
        this.keyItem = keyItem;
    }

    public Table(String tableName, String keyItem, ArrayList<Item> items) {
        this.tableName = tableName;
        this.keyItem = keyItem;
        this.items = items;
    }

    /**
     * 绑定一条数据
     * @param item 字段
     * @param value 值
     * @return 数据
     */
    public ContentValues getContentValues(String item, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(item, value);
        return contentValues;
    }

    /**
     * 绑定一条数据
     * @param item 字段
     * @param value 值
     * @return 数据
     */
    public ContentValues getContentValues(String item, int value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(item, value);
        return contentValues;
    }

    /**
     * 绑定一条数据
     * @param item 字段
     * @param value 值
     * @return 数据
     */
    public ContentValues getContentValues(String item, double value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(item, value);
        return contentValues;
    }

    /**
     * 绑定多条数据
     * @param items_ 字段组
     * @param values 值组
     * @return 数据
     */
    public ContentValues getContentValues(String[] items_, String[] values) {
        if (items_.length == values.length) {
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < items_.length; i++) {
                contentValues.put(items_[i], values[i]);
            }
            return contentValues;
        } else {
            Log.d(TAG, "getContentValues: " + "更新数据无法匹配!");
        }
        return null;
    }

    /**
     * 增加数据
     * @param cv 数据
     */
    public void insert(ContentValues cv) {
        long affectRows = SQLiteManager.getInstance().db.insert(tableName, null, cv);
        Log.d(TAG, "insert: " + affectRows);
    }

    /**
     * 删除全部数据
     */
    public void deleteAllItem() {
        long affectRows = SQLiteManager.getInstance().db.delete(tableName, null, null);
        Log.d(TAG, "deleteAllItem: " + affectRows);
    }

    /**
     * 根据条件删除一条数据
     * @param item 字段
     * @param content 条件
     */
    public void deleteOneByItem(String item, String content) {
        long affectRows = SQLiteManager.getInstance().db.delete(tableName, item + "=?", new String[]{content});
        Log.d(TAG, "deleteOneByItem: " + affectRows);
    }

    /**
     * 根据条件更新一条数据
     * @param item 字段
     * @param content 内容
     * @param contentValues 数据
     */
    public void updateOneByItem(String item, String content, ContentValues contentValues) {
        long affectRows = SQLiteManager.getInstance().db.update(tableName, contentValues, item + "=?", new String[]{content});
        Log.d(TAG, "updateOneByItem: " + affectRows);
    }

    // 所有表名
    public static final String PERSON_TABLE = "tPerson";
    public static final String REAGENT_TABLE = "tReagent";
    public static final String OPERATION_TABLE = "tOperation";
    public static final String ENVIRONMENT_TABLE = "tEnvironmental";
    public static final String WARNING_TABLE = "tWarning";
    public static final String ROLE_TABLE = "tRole";
    public static final String CABINET_TABLE = "tCabinet";
    public static final String PC_TABLE = "tPC";
    public static final String TIME_TABLE = "tTime";
    public static final String REAGENT_ENTRY_TABLE = "tReagentEntry";
    public static final String HELPER_TABLE = "tHelper";


    @Override
    public void initTable() {
    }

    @Override
    public ContentValues getInsertContentValues(Object bean) {
        return null;
    }
}
