package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.sinano.rfidrccs.bean.WarningBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 预警记录表操作
 */
public class WarningTable extends Table implements TableOpt {
    private static final String TAG = "WarningTable";

    public WarningTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = Table.WARNING_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.USER_ID, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.CABINET_CODE, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.WARNING_ID, Item.ITEM_TYPE_INTEGER_NN));
        this.items.add(new Item(Item.WARNING_ID_STRING, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.WARNING_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.WARNING_LEVEL, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.SEND_FLAG, Item.ITEM_TYPE_INTEGER_D0));
    }

    @Override
    public ContentValues getInsertContentValues(Object bean) {
        WarningBean warningBean = (WarningBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.USER_ID, warningBean.getUserId());
        contentValues.put(Item.CABINET_CODE, warningBean.getCabinetCode());
        contentValues.put(Item.WARNING_ID, warningBean.getWarningId());
        contentValues.put(Item.WARNING_LEVEL, warningBean.getWarningLevel());
        return contentValues;
    }

    public ContentValues getInsertContentValuesNoScore(Object bean) {
        WarningBean warningBean = (WarningBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.USER_ID, warningBean.getUserId());
        contentValues.put(Item.CABINET_CODE, warningBean.getCabinetCode());
        contentValues.put(Item.WARNING_ID, warningBean.getWarningId());
        contentValues.put(Item.WARNING_ID_STRING, warningBean.getWarningIdString());
        return contentValues;
    }

    /**
     * 添加预警信息
     * @param cv 数据
     * @param onceFlag 标志位
     */
    public void insertWarning(ContentValues cv, boolean onceFlag) {
        if (onceFlag) {
            long affectRows = SQLiteManager.getInstance().db.insert(tableName, null, cv);
            Log.d(TAG, "insertWarning: " + affectRows);
        }
    }

    /**
     * 查询打包上传扣分预警数据
     * @return 数据
     */
    public List<WarningBean> queryWarningPost() {
        TimeTable timeTable = new TimeTable();
        List<WarningBean> listSend = new ArrayList<>();
        String timeStr = timeTable.queryTime(Item.WARNING_ID_UPDATE_TIME);
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.cabinetCode,a.userId,a.warningId,a.warningTime,a.warningLevel from "
                + tableName + " as a where a.warningTime>=? and a.warningId<=6 order by a.warningTime ASC", new String[]{timeStr})) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String cabinetCode = cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE));
                    String userId = cursor.getString(cursor.getColumnIndex(Item.USER_ID));
                    int warningId = cursor.getInt(cursor.getColumnIndex(Item.WARNING_ID));
                    String warningTime = cursor.getString(cursor.getColumnIndex(Item.WARNING_TIME));
                    String warningLevel = cursor.getString(cursor.getColumnIndex(Item.WARNING_LEVEL));

                    WarningBean warningBean = new WarningBean(userId, cabinetCode, warningId, warningTime, warningLevel);
                    listSend.add(warningBean);
                }
                Log.d(TAG, "queryWarningPost: " + listSend.size());
                return listSend;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询打包上传不扣分预警数据
     * @return 数据
     */
    public List<WarningBean> queryWarningPostNoScore() {
        TimeTable timeTable = new TimeTable();
        List<WarningBean> listSend = new ArrayList<>();
        String timeStr = timeTable.queryTime(Item.WARNING_UPDATE_TIME);
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.cabinetCode,a.warningIdString,a.warningTime from "
                + tableName + " as a where a.warningTime>=? and a.warningId>6 order by a.warningTime ASC", new String[]{timeStr})) {
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    String cabinetCode = cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE));
                    String warningIdString = cursor.getString(cursor.getColumnIndex(Item.WARNING_ID_STRING));
                    String warningTime = cursor.getString(cursor.getColumnIndex(Item.WARNING_TIME));

                    WarningBean warningBean = new WarningBean(cabinetCode, warningIdString, warningTime);
                    listSend.add(warningBean);
                }
                Log.d(TAG, "queryWarningPostNoScore: " + listSend.size());
                return listSend;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 适配预警表
     * @return 游标
     */
    public Cursor adapterWarning() {
        return SQLiteManager.getInstance().db.rawQuery("select a._id,c.userName,b.warningIdContent,a.warningTime from "
                + tableName + " as a inner join " + Table.PERSON_TABLE + " as c on a.userId=c.userId inner join "
                + Table.CABINET_TABLE + " as b on a.warningId=b.warningId order by a.warningTime DESC", new String[]{});
    }

    /**
     * 适配风险查询表
     * @return 游标
     */
    public Cursor adapterWarningRisk() {
        return  SQLiteManager.getInstance().db.rawQuery("select a._id,a.warningTime,c.userName,b.warningIdContent,a.warningLevel from "
                + tableName + " as a inner join " + Table.PERSON_TABLE + " as c on a.userId=c.userId inner join "
                + Table.CABINET_TABLE + " as b on a.warningId=b.warningId order by a.warningTime DESC", new String[]{});
    }

}
