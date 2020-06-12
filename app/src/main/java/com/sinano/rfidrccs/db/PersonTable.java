package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.sinano.rfidrccs.bean.PersonBean;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.SQLiteManager;
import com.sinano.rfidrccs.db.Table;
import com.sinano.rfidrccs.db.TableOpt;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/24
 * Description: 人员表操作
 */
public class PersonTable extends Table implements TableOpt {
    private static final String TAG = "PersonTable";

    public PersonTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = Table.PERSON_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.USER_ID, Item.ITEM_TYPE_VARCHAR_UNN));
        this.items.add(new Item(Item.UNIT, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.DEPARTMENT_NAME, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.USER_NAME, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.ARP, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.ROLE_CODE, Item.ITEM_TYPE_INTEGER_NN));
        this.items.add(new Item(Item.SAFE_SCORE, Item.ITEM_TYPE_INTEGER_NN));
        this.items.add(new Item(Item.VALID_FLAG, Item.ITEM_TYPE_INTEGER));
        this.items.add(new Item(Item.SEND_FLAG, Item.ITEM_TYPE_INTEGER_D0));
    }

    @Override
    public ContentValues getInsertContentValues(Object bean) {
        PersonBean personBean = (PersonBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.USER_ID, personBean.getUserId());
        contentValues.put(Item.UNIT, personBean.getUnit());
        contentValues.put(Item.DEPARTMENT_NAME, personBean.getDepartmentName());
        contentValues.put(Item.USER_NAME, personBean.getUserName());
        contentValues.put(Item.ARP, personBean.getArp());
        contentValues.put(Item.ROLE_CODE, personBean.getRoleCode());
        contentValues.put(Item.SAFE_SCORE, personBean.getSafeScore());
        contentValues.put(Item.VALID_FLAG, personBean.getValidFlag());
        return contentValues;
    }

    /**
     * 更新人员安全信誉分
     * @param strUserId 卡号
     * @param score 扣分值
     * @param onceFlag 扣分标志位
     */
    public void updatePersonScore(String strUserId, int score, boolean onceFlag){
        if (onceFlag) {
            int scores = queryPersonScore(strUserId);
            ContentValues cv = new ContentValues();
            cv.put(Item.SAFE_SCORE, scores - score);
            long affectRows = SQLiteManager.getInstance().db.update(tableName, cv, Item.USER_ID + "=?", new String[]{strUserId});
            Log.d(TAG, "updatePersonScore: " + affectRows);
        }
    }

    /**
     * 查询人员是否已注册
     * @param strUserId 卡号
     * @return boolean
     */
    public boolean queryPersonRegister(String strUserId) {
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, new String[]{Item.USER_ID}, Item.USER_ID + "=?",
            new String[]{strUserId}, null, null, null)) {
            Log.d(TAG, "queryPersonRegister: " + (null != cursor && cursor.moveToFirst() ? "已注册" : "未注册"));
            return null != cursor && cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询未归还试剂人员
     * @param strReagentCode 试剂编码
     * @return 卡号
     */
    public String queryPresonNoReturnReagent(String strReagentCode) {
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, new String[]{Item.REAGENT_CODE}, Item.REAGENT_CODE +
            " =? ", new String[]{strReagentCode}, null, null, Item.OPERATE_TIME + "DESC", "1")) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d(TAG, "未归还试剂人员查询成功！人员ID：" + cursor.getString(cursor.getColumnIndex(Item.USER_ID)));
                return cursor.getString(cursor.getColumnIndex(Item.USER_ID));
            } else {
                Log.d(TAG, "未归还试剂人员查询无结果！");
            }
        } catch (Exception e) {
            Log.d(TAG, "未归还试剂人员查询失败！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询人员安全信誉分值
     * @param strUserId 卡号
     * @return 分值
     */
    public int queryPersonScore(String strUserId) {
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, new String[]{Item.SAFE_SCORE},
            Item.USER_ID + "=?", new String[]{strUserId}, null, null, null)) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d("TAG", "人员安全信誉分值查询成功：" + cursor.getInt(cursor.getColumnIndex(Item.SAFE_SCORE)));
                return cursor.getInt(cursor.getColumnIndex(Item.SAFE_SCORE));
            } else {
                Log.d("TAG", "人员安全信誉分值查询无结果！");
            }
        } catch (Exception e) {
            Log.d("TAG", "人员安全信誉分值查询失败！");
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 查询人员权限等级
     * @param strUserId 卡号
     * @return 等级
     */
    public int queryPersonAuth(String strUserId) {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select a.authLevel from " + Table.ROLE_TABLE +
                " as a inner join " + tableName + " as b on b.roleCode=a.roleCode where b.userId=?", new String[]{strUserId})) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d("TAG", "人员权限等级查询成功：" + cursor.getInt(cursor.getColumnIndex(Item.AUTH_LEVEL)));
                return cursor.getInt(cursor.getColumnIndex(Item.AUTH_LEVEL));
            } else {
                Log.d("TAG", "人员权限等级查询无结果！");
            }
        } catch (Exception e) {
            Log.d("TAG", "人员权限等级查询失败！");
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 查询人员身份是否有效
     * @param strUserId 卡号
     * @return 有效：1，无效：0
     */
    public int queryPersonValid(String strUserId) {
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, new String[]{Item.VALID_FLAG},
            Item.USER_ID + "=?", new String[]{strUserId}, null, null, null)) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d("TAG", "人员有效标识查询成功：" + cursor.getInt(cursor.getColumnIndex(Item.VALID_FLAG)));
                return cursor.getInt(cursor.getColumnIndex(Item.VALID_FLAG));
            } else {
                Log.d("TAG", "人员有效标识查询无结果！");
            }
        } catch (Exception e) {
            Log.d("TAG", "人员有效标识查询失败！");
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 查询人员姓名
     * @param strUserId 卡号
     * @return 姓名
     */
    public String queryPersonName(String strUserId) {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select a.userName from " +
            tableName + " as a where a.userId=?", new String[]{strUserId})) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d("TAG", "人员姓名查询成功：" + cursor.getString(cursor.getColumnIndex(Item.USER_NAME)));
                return cursor.getString(cursor.getColumnIndex(Item.USER_NAME));
            } else {
                Log.d("TAG", "人员姓名查询无结果！");
            }
        } catch (Exception e) {
            Log.d("TAG", "人员姓名查询失败！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询人员角色
     * @param strUserId 卡号
     * @return 角色名
     */
    public String queryPersonRole(String strUserId) {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select a.roleName from " + Table.ROLE_TABLE +
            " as a inner join " + tableName + " as b on a.roleCode=b.roleCode where b.userId=?", new String[] {strUserId})) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d("TAG", "人员角色查询成功：" + cursor.getString(cursor.getColumnIndex(Item.ROLE_NAME)));
                return cursor.getString(cursor.getColumnIndex(Item.ROLE_NAME));
            } else {
                Log.d("TAG", "人员角色查询无结果！");
            }
        } catch (Exception e) {
            Log.d("TAG", "人员角色查询失败！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询管理员ID
     * @return 卡号
     */
    public String queryAdmId() {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select a.userId from " + tableName +
            " as a where a.roleCode=0 and a.validFlag=1", null)) {
            if (null != cursor && cursor.moveToFirst()) {
                Log.d("TAG", "管理员ID查询成功：" + cursor.getString(cursor.getColumnIndex(Item.USER_ID)));
                return cursor.getString(cursor.getColumnIndex(Item.USER_ID));
            } else {
                Log.d("TAG", "管理员ID查询无结果！");
            }
        } catch (Exception e) {
            Log.d("TAG", "管理员ID查询失败！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 适配人员管理表
     * @return 游标
     */
    public Cursor cursorAdapterPerson() {
        return  SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.unit,a.departmentName,a.userName,a.arp," +
                "b.roleName,b.authLevel,a.safeScore from " + tableName + " as a inner join " + Table.ROLE_TABLE +
                " as b on a.roleCode=b.roleCode where a.validFlag=1 group by a.userId", null);
    }
}
