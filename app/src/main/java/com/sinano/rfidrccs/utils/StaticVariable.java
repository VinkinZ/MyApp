package com.sinano.rfidrccs.utils;

import java.util.List;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/3 0003
 * Description:
 */
public class StaticVariable {

    public static boolean needCardNum;
    public static boolean needWeighCardNum;
    public static boolean needAdmCardNum;

    public static String cardNum;
    public static String weighCardNum;
    public static String admCardNum;

    public static String optID;
    public static String weighID;
    public static String admID;

    public static boolean needWeighRfid;
    public static boolean needScrapRfid;
    public static boolean needRfid;

    public static StringBuffer weighRfid;
    public static StringBuffer scrapRfid;
    public static StringBuffer rfid;

    public static String cabinetOrderNum;
    public static String cabinetOrderNumEntry;

    public static String optType;

    public static double reagentWeight;

    public static List<String> weighReagentCodeTaken;  // 取出的需称重的有毒试剂
    public static List<String> fireGet;
    public static String reagentReturn;
    public static String reagentEntry;

    public static boolean isEntry;
    public static boolean isScrap;
    public static boolean isToxic;
    public static boolean isToxicWeigh;

    public static void resumeWeighVariable() {
        if (null != optType) {
            optType = null;
        }
        if (needWeighRfid) {
            needWeighRfid = false;
        }
        if (null != weighRfid) {
            weighRfid = null;
        }
        if (reagentWeight != 0) {
            reagentWeight = 0;
        }
        if (null != weighReagentCodeTaken) {
            weighReagentCodeTaken = null;
        }
    }

    public static void resumeScrapVariable() {
        if (needScrapRfid) {
            needScrapRfid = false;
        }
        if (null != scrapRfid) {
            scrapRfid = null;
        }
        if (isScrap) {
            isScrap = false;
        }
    }


    public static void makeCardNumNull() {
        if (null != cardNum) {
            cardNum = null;
        }
    }

    public static void makeOptIDNull() {
        if (null != optID) {
            optID = null;
        }
    }

    public static void makeNeedCardNumFalse() {
        if (needCardNum) {
            needCardNum = false;
        }
    }

    public static void makeNeedCardNumTrue() {
        if (!needCardNum) {
            needCardNum = true;
        }
    }
}
