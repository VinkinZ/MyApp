package com.sinano.rfidrccs.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Vinkin
 * Email:zwj96812@163.com
 * Date: 2020/5/24
 * Description: 数据库管理类
 */
public class SQLiteManager{
    private static final String TAG = "SQLiteManager";
    private String DB_NAME;
    private int DB_VERSION = 1;
    private ArrayList<Table> tables = new ArrayList<>();
    private Context context;
    public SQLiteDatabase db;

    /**
     * 数据库帮助类
     */
    private class DBOpenHelper extends SQLiteOpenHelper {
        /**
         * 构造方法
         * @param context 上下文对象
         */
        private DBOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        /**
         * 创建数据库
         * @param db 数据库对象
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            for(int i = 0; i < tables.size(); i++) {
                String sql = getTableCreateSQLString(tables.get(i));
                db.execSQL(sql);
            }
            String sql1 = "CREATE TRIGGER delete_environment_360 AFTER INSERT ON " + Table.ENVIRONMENT_TABLE +
                    " WHEN (select count(*) from " + Table.ENVIRONMENT_TABLE + ")>360 " +
                    "BEGIN " +
                    "DELETE FROM " + Table.ENVIRONMENT_TABLE + " where " + Table.ENVIRONMENT_TABLE + "._id NOT IN (SELECT " +
                    Table.ENVIRONMENT_TABLE + "._id from " + Table.ENVIRONMENT_TABLE + " ORDER BY " + Table.ENVIRONMENT_TABLE + "._id DESC LIMIT 360);" +
                    "END;";
            db.execSQL(sql1);

            String sql2 = "CREATE TRIGGER delete_operate_1000 AFTER INSERT ON " + Table.OPERATION_TABLE +
                    " WHEN (select count(*) from "+ Table.OPERATION_TABLE + ")>1000 " +
                    "BEGIN " +
                    "DELETE FROM " + Table.OPERATION_TABLE + " where " + Table.OPERATION_TABLE + "._id NOT IN (SELECT " +
                    Table.OPERATION_TABLE + "._id from " + Table.OPERATION_TABLE + " ORDER BY " + Table.OPERATION_TABLE + "._id DESC LIMIT 1000);" +
                    "END;";
            db.execSQL(sql2);

            String sql3 = "CREATE TRIGGER delete_warning_1000 AFTER INSERT ON "+ Table.WARNING_TABLE +
                    " WHEN (select count(*) from "+ Table.WARNING_TABLE + ")>1000 " +
                    "BEGIN " +
                    "DELETE FROM " + Table.WARNING_TABLE + " where "+ Table.WARNING_TABLE + "._id NOT IN (SELECT " +
                    Table.WARNING_TABLE + "._id from "+ Table.WARNING_TABLE + " ORDER BY " + Table.WARNING_TABLE + "._id DESC LIMIT 1000);" +
                    "END;";
            db.execSQL(sql3);
        }

        /**
         * 更新数据库
         * @param db 数据库对象
         * @param oldVersion 旧版本号
         * @param newVersion 新版本号
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) {
            for (int i = 0; i < tables.size(); i++) {
                db.execSQL("DROP TABLE IF EXISTS " + getTableCreateSQLString(tables.get(i)));
            }
            onCreate(db);
        }
    }

    /**
     * 构造方法
     */
    private SQLiteManager() {
    }

    /**
     * 初始化建库工作，传入数据库名
     */
    public void initDB(Context context, String DB_Name) {
        tables.clear();
        this.context = context;
        this.DB_NAME = DB_Name;
    }

    private volatile static SQLiteManager instance = null;
    /**
     * 获取当前实例，没有则创建，单例模式
     * @return 实例对象
     */
    public static SQLiteManager getInstance() {
        if(null == instance) {
            synchronized (SQLiteManager.class) {
                if(null == instance) {
                    instance = new SQLiteManager();
                }
            }
        }
        return instance;
    }

    /**
     * 做数据库操作之前都要调用open方法
     */
    public void open() {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            ex.printStackTrace();
            exceptionHandler();
        }
        db.beginTransaction();
    }

    /**
     * 数据库文件损坏，删除异常处理
     */
    private void exceptionHandler() {
        if (null == db) {
            return;
        }
        File file = new File(db.getPath());
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    open();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 长时间不用数据库，执行close方法关闭数据库
     */
    public void close() {
        db.setTransactionSuccessful();
        db.endTransaction();
        if (db != null) {
            db.close();
            db = null;
        }
    }

    /**
     * 注册表方法：从外界传入表
     * @param table 表对象
     */
    public void registerTable(Table table) {
        tables.add(table);
    }

    /**
     * 建表语句拼接代码，遍历tables列表，每个表又可以有多个字段，字段类型不同，对应的语句不同
     * @param table 表对象
     * @return sql语句
     */
    private static String getTableCreateSQLString(Table table) {
        StringBuilder sql = new StringBuilder("create table " + table.tableName);
        sql.append("(").append(table.keyItem).append(" integer primary key autoincrement");
        for(int i = 0; i < table.items.size(); i++) {
            sql.append(",");
            switch (table.items.get(i).type) {
                case Item.ITEM_TYPE_VARCHAR:
                    sql.append(table.items.get(i).text).append(" Varchar(57)");
                    break;
                case Item.ITEM_TYPE_VARCHAR_D:
                    sql.append((table.items.get(i).text)).append(" Varchar(57) DEFAULT ''");
                    break;
                case Item.ITEM_TYPE_VARCHAR_NN:
                    sql.append((table.items.get(i).text)).append(" Varchar(57) NOT NULL");
                    break;
                case Item.ITEM_TYPE_VARCHAR_UNN:
                    sql.append((table.items.get(i).text)).append(" Varchar(57) UNIQUE NOT NULL");
                    break;
                case Item.ITEM_TYPE_INTEGER:
                    sql.append((table.items.get(i).text)).append(" Integer");
                    break;
                case Item.ITEM_TYPE_INTEGER_D0:
                    sql.append((table.items.get(i).text)).append(" Integer DEFAULT 0");
                    break;
                case Item.ITEM_TYPE_INTEGER_NN:
                    sql.append((table.items.get(i).text)).append(" Integer NOT NULL");
                    break;
                case Item.ITEM_TYPE_INTEGER_UNN:
                    sql.append((table.items.get(i).text)).append(" Integer UNIQUE NOT NULL");
                    break;
                case Item.ITEM_TYPE_DOUBLE:
                    sql.append((table.items.get(i).text)).append(" Double");
                    break;
                case Item.ITEM_TYPE_DOUBLE_D0:
                    sql.append((table.items.get(i).text)).append(" Double DEFAULT 0");
                    break;
                case Item.ITEM_TYPE_TIMESTAMP_D:
                    sql.append((table.items.get(i).text)).append(" TimeStamp DEFAULT (datetime('now','localtime'))");
                    break;
                case Item.ITEM_TYPE_TIMESTAMP_NND:
                    sql.append((table.items.get(i).text)).append(" TimeStamp NOT NULL DEFAULT (datetime('now','localtime'))");
                    break;
                case Item.ITEM_TYPE_TEXT:
                    sql.append((table.items.get(i).text)).append(" Text");
                    break;
            }
        }
        sql.append(");");
        Log.d(TAG, "getTableCreateSQLString: " + sql);
        return sql.toString();
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }
}

