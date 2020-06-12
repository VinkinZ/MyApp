package com.sinano.rfidrccs.utils;

import com.sinano.rfidrccs.bean.CabinetBean;
import com.sinano.rfidrccs.bean.EnvironmentBean;
import com.sinano.rfidrccs.bean.OperationBean;
import com.sinano.rfidrccs.bean.ReagentBean;
import com.sinano.rfidrccs.bean.WarningBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sinano.rfidrccs.ui.act.MainActivity.controllerCode;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30 15:54
 * Description: 数据转换工具类
 * 温湿度、浓度、标签、指令等数据处理
 * 将数据库得到的最新需上传数据转换成webConnectionUtils所需数据(Bean--->Map)
 */
public class DataUtil {
    private static double tem_ins, hum_ins;

    // 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
    private static boolean isOdd(int num) {
        return num == 1;
    }

    // Hex 字符串转int
    private static int strHexToInt(String strHex) {
        return Integer.parseInt(strHex, 16);
    }

    // Hex int转字符串
    private static String intHexToStr(int intHex){
        return Integer.toHexString(intHex);
    }

    // Hex 字符串转byte
    private static byte strHexToByte(String strHex) {
        return (byte) Integer.parseInt(strHex, 16);
    }

    // 1个字节转2个Hex字符串
    private static String byte2TwoHex(Byte byte_) {
        return String.format("%02x", byte_).toUpperCase();
    }

    // 温度16进制转换为温度值
    public static double temTrans(String hex, int radix){
        String hex_tem = hex.substring(6, 10);
        double tem = Integer.parseInt(hex_tem,radix) / 10.0;
        if(tem < 60.0){
            tem_ins = tem;
            return tem;
        }else{
            return tem_ins;
        }
    }

    public static double temTrans_(String hex, int radix){
        String hex_tem = hex.substring(6, 10);
        double dec = Integer.parseInt(hex_tem, radix);
        double analog = dec * 20 / 3276;
        double tem = (7.5 * (analog - 4)) - 40;
        BigDecimal bigDecimal = new BigDecimal(tem);
        tem = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        if(tem < 60.0){
            tem_ins = tem;
            return tem;
        }else{
            return tem_ins;
        }
    }

    // 湿度16进制转换为湿度值
    public static double humTrans(String hex, int radix){
        String hex_hum = hex.substring(10, 14);
        double hum = Integer.parseInt(hex_hum, radix) / 10.0;
        if(hum < 100.0){
            hum_ins = hum;
            return hum;
        }else{
            return hum_ins;
        }
    }

    public static double humTrans_(String hex, int radix){
        String hex_hum = hex.substring(10, 14);
        double dec = Integer.parseInt(hex_hum, radix);
        double analog = dec * 20 / 3276;
        double hum = 100 * (analog - 4) / 16;
        BigDecimal bigDecimal = new BigDecimal(hum);
        hum = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        if(hum < 100.0){
            hum_ins = hum;
            return hum;
        }else{
            return hum_ins;
        }

    }

    // 浓度16进制转换为浓度值
    public static double vocTrans(String hex, int radix){
        String hex_voc = hex.substring(6, 10);
        return Integer.parseInt(hex_voc, radix) / 1000.0;
    }

    // 字节数组转Hex字符串
    public static String byteArrToStrHex(byte[] bytes) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte byte_ : bytes) {
            strBuilder.append(byte2TwoHex(byte_));
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }

    // 字节数组转Hex字符串，可选长度
    public static String byteArrToStrHex(byte[] bytes, int offset, int byteCount) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = offset; i < byteCount; i++) {
            strBuilder.append(byte2TwoHex(bytes[i]));
        }
        return strBuilder.toString();
    }

    // Hex字符串转字节数组
    public static byte[] strHexToByteArr(String strHex) {
        byte[] result;
        int hexlen = strHex.length();
        if (isOdd(hexlen)) {
            hexlen++;
            result = new byte[(hexlen / 2)];
            strHex = "0" + strHex;
        } else {
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = strHexToByte(strHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    // 按照2个长度切割字符串
    private static List<String> getDivLines(String inputString) {
        List<String> divList = new ArrayList<>();
        int remainder = inputString.length() % 2;
        int count = inputString.length() / 2;
        for (int index = 0; index < count; index++) {
            String childStr = inputString.substring(index * 2, (index + 1) * 2);
            divList.add(childStr);
        }
        if (remainder > 0) {
            String cStr = inputString.substring(count * 2);
            divList.add(cStr);
        }
        return divList;
    }

    // 计算长度，两个字节长度
    private static String twoByte(String val) {
        if (val.length() > 4) {
            val = val.substring(0, 4);
        } else {
            int l = 4 - val.length();
            StringBuilder valBuilder = new StringBuilder(val);
            for (int i = 0; i < l; i++) {
                valBuilder.insert(0, "0");
            }
            val = valBuilder.toString();
        }
        return val;
    }

    // 校验和
    public static String sum(String cmd) {
        List<String> cmdList = getDivLines(cmd);
        int sumInt = 0;
        for (String c : cmdList) {
            sumInt += strHexToInt(c);
        }
        String sum = intHexToStr(sumInt);
        sum = twoByte(sum);
        cmd += sum;
        return cmd.toUpperCase();
    }


    public static List<String> rfidTrans(String rfidString, int length){
        rfidString = rfidString.substring(2, rfidString.length() - 12);
        int rfidStringLength = rfidString.length();
        int listSize = rfidStringLength / length;
        List<String> rfidList0 = new ArrayList<>();
        List<String> rfidList = new ArrayList<>();
        for(int i = 0; i < listSize; i++){
            rfidList0.add(rfidString.substring(i * length, (i+1) * length));
        }
        for(String str : rfidList0){
            rfidList.add(str.substring(18,42));
        }
        return rfidList;
    }


    public static Map data2Map(String controllerCode, Integer validFlag){
        Map<String,Object> map = new HashMap<>();
        map.put("controllerCode", controllerCode);
        map.put("validFlag", validFlag);
        return map;
    }

    private static String getLeft(String left){
        switch (left) {
            case "普通型":
                return "0";
            case "阻燃型":
                return "1";
            case "抗腐蚀型":
                return "2";
            case "防爆型":
                return "3";
            case "防爆无浓度型":
                return "3";
            case "有毒称重型":
                return "4";
            case "有毒不称重型":
                return "5";
        }
        return "6";
    }

    public static List cabBeanList2MapList(List<CabinetBean> list_cab0) {
        try {
            List<Map<String,Object>> list_cab = new ArrayList<>();
            for (CabinetBean cabinetBean : list_cab0) {
                Map<String, Object> map = new HashMap<>();
                if (!cabinetBean.getCabinetType().equals("无配置")) {
                    map.put("containerCode", controllerCode + cabinetBean.getCabinetCode());
                    map.put("validFlag", cabinetBean.getValidFlag());
                    map.put("barCode", cabinetBean.getBarCode());
                    map.put("type", 200);
                    map.put("place", cabinetBean.getCabinetPlace());
                    map.put("left", getLeft(cabinetBean.getCabinetType()));
                    list_cab.add(map);
                }
            }
            return list_cab;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List regBeanList2MapList(List<ReagentBean> list_reg0) {
        try {
            List<Map<String,Object>> list_reg = new ArrayList<>();
            for (ReagentBean reagentBean : list_reg0) {
                Map<String, Object> map = new HashMap<>();
                map.put("containerCode", controllerCode + reagentBean.getCabinetCode());
                map.put("status", reagentBean.getReagentStatus());
                map.put("realstatus", reagentBean.getRealStatus());
                map.put("reagentCode", reagentBean.getReagentCode());
                map.put("idcard", reagentBean.getUserId());
                map.put("updateTime", reagentBean.getOperateTime());
                if (reagentBean.getResidualAmount() != 0) {
                    map.put("remainder", reagentBean.getResidualAmount());
                }
                list_reg.add(map);
            }
            return list_reg;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List optBeanList2MapList(List<OperationBean> list_opt0) {
        try {
            List<Map<String,Object>> list_opt = new ArrayList<>();
            for (OperationBean operationBean : list_opt0) {
                Map<String, Object> map = new HashMap<>();
                map.put("containerCode", controllerCode + operationBean.getCabinetCode());
                map.put("status", operationBean.getReagentStatus());
                map.put("realstatus", operationBean.getRealStatus());
                map.put("reagentCode", operationBean.getReagentCode());
                map.put("idcard", operationBean.getUserId());
                map.put("updateTime", operationBean.getOperateTime());
                map.put("operateState", operationBean.getOperateState());
                if (operationBean.getRealWeight() != 0) {
                    map.put("remainder", operationBean.getRealWeight());
                }
                list_opt.add(map);
            }
            return list_opt;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List warnStrBeanList2MapList(List<WarningBean> list_warn0) {
        try {
            List<Map<String,Object>> list_warn = new ArrayList<>();
            for (WarningBean warningBean : list_warn0) {
                Map<String, Object> map = new HashMap<>();
                if (null == warningBean.getCabinetCode() || warningBean.getCabinetCode().equals("")) {
                    map.put("containerCode", controllerCode + "1");
                } else {
                    map.put("containerCode", controllerCode + warningBean.getCabinetCode());
                }
                map.put("warningId", warningBean.getWarningIdString());
                map.put("warningTime", warningBean.getWarningTime());
                list_warn.add(map);
            }
            return list_warn;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static int getWarningLevel(String str){
        int warningLevel = 0;
        switch (str) {
            case "I":
                warningLevel = 1;
                break;
            case "II":
                warningLevel = 2;
                break;
            case "III":
                warningLevel = 3;
                break;
        }
        return warningLevel;
    }

    private static int getWarningPoint(String str){
        int warningPoint = 0;
        switch (str) {
            case "I":
                warningPoint = 3;
                break;
            case "II":
                warningPoint = 2;
                break;
            case "III":
                warningPoint = 1;
                break;
        }
        return warningPoint;
    }

    public static List warnIdBeanList2MapList(List<WarningBean> list_warn0) {
        try {
            List<Map<String,Object>> list_warn = new ArrayList<>();
            for (WarningBean warningBean : list_warn0) {
                Map<String, Object> map = new HashMap<>();
                map.put("containerCode", controllerCode + warningBean.getCabinetCode());
                map.put("warningId", warningBean.getWarningId());
                map.put("warningTime", warningBean.getWarningTime());
                map.put("idcard", warningBean.getUserId());
                map.put("warningLevel", getWarningLevel(warningBean.getWarningLevel()));
                map.put("warningPoint", getWarningPoint(warningBean.getWarningLevel()));
                list_warn.add(map);
            }
            return list_warn;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String getEnvFlag(double tem,double hum,double voc){        //获取环境信息的预警信息字段
        String[] flag = {"0","0","0","0","0"};
        try {
            if (tem > 60) {
                flag[1] = "1";
            }
            if (hum > 80) {
                flag[3] = "1";
            }
            if (voc > 2) {
                flag[4] = "1";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "00000";
        }
        return flag[0] + flag[1] + flag[2] + flag[3] + flag[4];
    }

    public static List envBeanList2MapList(List<EnvironmentBean> list_env0){
        try {
            List<Map<String,Object>> list_env = new ArrayList<>();
            for (EnvironmentBean environmentBean : list_env0) {
                Map<String, Object> map = new HashMap<>();
                map.put("Temp", environmentBean.getTemperature01());
                map.put("Humidity", environmentBean.getHumidity01());
                map.put("voc1", environmentBean.getVoc01());
                map.put("Flag", getEnvFlag(environmentBean.getTemperature01(), environmentBean.getHumidity01(), environmentBean.getVoc01()));
                map.put("updateTime", environmentBean.getUpdateTime());
                list_env.add(map);
            }
            return list_env;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
