package com.sinano.rfidrccs.comm;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30 15:07
 * Description: 握手协议，指令发送部分
 */
public class Cmd {

    public static final String T_H_POLL_1 = "0104AA00010203DD";      // 试剂柜1温湿度轮询
    public static final String T_H_POLL_2 = "0204AA00010203DD";      // 试剂柜2温湿度轮询
    public static final String T_H_POLL_3 = "0304AA00010203DD";      // 试剂柜3温湿度轮询
    public static final String T_H_POLL_4 = "0404AA00010203DD";      // 试剂柜4温湿度轮询

    public static final String T_H_POLL_1_ = "0104BB00010203DD";     // 试剂柜1温湿度轮询
    public static final String T_H_POLL_2_ = "0204BB00010203DD";     // 试剂柜2温湿度轮询
    public static final String T_H_POLL_3_ = "0304BB00010203DD";     // 试剂柜3温湿度轮询
    public static final String T_H_POLL_4_ = "0404BB00010203DD";     // 试剂柜4温湿度轮询

    public static final String VOC_POLL_1 = "0103AA00010203DD";      // 试剂柜1浓度轮询
    public static final String VOC_POLL_2 = "0203AA00010203DD";      // 试剂柜2浓度轮询
    public static final String VOC_POLL_3 = "0303AA00010203DD";      // 试剂柜3浓度轮询
    public static final String VOC_POLL_4 = "0403AA08010203DD";      // 试剂柜4浓度轮询

    public static final String OPEN_DOOR_1 = "0102AA00010203DD";     // 试剂柜1正常开门
    public static final String OPEN_DOOR_2 = "0202AA00010203DD";     // 试剂柜2正常开门
    public static final String OPEN_DOOR_3 = "0302AA00010203DD";     // 试剂柜3正常开门
    public static final String OPEN_DOOR_4 = "0402AA00010203DD";     // 试剂柜4正常开门

    public static final String OPEN_DOOR_1E = "0102BB00010203DD";    // 试剂柜1入库开门
    public static final String OPEN_DOOR_2E = "0202BB00010203DD";    // 试剂柜2入库开门
    public static final String OPEN_DOOR_3E = "0302BB00010203DD";    // 试剂柜3入库开门
    public static final String OPEN_DOOR_4E = "0402BB00010203DD";    // 试剂柜4入库开门

    public static final String GET_RFID_1 = "0108AA00010203DD";      // 试剂柜1索要试剂
    public static final String GET_RFID_2 = "0208AA00010203DD";      // 试剂柜2索要试剂
    public static final String GET_RFID_3 = "0308AA00010203DD";      // 试剂柜3索要试剂
    public static final String GET_RFID_4 = "0408AA00010203DD";      // 试剂柜4索要试剂

    public static final String GET_RFID_1L1 = "0108AA01010203DD";   // 试剂柜1索要试剂_Level1
    public static final String GET_RFID_2L1 = "0208AA01010203DD";   // 试剂柜2索要试剂_Level1
    public static final String GET_RFID_3L1 = "0308AA01010203DD";   // 试剂柜3索要试剂_Level1
    public static final String GET_RFID_4L1 = "0408AA01010203DD";   // 试剂柜4索要试剂_Level1

    public static final String GET_RFID_1L2 = "0108AA02010203DD";   // 试剂柜1索要试剂_Level2
    public static final String GET_RFID_2L2 = "0208AA02010203DD";   // 试剂柜2索要试剂_Level2
    public static final String GET_RFID_3L2 = "0308AA02010203DD";   // 试剂柜3索要试剂_Level2
    public static final String GET_RFID_4L2 = "0408AA02010203DD";   // 试剂柜4索要试剂_Level2

    public static final String GET_RFID_1L3 = "0108AA03010203DD";   // 试剂柜1索要试剂_Level3
    public static final String GET_RFID_2L3 = "0208AA03010203DD";   // 试剂柜2索要试剂_Level3
    public static final String GET_RFID_3L3 = "0308AA03010203DD";   // 试剂柜3索要试剂_Level3
    public static final String GET_RFID_4L3 = "0408AA03010203DD";   // 试剂柜4索要试剂_Level3

    public static final String IC_OK = "7C01017E";        // 刷卡器OK
    public static final String IC_NOTOK = "7C00017E";     // 刷卡器NOT_OK

    public static final String OPEN_FAN = "0501AA00010203DD";     // 打开风机
    public static final String CLOSE_FAN = "0500AA00010203DD";    // 关闭风机

    public static final String GET_WEIGH_RFID = "0109AA00010203DD";   // 索要称重试剂

    public static final String DISABLE_INFO = "0906AA09010203DD";   // 通知STM32将当前可发送数据标志位置为否
}
