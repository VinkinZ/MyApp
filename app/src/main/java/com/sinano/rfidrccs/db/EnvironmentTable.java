package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.sinano.rfidrccs.bean.EnvironmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 环境信息表操作
 */
public class EnvironmentTable extends Table implements TableOpt {
    private static final String TAG = "EnvironmentTable";

    public EnvironmentTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = Table.ENVIRONMENT_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.DEPARTMENT_NAME, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.TEMPERATURE01, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.HUMIDITY01, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.VOC01, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.TEMPERATURE02, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.HUMIDITY02, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.VOC02, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.TEMPERATURE03, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.HUMIDITY03, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.VOC03, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.TEMPERATURE04, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.HUMIDITY04, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.VOC04, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.UPDATE_TIME, Item.ITEM_TYPE_TIMESTAMP_NND));
        this.items.add(new Item(Item.ENVIRONMENT_WARNING, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.SEND_FLAG, Item.ITEM_TYPE_INTEGER_D0));
    }

    @Override
    public ContentValues getInsertContentValues(Object bean) {
        EnvironmentBean environmentBean = (EnvironmentBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.DEPARTMENT_NAME, environmentBean.getDepartmentName());
        contentValues.put(Item.TEMPERATURE01, environmentBean.getTemperature01());
        contentValues.put(Item.HUMIDITY01, environmentBean.getHumidity01());
        contentValues.put(Item.VOC01, environmentBean.getVoc01());
        contentValues.put(Item.TEMPERATURE02, environmentBean.getTemperature02());
        contentValues.put(Item.HUMIDITY02, environmentBean.getHumidity02());
        contentValues.put(Item.VOC02, environmentBean.getVoc02());
        contentValues.put(Item.TEMPERATURE03, environmentBean.getTemperature03());
        contentValues.put(Item.HUMIDITY03, environmentBean.getHumidity03());
        contentValues.put(Item.VOC03, environmentBean.getVoc03());
        contentValues.put(Item.TEMPERATURE04, environmentBean.getTemperature04());
        contentValues.put(Item.HUMIDITY04, environmentBean.getHumidity04());
        contentValues.put(Item.VOC04, environmentBean.getVoc04());
        contentValues.put(Item.ENVIRONMENT_WARNING, environmentBean.getEnvironmentWarning());
        return contentValues;
    }

    /**
     * 查询打包需要上传的环境信息
     * @param i 柜号
     * @return 数据
     */
    public List<EnvironmentBean> queryEnvironmentPost(int i) {
        switch (i){
            case 1:
                String sql1 = "select distinct a._id,a.temperature01,a.humidity01,a.voc01,a.updateTime,a.environmentWarning from " + tableName + " as a where a.updateTime>=? order by a.updateTime ASC";
                return envPostHelper(sql1, Item.TEMPERATURE01, Item.HUMIDITY01, Item.VOC01);
            case 2:
                String sql2 = "select distinct a._id,a.temperature02,a.humidity02,a.voc02,a.updateTime,a.environmentWarning from " + tableName + " as a where a.updateTime>=? order by a.updateTime ASC";
                return envPostHelper(sql2, Item.TEMPERATURE02, Item.HUMIDITY02, Item.VOC02);
            case 3:
                String sql3 = "select distinct a._id,a.temperature03,a.humidity03,a.voc03,a.updateTime,a.environmentWarning from " + tableName + " as a where a.updateTime>=? order by a.updateTime ASC";
                return envPostHelper(sql3, Item.TEMPERATURE03, Item.HUMIDITY03, Item.VOC03);
            case 4:
                String sql4 = "select distinct a._id,a.temperature04,a.humidity04,a.voc04,a.updateTime,a.environmentWarning from " + tableName + " as a where a.updateTime>=? order by a.updateTime ASC";
                return envPostHelper(sql4, Item.TEMPERATURE04, Item.HUMIDITY04, Item.VOC04);
            default:
                return null;
        }
    }

    /**
     * 环境信息打包帮助方法
     * @param sql sql语句
     * @param t 温度
     * @param h 湿度
     * @param v 浓度
     * @return 数据
     */
    private List<EnvironmentBean> envPostHelper(String sql, String t, String h, String v) {
        TimeTable timeTable = new TimeTable();
        List<EnvironmentBean> listSend = new ArrayList<>();
        String strTime = timeTable.queryTime(Item.ENVIRONMENT_UPDATE_TIME);
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery(sql, new String[]{strTime})) {
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    double temperature = cursor.getDouble(cursor.getColumnIndex(t));
                    double humidity = cursor.getDouble(cursor.getColumnIndex(h));
                    double voc = cursor.getDouble(cursor.getColumnIndex(v));
                    String updateTime = cursor.getString(cursor.getColumnIndex(Item.UPDATE_TIME));
                    String environmentWarning = cursor.getString(cursor.getColumnIndex(Item.ENVIRONMENT_WARNING));

                    EnvironmentBean environmentBeanPost = new EnvironmentBean(temperature, humidity, voc, environmentWarning, updateTime);
                    listSend.add(environmentBeanPost);
                }
                Log.d(TAG, "envPostHelper: " + listSend.size());
                return listSend;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 适配环境信息表
     * @return 游标
     */
    public Cursor adapterEnvironment(){
        return SQLiteManager.getInstance().db.rawQuery("select a._id,a.updateTime,a.temperature01,a.humidity01,a.voc01," +
               "a.temperature02,a.humidity02,a.voc02,a.temperature03,a.humidity03,a.voc03,a.temperature04,a.humidity04,a.voc04 from "
               + tableName + " as a order by a.updateTime DESC",null);
    }     //适配环境信息，返回cursor类型

}


