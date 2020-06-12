package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.sinano.rfidrccs.bean.CabinetBean;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.SQLiteManager;
import com.sinano.rfidrccs.db.Table;
import com.sinano.rfidrccs.db.TableOpt;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 试剂柜表操作
 */
public class CabinetTable extends Table implements TableOpt {
    private static final String TAG = "CabinetTable";

    public CabinetTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = Table.CABINET_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.DEPARTMENT_NAME, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.CABINET_CODE, Item.ITEM_TYPE_VARCHAR_UNN));
        this.items.add(new Item(Item.CABINET_TYPE, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.CABINET_PLACE, Item.ITEM_TYPE_INTEGER));
        this.items.add(new Item(Item.VALID_FLAG, Item.ITEM_TYPE_INTEGER));
        this.items.add(new Item(Item.BAR_CODE, Item.ITEM_TYPE_VARCHAR_D));
        this.items.add(new Item(Item.REGISTER_FLAG, Item.ITEM_TYPE_INTEGER_D0));
        this.items.add(new Item(Item.SEND_FLAG, Item.ITEM_TYPE_INTEGER_D0));
    }

    @Override
    public ContentValues getInsertContentValues(Object bean) {
        CabinetBean cabinetBean = (CabinetBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.DEPARTMENT_NAME, cabinetBean.getDepartmentName());
        contentValues.put(Item.CABINET_CODE, cabinetBean.getCabinetCode());
        contentValues.put(Item.CABINET_TYPE, cabinetBean.getCabinetType());
        contentValues.put(Item.CABINET_PLACE, cabinetBean.getCabinetPlace());
        contentValues.put(Item.VALID_FLAG, cabinetBean.getValidFlag());
        return contentValues;
    }

    /**
     * 查询柜体类型
     * @param strCabinetCode 试剂柜号
     * @return 类型
     */
    public String queryCabinetType(String strCabinetCode) {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.CABINET_TYPE + " from " + tableName +
            " where " + Item.CABINET_CODE + "=?", new String[]{strCabinetCode})) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d(TAG, "queryCabinetType: " + strCabinetCode + "号试剂柜--->" + cursor.getString(cursor.getColumnIndex(Item.CABINET_TYPE)));
                return cursor.getString(cursor.getColumnIndex(Item.CABINET_TYPE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询场所编码
     * @return 场所编码
     */
    public String queryBarCode() {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.BAR_CODE + " from " + tableName +
            " where " + Item.CABINET_CODE + "=?", new String[]{"1"})) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d(TAG, "queryCabinetType: 场所编码--->" + cursor.getString(cursor.getColumnIndex(Item.BAR_CODE)));
                return cursor.getString(cursor.getColumnIndex(Item.BAR_CODE));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询注册状态
     * @return 注册状态
     */
    public int queryRegisterFlag() {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.REGISTER_FLAG + " from " + tableName +
            " where " + Item.CABINET_CODE + "=?", new String[]{"1"})) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d(TAG, "queryRegisterFlag: 注册--->" + cursor.getInt(cursor.getColumnIndex(Item.REGISTER_FLAG)));
                return cursor.getInt(cursor.getColumnIndex(Item.REGISTER_FLAG));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询是否是有毒称重试剂柜
     * @param strCabinetCode 试剂柜号
     * @return boolean
     */
    public boolean queryCabinetToxic(String strCabinetCode) {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.CABINET_TYPE + " from " + tableName +
            " where " + Item.CABINET_CODE + "=?", new String[]{strCabinetCode})) {
            if (null != cursor && cursor.moveToFirst()) {
                boolean tof = cursor.getString(cursor.getColumnIndex(Item.CABINET_TYPE)).equals("有毒称重型");
                Log.d(TAG, "queryCabinetToxic: " + strCabinetCode + "号试剂柜" + (tof ? "是" : "不是") + "有毒称重型");
                return cursor.getString(cursor.getColumnIndex(Item.CABINET_TYPE)).equals("有毒称重型");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询柜信息，打包
     * @return 打包数据
     */
    public List<CabinetBean> queryCabinetPost() {
        List<CabinetBean> sendList = new ArrayList<>();
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.cabinetCode,a.validFlag,a.barCode,a.cabinetPlace," +
            "a.cabinetType from " + tableName + " as a ", new String[]{})) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String cabinetCode = cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE));
                    int validFLag = cursor.getInt(cursor.getColumnIndex(Item.VALID_FLAG));
                    String barCode = cursor.getString(cursor.getColumnIndex(Item.BAR_CODE));
                    int cabinetPlace = cursor.getInt(cursor.getColumnIndex(Item.CABINET_PLACE));
                    String cabinetType = cursor.getString(cursor.getColumnIndex(Item.CABINET_TYPE));

                    CabinetBean cabinetBean = new CabinetBean(cabinetCode, cabinetType, cabinetPlace, validFLag, barCode);
                    sendList.add(cabinetBean);
                }
                Log.d(TAG, "queryCabinetPost: " + sendList.size());
                return sendList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
