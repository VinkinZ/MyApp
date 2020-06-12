package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.List;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 辅助表操作
 */
public class HelperTable extends Table implements TableOpt {
    private static final String TAG = "HelperTable";

    public HelperTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = Table.HELPER_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.VALID_FLAG, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.VALID_FLAG_CONTENT, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.REAL_STATUS, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.REAL_STATUS_CONTENT, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.REAGENT_STATUS, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.REAGENT_STATUS_CONTENT, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.OPERATE_STATE, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.OPERATE_STATE_CONTENT, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.WARNING_ID, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.WARNING_ID_CONTENT, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.CABINET_TYPE, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.CABINET_TYPE_CONTENT, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.CABINET_PLACE, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.CABINET_PLACE_CONTENT, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.FIRE_EXTINGUISHER, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.FIRE_EXTINGUISHER_NAME, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.SETTING, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.MAX_AMOUNT, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.CHECK_TIME, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.POWER_OFF, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.POWER_ON, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.INITIAL_CODE, Item.ITEM_TYPE_VARCHAR));
    }

    @Override
    public ContentValues getInsertContentValues(Object bean) {
        return null;
    }

    /**
     * 初始化
     */
    public void insertHelperSql() {
        String sql1 = "insert into " + tableName + " (validFlag,validFlagContent)values(0,'无效')";
        SQLiteManager.getInstance().db.execSQL(sql1);
        String sql2 = "insert into " + tableName + " (validFlag,validFlagContent)values(1,'有效')";
        SQLiteManager.getInstance().db.execSQL(sql2);
        String sql3 = "insert into " + tableName+ " (realStatus,realStatusContent)values(0,'不在柜')";
        SQLiteManager.getInstance().db.execSQL(sql3);
        String sql4 = "insert into " + tableName+ " (realStatus,realStatusContent)values(1,'在柜')";
        SQLiteManager.getInstance().db.execSQL(sql4);
        String sql5 = "insert into " + tableName+ " (reagentStatus,reagentStatusContent)values(0,'未入库')";
        SQLiteManager.getInstance().db.execSQL(sql5);
        String sql6 = "insert into " + tableName+ " (reagentStatus,reagentStatusContent)values(1,'已入库')";
        SQLiteManager.getInstance().db.execSQL(sql6);
        String sql7 = "insert into " + tableName+ " (reagentStatus,reagentStatusContent)values(3,'报废')";
        SQLiteManager.getInstance().db.execSQL(sql7);
        String sql8 = "insert into " + tableName+ " (cabinetType,cabinetTypeContent)values(0,'普通型')";
        SQLiteManager.getInstance().db.execSQL(sql8);
        String sql9 = "insert into " + tableName+ " (cabinetType,cabinetTypeContent)values(1,'阻燃型')";
        SQLiteManager.getInstance().db.execSQL(sql9);
        String sql10 = "insert into " + tableName + " (cabinetType,cabinetTypeContent)values(2,'抗腐蚀型')";
        SQLiteManager.getInstance().db.execSQL(sql10);
        String sql63 = "insert into " + tableName + " (cabinetType,cabinetTypeContent)values(7,'防爆无浓度型')";
        SQLiteManager.getInstance().db.execSQL(sql63);
        String sql11 = "insert into " + tableName + " (cabinetType,cabinetTypeContent)values(3,'防爆型')";
        SQLiteManager.getInstance().db.execSQL(sql11);
        String sql12 = "insert into " + tableName + " (cabinetType,cabinetTypeContent)values(4,'有毒不称重型')";
        SQLiteManager.getInstance().db.execSQL(sql12);
        String sql13 = "insert into " + tableName + " (cabinetType,cabinetTypeContent)values(5,'有毒称重型')";
        SQLiteManager.getInstance().db.execSQL(sql13);
        String sql14 = "insert into " + tableName + " (cabinetType,cabinetTypeContent)values(6,'无配置')";
        SQLiteManager.getInstance().db.execSQL(sql14);
        String sql15 = "insert into " + tableName + " (cabinetPlace,cabinetPlaceContent)values(0,'仓库')";
        SQLiteManager.getInstance().db.execSQL(sql15);
        String sql16 = "insert into " + tableName + " (cabinetPlace,cabinetPlaceContent)values(1,'实验室')";
        SQLiteManager.getInstance().db.execSQL(sql16);
        String sql17 = "insert into " + tableName + " (warningId,warningIdContent)values(1,'有毒试剂未称重(扣3分)')";
        SQLiteManager.getInstance().db.execSQL(sql17);
        String sql18 = "insert into " + tableName + " (warningId,warningIdContent)values(2,'相忌试剂存储报警(扣2分)')";
        SQLiteManager.getInstance().db.execSQL(sql18);
        String sql19 = "insert into " + tableName + " (warningId,warningIdContent)values(3,'一次多取试剂超过指定数量(扣2分)')";
        SQLiteManager.getInstance().db.execSQL(sql19);
        String sql20 = "insert into " + tableName + " (warningId,warningIdContent)values(4,'购置试剂当日未存试剂柜(扣2分)')";
        SQLiteManager.getInstance().db.execSQL(sql20);
        String sql21 = "insert into " + tableName + " (warningId,warningIdContent)values(5,'取出试剂超出系统(定义时间)未还(扣1分)')";
        SQLiteManager.getInstance().db.execSQL(sql21);
        String sql22 = "insert into " + tableName + " (warningId,warningIdContent)values(6,'柜门未关(扣1分)')";
        SQLiteManager.getInstance().db.execSQL(sql22);
        String sql23 = "insert into " + tableName + " (warningId,warningIdContent)values(7,'试剂柜存储条件(温度、湿度、浓度)超标')";
        SQLiteManager.getInstance().db.execSQL(sql23);
        String sql24 = "insert into " + tableName + " (warningId,warningIdContent)values(8,'主电掉电，备电工作计时')";
        SQLiteManager.getInstance().db.execSQL(sql24);
        String sql25 = "insert into " + tableName + " (warningId,warningIdContent)values(9,'灭火器配置缺失')";
        SQLiteManager.getInstance().db.execSQL(sql25);
        String sql26 = "insert into " + tableName + " (operateState,operateStateContent)values(0,'入库')";
        SQLiteManager.getInstance().db.execSQL(sql26);
        String sql27 = "insert into " + tableName + " (operateState,operateStateContent)values(1,'错误入库')";
        SQLiteManager.getInstance().db.execSQL(sql27);
        String sql28 = "insert into " + tableName + " (operateState,operateStateContent)values(2,'领用')";
        SQLiteManager.getInstance().db.execSQL(sql28);
        String sql29 = "insert into " + tableName + " (operateState,operateStateContent)values(3,'存放')";
        SQLiteManager.getInstance().db.execSQL(sql29);
        String sql30 = "insert into " + tableName + " (operateState,operateStateContent)values(4,'错误存放')";
        SQLiteManager.getInstance().db.execSQL(sql30);
        String sql31 = "insert into " + tableName + " (operateState,operateStateContent)values(5,'报废')";
        SQLiteManager.getInstance().db.execSQL(sql31);
        String sql32 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('00','火场周围可用的灭火介质')";
        SQLiteManager.getInstance().db.execSQL(sql32);
        String sql33 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('01','干粉')";
        SQLiteManager.getInstance().db.execSQL(sql33);
        String sql34 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('02','BC干粉')";
        SQLiteManager.getInstance().db.execSQL(sql34);
        String sql35 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('03','ABC干粉')";
        SQLiteManager.getInstance().db.execSQL(sql35);
        String sql36 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('04','干粉(推)')";
        SQLiteManager.getInstance().db.execSQL(sql36);
        String sql37 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('05','洁净气体')";
        SQLiteManager.getInstance().db.execSQL(sql37);
        String sql38 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('06','1211')";
        SQLiteManager.getInstance().db.execSQL(sql38);
        String sql39 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('07','卤代烷')";
        SQLiteManager.getInstance().db.execSQL(sql39);
        String sql40 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('08','七氟丙烷')";
        SQLiteManager.getInstance().db.execSQL(sql40);
        String sql41 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('09','二氧化碳')";
        SQLiteManager.getInstance().db.execSQL(sql41);
        String sql42 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('10','二氧化碳(推)')";
        SQLiteManager.getInstance().db.execSQL(sql42);
        String sql43 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('11','水基型')";
        SQLiteManager.getInstance().db.execSQL(sql43);
        String sql44 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('12','泡沫')";
        SQLiteManager.getInstance().db.execSQL(sql44);
        String sql45 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('13','蛋白泡沫')";
        SQLiteManager.getInstance().db.execSQL(sql45);
        String sql46 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('14','水成膜泡沫')";
        SQLiteManager.getInstance().db.execSQL(sql46);
        String sql47 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('15','氟蛋白泡沫')";
        SQLiteManager.getInstance().db.execSQL(sql47);
        String sql48 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('16','抗溶泡沫')";
        SQLiteManager.getInstance().db.execSQL(sql48);
        String sql49 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('17','雾状水')";
        SQLiteManager.getInstance().db.execSQL(sql49);
        String sql50 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('18','强化水系')";
        SQLiteManager.getInstance().db.execSQL(sql50);
        String sql51 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('19','水')";
        SQLiteManager.getInstance().db.execSQL(sql51);
        String sql52 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('20','砂土')";
        SQLiteManager.getInstance().db.execSQL(sql52);
        String sql53 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('21','干燥石墨粉末')";
        SQLiteManager.getInstance().db.execSQL(sql53);
        String sql54 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('22','干燥氯化钠粉末、碳酸钠粉末、碳酸钙粉末')";
        SQLiteManager.getInstance().db.execSQL(sql54);
        String sql55 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('23','碱性物质')";
        SQLiteManager.getInstance().db.execSQL(sql55);
        String sql56 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('24','灭火毯')";
        SQLiteManager.getInstance().db.execSQL(sql56);
        String sql57 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('25','惰性材料')";
        SQLiteManager.getInstance().db.execSQL(sql57);
        String sql58 = "insert into " + tableName + " (fireExtinguisher,fireExtinguisherName)values('26','酸碱灭火器')";
        SQLiteManager.getInstance().db.execSQL(sql58);
        String sql59 = "insert into " + tableName + " (setting,maxAmount,checkTime,powerOff,powerOn,initialCode)values('设置','3','23:00','23:00','23:00','')";
        SQLiteManager.getInstance().db.execSQL(sql59);
        String sql60 = "insert into " + tableName + " (warningId,warningIdContent)values(10,'试剂柜温度超标')";
        SQLiteManager.getInstance().db.execSQL(sql60);
        String sql61 = "insert into " + tableName + " (warningId,warningIdContent)values(11,'试剂柜湿度超标')";
        SQLiteManager.getInstance().db.execSQL(sql61);
        String sql62 = "insert into " + tableName + " (warningId,warningIdContent)values(12,'试剂柜气体浓度超标')";
        SQLiteManager.getInstance().db.execSQL(sql62);
    }

    /**
     * 查询灭火器
     * @param stringList 灭火器编码
     * @return 游标
     */
    public Cursor queryFireExtinguisher(List<String> stringList){
        try {
            String placeHolder = makePlaceholders(stringList.size());
            String sql = "select a._id,a.fireExtinguisherName from " + tableName + " as a where a.fireExtinguisher in (" + placeHolder + ")";
            StringBuilder args = new StringBuilder();
            for (int i = 0; i < stringList.size(); i++) {
                args.append(stringList.get(i)).append(",");
            }
            if (args.length() > 1) {
                args = new StringBuilder(args.substring(0, args.length() - 1));
            }
            return SQLiteManager.getInstance().db.rawQuery(sql, args.toString().split(","));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询灭火器帮助方法
     * @param len list长度
     * @return string
     */
    private String makePlaceholders(int len) {
        if (len < 1) {
            throw new RuntimeException("No Placeholders");
        } else {
            StringBuilder temp = new StringBuilder(len * 2 - 1);
            temp.append("?");
            for (int i = 1; i < len; i++) {
                temp.append(",?");
            }
            return temp.toString();
        }
    }

    /**
     * 查询相关设置
     * @param item 字段
     * @return 时间
     */
    public String queryInHelper(String item) {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select " + item + " from " + tableName + " where " +
                Item.SETTING + "=?", new String[]{"设置"})) {
            if (null != cursor && cursor.moveToFirst()) {
                String result = cursor.getString(cursor.getColumnIndex(item));
                Log.d(TAG, "queryInHelper: " +  item + " " + result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
