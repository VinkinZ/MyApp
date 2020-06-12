package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.sinano.rfidrccs.bean.OperationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 操作记录表操作
 */
public class OperationTable extends Table implements TableOpt {
    private static final String TAG = "OperationTable";

    public OperationTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = Table.OPERATION_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.USER_ID, Item.ITEM_TYPE_VARCHAR_NN));
        this.items.add(new Item(Item.REAGENT_CODE, Item.ITEM_TYPE_VARCHAR_NN));
        this.items.add(new Item(Item.OPERATE_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
        this.items.add(new Item(Item.CABINET_CODE, Item.ITEM_TYPE_VARCHAR_NN));
        this.items.add(new Item(Item.OPERATE_STATE, Item.ITEM_TYPE_INTEGER_NN));
        this.items.add(new Item(Item.REAL_WEIGHT, Item.ITEM_TYPE_DOUBLE));
        this.items.add(new Item(Item.MISPLACE, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.SEND_FLAG, Item.ITEM_TYPE_INTEGER_D0));
    }

    /**
     * 插入表数据
     * @param bean 单条数据
     * @return 数据
     */
    @Override
    public ContentValues getInsertContentValues(Object bean) {
        OperationBean operationBean = (OperationBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.USER_ID, operationBean.getUserId());
        contentValues.put(Item.REAGENT_CODE, operationBean.getReagentCode());
        contentValues.put(Item.CABINET_CODE, operationBean.getCabinetCode());
        contentValues.put(Item.OPERATE_STATE, operationBean.getOperateState());
        return contentValues;
    }

    /**
     * 插入表数据
     * @param bean 单条数据
     * @return 数据
     */
    public ContentValues getInsertContentValuesWeight(Object bean) {
        OperationBean operationBean = (OperationBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.USER_ID, operationBean.getUserId());
        contentValues.put(Item.REAGENT_CODE, operationBean.getReagentCode());
        contentValues.put(Item.CABINET_CODE, operationBean.getCabinetCode());
        contentValues.put(Item.OPERATE_STATE, operationBean.getOperateState());
        contentValues.put(Item.REAL_WEIGHT,operationBean.getRealWeight());
        return contentValues;
    }

    /**
     * 插入表数据
     * @param bean 单条数据
     * @return 数据
     */
    public ContentValues getInsertContentValuesMisPlace(Object bean) {
        OperationBean operationBean = (OperationBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.USER_ID, operationBean.getUserId());
        contentValues.put(Item.REAGENT_CODE, operationBean.getReagentCode());
        contentValues.put(Item.CABINET_CODE, operationBean.getCabinetCode());
        contentValues.put(Item.OPERATE_STATE, operationBean.getOperateState());
        contentValues.put(Item.MISPLACE, operationBean.getMisplace());
        return contentValues;
    }

    /**
     * 查询打包数据
     * @return 数据
     */
    public List<OperationBean> queryOperatePost(){
        TimeTable timeTable = new TimeTable();
        List<OperationBean> listSend = new ArrayList<>();
        String timeStr = timeTable.queryTime(Item.OPERATION_UPDATE_TIME);
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select distinct a._id,b.reagentStatus,b.realStatus,a.userId,a.reagentCode," +
            "a.operateTime,a.cabinetCode,a.realWeigh,a.operateState from " + tableName + " as a inner join " + Table.REAGENT_TABLE +
            " as b on a.reagentCode=b.reagentCode where a.operateTime>=? order by a.operateTime ASC", new String[]{timeStr})) {
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    int reagentStatus = cursor.getInt(cursor.getColumnIndex(Item.REAGENT_STATUS));
                    int realStatus = cursor.getInt(cursor.getColumnIndex(Item.REAL_STATUS));
                    String userId = cursor.getString(cursor.getColumnIndex(Item.USER_ID));
                    String reagentCode = cursor.getString(cursor.getColumnIndex(Item.REAGENT_CODE));
                    String operateTime = cursor.getString(cursor.getColumnIndex(Item.OPERATE_TIME));
                    String cabinetCode = cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE));
                    double realWeigh = cursor.getDouble(cursor.getColumnIndex(Item.REAL_WEIGHT));
                    int operateState = cursor.getInt(cursor.getColumnIndex(Item.OPERATE_STATE));

                    if (reagentStatus == 1) {
                        if (realStatus == 0) {
                            reagentStatus = 0;
                        } else if (realStatus == 1) {
                            reagentStatus = 2;
                        }
                    } else if (reagentStatus == 0) {
                        reagentStatus = -1;
                    }

                    OperationBean operationBean = new OperationBean(userId, reagentCode, operateTime, cabinetCode, operateState,
                                                                    reagentStatus, realStatus, realWeigh);
                    listSend.add(operationBean);
                }
                Log.d(TAG, "queryOperatePost: " + listSend.size());
                return listSend;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 适配操作记录表
     * @return 游标
     */
    public Cursor adapterOperateRecord(){
        return SQLiteManager.getInstance().db.rawQuery("select a._id,a.operateTime,c.userName,b.reagentName," +
                "a.cabinetCode,d.operateStateContent,a.realWeigh,a.misplace from " + tableName + " as a inner join " +
                Table.REAGENT_TABLE + " as b on a.reagentCode=b.reagentCode inner join " + Table.PERSON_TABLE +
                " as c on a.userId=c.userId inner join " + Table.CABINET_TABLE + " as d on a.operateState=d.operateState order by" +
                " a.operateTime DESC" , new String[]{});
    }

}
