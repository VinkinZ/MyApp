package com.sinano.rfidrccs.db;

import android.content.ContentValues;

import com.sinano.rfidrccs.bean.ReagentEntryBean;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.Table;
import com.sinano.rfidrccs.db.TableOpt;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 试剂入库表操作
 */
public class ReagentEntryTable extends Table implements TableOpt {

    public ReagentEntryTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = Table.REAGENT_ENTRY_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.REAGENT_CODE, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.ENTRY_TIME, Item.ITEM_TYPE_TIMESTAMP_D));
    }

    @Override
    public ContentValues getInsertContentValues(Object bean) {
        ReagentEntryBean reagentEntryBean = (ReagentEntryBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put(Item.REAGENT_CODE, reagentEntryBean.getReagentCode());
        return contentValues;
    }
}
