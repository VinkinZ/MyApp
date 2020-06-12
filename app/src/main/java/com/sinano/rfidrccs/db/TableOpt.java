package com.sinano.rfidrccs.db;

import android.content.ContentValues;

/**
 * Author: Vinkin
 * Email:zwj96812@163.com
 * Date: 2020/5/24
 * Description: 表操作接口
 */
public interface TableOpt {

    /**
     * 初始化表
     */
    void initTable();

    /**
     * 获得增加的数据
     * @param bean 单条数据
     * @return 所有条目
     */
    ContentValues getInsertContentValues(Object bean);

}
