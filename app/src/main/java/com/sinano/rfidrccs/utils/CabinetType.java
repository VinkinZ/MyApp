package com.sinano.rfidrccs.utils;

import com.sinano.rfidrccs.db.CabinetTable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/15
 * Description: 枚举试剂柜类型
 */
public enum CabinetType {
    PTX("普通型", 0),
    ZRX("阻燃型", 1),
    KFSX("抗腐蚀型", 2),
    FBX_Y("防爆无浓度型", 3),
    FBX_N("防爆型", 4),
    YDX_Y("有毒称重型", 5),
    YDX_N("有毒不称重型", 6),
    NULL("无配置", 7);

    private String cabType;
    private int cabIndex;

    CabinetType(String cabType, int cabIndex) {
        this.cabType = cabType;
        this.cabIndex = cabIndex;
    }

    public static int getCabIndex(String cabType) {
        if (null == cabType) {
            return -1;
        }
        for (CabinetType ct : CabinetType.values()){
            if (cabType.equals(ct.getCabType())) {
                return ct.getCabIndex();
            }
        }
        System.out.println("输入类型不存在！");
        return -1;
    }

    public static int getCabNum() {
        int j = 4;
        CabinetTable tCab = new CabinetTable();
        for (int n = 1; n < 5; n++){
            String typeStr = tCab.queryCabinetType(Integer.toString(n));
            if (typeStr.equals("无配置")) {
                j -= 1;
            }
        }
        return j;
    }

    public static boolean isToxicWeigh(String cabOrderNum) {
        CabinetTable tCab = new CabinetTable();
        return "有毒称重型".equals(tCab.queryCabinetType(cabOrderNum));
    }

    public String getCabType() {
        return cabType;
    }

    public int getCabIndex() {
        return cabIndex;
    }

}

