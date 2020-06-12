package com.sinano.rfidrccs.db;

import android.content.ContentValues;

import com.sinano.rfidrccs.bean.RoleBean;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.Table;
import com.sinano.rfidrccs.db.TableOpt;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 角色表操作
 */
public class RoleTable extends Table implements TableOpt {

    public RoleTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = (Table.ROLE_TABLE);
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.ROLE_CODE, Item.ITEM_TYPE_INTEGER_UNN));
        this.items.add(new Item(Item.ROLE_NAME, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.AUTH_LEVEL, Item.ITEM_TYPE_INTEGER_NN));
    }

    @Override
    public ContentValues getInsertContentValues(Object bean) {
        RoleBean roleBean = (RoleBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.ROLE_CODE, roleBean.getRoleCode());
        contentValues.put(Item.ROLE_NAME, roleBean.getRoleName());
        contentValues.put(Item.AUTH_LEVEL, roleBean.getAuthLevel());
        return contentValues;
    }
}
