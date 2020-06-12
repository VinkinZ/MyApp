package com.sinano.rfidrccs.comm;


/**
 * @author by J.mQ on 2019/6/1,Fivo@.co.,ltd.
 */

import android.content.Context;
import android.util.Log;


import com.sinano.rfidrccs.db.CabinetTable;
import com.sinano.rfidrccs.db.ReagentTable;
import com.sinano.rfidrccs.ui.frag.CabFrag1;
import com.sinano.rfidrccs.utils.DataUtil;
import com.sinano.rfidrccs.utils.StaticVariable;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30 15:07
 * Description: STM32串口数据流
 */
public class SerialPortSTM32 {
    private SerialPort serialPort = null;
    private static InputStream inputStream32 = null;
    private static OutputStream outputStream32 = null;
    private ReceiveThread32 receiveThread32 = null;
    private boolean isOpen = false;
    private Context context;
    private ReagentTable tReg = new ReagentTable();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String cab1Type, cab2Type, cab3Type, cab4Type;

    public static HashMap<String,Double> hm_single = new HashMap<>();
    public static HashMap<String,Double> hm_single_ = new HashMap<>();

    public SerialPortSTM32(Context context) {
        this.context = context;
        CabinetTable tCab = new CabinetTable();
        cab1Type = tCab.queryCabinetType("1");
        cab2Type = tCab.queryCabinetType("2");
        cab3Type = tCab.queryCabinetType("3");
        cab4Type = tCab.queryCabinetType("4");
    }

    public void openSerialPortSTM32() {//打开串口
        Log.i("SerialPortSTM32","open");
        try {
            serialPort = new SerialPort(new File("dev/ttyS2"),115200,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream32 = serialPort.getInputStream();
        outputStream32 = serialPort.getOutputStream();
        isOpen = true;
        getserial();
    }

    public void closeSerialPortSTM32() {
        Log.i("SerialPortSTM32","close");
        if (null != inputStream32) {
            try {
                inputStream32.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != outputStream32) {
            try {
                outputStream32.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        serialPort.close();
        isOpen = false;
    }

    public static void sendOrder(String data) {
        byte[] sendData = DataUtil.strHexToByteArr(data);
        try {
            outputStream32.write(sendData);
            outputStream32.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getserial() {
        if (null == receiveThread32){
            receiveThread32 = new ReceiveThread32();
        }
        receiveThread32.start();
    }

    private class ReceiveThread32 extends Thread{
        @Override
        public void run(){
            super.run();
            while(isOpen){
                byte[] readData = new byte[1024];
                int size = 0;
                try {
                    size = inputStream32.read(readData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (size > 0){
                        String readString = DataUtil.byteArrToStrHex(readData, 0,  size);
                        System.out.println("readString: " + readString);
                        // 1号柜
                        if (readString.startsWith("01")) {
                            if (readString.startsWith("010104")) {
                                // STM1关门警告，EventBus发送到对应CabFrag
                                System.out.println("door1_warning" + readString);
                                EventBus.getDefault().post("warning_1","close_warning_1");
                            } else if (readString.startsWith("010204")) {
                                // STM1关门信号，EventBus发送到Main
                                System.out.println("door1_closed: " + readString);
                                if (StaticVariable.isEntry) {
                                    sendOrder(Cmd.GET_RFID_1L3);
                                    EventBus.getDefault().post("ok","get_rfid_1");
                                    if (!cab1Type.equals("普通型")) {
                                        EventBus.getDefault().post("change_opt","opt_rfid_scan");
                                    } else {
                                        EventBus.getDefault().post("change_opt","opt_null");
                                    }
                                    StaticVariable.isEntry = false;
                                } else {
                                    sendOrder(getScanLevel1(tReg.queryReagentCount("1")));
                                    EventBus.getDefault().post("ok","get_rfid_1");
                                    if (!cab1Type.equals("普通型")) {
                                        EventBus.getDefault().post("change_opt","opt_rfid_scan");
                                    } else {
                                        EventBus.getDefault().post("change_opt","opt_null");
                                    }
                                }
//                                sendOrder(Cmd.GET_RFID_1);
//                                EventBus.getDefault().post("ok","get_rfid_1");
//                                EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                            } else if (readString.startsWith("0102AA")) {
                                CabFrag1.doorOpened = true;
                            } else if (readString.startsWith("0102BB")) {
                                inCounter_C_O_E_F.opendoor_in1suc = true;
                            }
                            else if (readString.startsWith("010304") & readString.length() == 16){
                                // STM1浓度
                                double concen_1d = DataUtil.vocTrans(readString,16);
                                hm_single.put("c1",concen_1d);
                                System.out.println("concen1:    " + concen_1d);
                            }else if (readString.startsWith("010404") & readString.length() == 18){
                                //*******************STM1温湿度*****************************
                                double tem_1d = DataUtil.temTrans(readString,16);
                                hm_single.put("t1",tem_1d);

                                double hum_1d = DataUtil.humTrans(readString,16);
                                hm_single.put("h1",hum_1d);

                            } else if (readString.startsWith("010004") & readString.length() == 18){
                                double tem_1d = DataUtil.temTrans_(readString,16);
                                hm_single.put("t1",tem_1d);

                                    double hum_1d = DataUtil.humTrans_(readString,16);
                                hm_single.put("h1",hum_1d);
                            }else if(readString.startsWith("010504") & readString.length() == 16){
                                EventBus.getDefault().post("ok","OperationIDNull");
                                EventBus.getDefault().post("ok","auto_close");
                            }
                            else if(readString.startsWith("01A0")){
                                //*******************一号柜试剂******************************
                                RFID_Scan_C_O_E_F.sb_rfid_C_O_E_F.append(readString);
                            }
                        }

                        /**********************************************二号柜**********************************************/
                        else if(readString.startsWith("02")){
                            if(readString.startsWith("020104")){
                                //******************STM2关门警告**************************
                                Log.i("warning_2","Receive");
                                EventBus.getDefault().post("warning_2","close_warning_2");
                            }else if(readString.startsWith("020204")){
                                //********************STM2关门信号************************
                                Log.i("door2_close","Receive");
                                if (StaticVariable.isEntry){
                                    sendOrder(Cmd.GET_RFID_2L3);
                                    EventBus.getDefault().post("ok","get_rfid_2");
                                    if(!type02.equals("普通型")) {
                                        EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                                    }else{
                                        EventBus.getDefault().post("ok","OperationIDNull");
                                    }
                                    incounter2 = false;
                                }else {
                                    sendOrder(getScanLevel2(dbhelper.queryReagentCount("2")));
                                    EventBus.getDefault().post("ok","get_rfid_2");
                                    if(!type02.equals("普通型")) {
                                        EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                                    }else{
                                        EventBus.getDefault().post("ok","OperationIDNull");
                                    }
                                }
//                                sendOrder(Cmd.GET_RFID_2);
//                                EventBus.getDefault().post("ok","get_rfid_2");
//                                EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                            }else if(readString.startsWith("0202AA")){
                                Rfidfragment2.opendoor2_suc = true;
                            }else if(readString.startsWith("0202BB")){
                                inCounter_C_O_E_F.opendoor_in2suc = true;
                            }
                            else if (readString.startsWith("020304") & readString.length() == 16){
                                //*******************STM2浓度****************************
                                double concen_2d = DataUtil.vocTrans(readString,16);
                                hm_single.put("c2",concen_2d);
                            }else if (readString.startsWith("020404") & readString.length() == 18){
                                //*******************STM2温湿度***************************
                                double tem_2d = DataUtil.temTrans(readString,16);
                                hm_single.put("t2",tem_2d);

                                double hum_2d = DataUtil.humTrans(readString,16);
                                hm_single.put("h2",hum_2d);
                            }else if (readString.startsWith("020004") & readString.length() == 18){
                                double tem_2d = DataUtil.temTrans_(readString,16);
                                hm_single.put("t2",tem_2d);

                                double hum_2d = DataUtil.humTrans_(readString,16);
                                hm_single.put("h2",hum_2d);
                            }else if(readString.startsWith("020504") & readString.length() == 16){
                                EventBus.getDefault().post("ok","auto_close");
                            }
                            else if(readString.startsWith("02A0")){
                                //*******************二号柜试剂******************************
                                RFID_Scan_C_O_E_F.sb_rfid_C_O_E_F.append(readString);
                            }
                        }

                        /**********************************************三号柜**********************************************/
                        else if(readString.startsWith("03")){
                            if(readString.startsWith("030104")){
                                //******************STM3关门警告**************************
                                Log.i("warning_3","Receive");
                                EventBus.getDefault().post("warning_3","close_warning_3");
                            }else if(readString.startsWith("030204")){
                                //*******************STM3关门信号*************************
                                Log.i("door3_close","Receive");

                                if (incounter3){
                                    sendOrder(Cmd.GET_RFID_3l3);
                                    EventBus.getDefault().post("ok","get_rfid_3");
                                    if(!type03.equals("普通型")) {
                                        EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                                    }else{
                                        EventBus.getDefault().post("ok","OperationIDNull");
                                    }
                                    incounter3 = false;
                                }else {
                                    sendOrder(getScanLevel3(dbhelper.queryReagentCount("3")));
                                    EventBus.getDefault().post("ok","get_rfid_3");
                                    if(!type03.equals("普通型")) {
                                        EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                                    }else{
                                        EventBus.getDefault().post("ok","OperationIDNull");
                                    }
                                }
//                                sendOrder(Cmd.GET_RFID_3);
//                                EventBus.getDefault().post("ok","get_rfid_3");
//                                EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                            }else if(readString.startsWith("0302AA")){
                                Rfidfragment3.opendoor3_suc = true;
                            }else if(readString.startsWith("0302BB")){
                                inCounter_C_O_E_F.opendoor_in3suc = true;
                            }
                            else if (readString.startsWith("030304") & readString.length() == 16){
                                //*******************STM3浓度****************************
                                double concen_3d = DataUtil.concen_trans(readString,16);
                                hm_single.put("c3",concen_3d);
                            }else if (readString.startsWith("030404") & readString.length() == 18){
                                //*******************STM3温湿度****************************
                                double tem_3d = DataUtil.tem_trans(readString,16);
                                hm_single.put("t3",tem_3d);

                                double hum_3d = DataUtil.hum_trans(readString,16);
                                hm_single.put("h3",hum_3d);
                            }else if (readString.startsWith("030004") & readString.length() == 18){
                                double tem_3d = DataUtil.tem_trans_(readString,16);
                                hm_single.put("t3",tem_3d);

                                double hum_3d = DataUtil.hum_trans_(readString,16);
                                hm_single.put("h3",hum_3d);
                            } else if(readString.startsWith("030504") & readString.length() == 16){
                                EventBus.getDefault().post("ok","auto_close");
                            }
                            else if(readString.startsWith("03A0")){
                                //*******************三号柜试剂******************************
                                RFID_Scan_C_O_E_F.sb_rfid_C_O_E_F.append(readString);
                            }
                        }

                        /**********************************************四号柜**********************************************/
                        else if(readString.startsWith("04")){
                            if(readString.startsWith("040104")){
                                //******************STM4关门警告**************************
                                Log.i("warning_4","Receive");
                                EventBus.getDefault().post("warning_4","close_warning_4");
                            }else if(readString.startsWith("040204")){
                                //*******************STM4关门信号*************************
                                Log.i("door4_close","Receive");

                                if (incounter4){
                                    sendOrder(Cmd.GET_RFID_4l3);
                                    EventBus.getDefault().post("ok","get_rfid_4");
                                    if(!type04.equals("普通型")) {
                                        EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                                    }else{
                                        EventBus.getDefault().post("ok","OperationIDNull");
                                    }
                                    incounter4 = false;
                                }else {
                                    sendOrder(getScanLevel4(dbhelper.queryReagentCount("4")));
                                    EventBus.getDefault().post("ok","get_rfid_4");
                                    if(!type04.equals("普通型")) {
                                        EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                                    }else{
                                        EventBus.getDefault().post("ok","OperationIDNull");
                                    }
                                }
//                                sendOrder(Cmd.GET_RFID_4);
//                                EventBus.getDefault().post("ok","get_rfid_4");
//                                EventBus.getDefault().post("ok","OperationRFID_Scan");//更改操作提示
                            }else if(readString.startsWith("0402AA")){
                                Rfidfragment4.opendoor4_suc = true;
                            }else if(readString.startsWith("0402BB")){
                                inCounter_C_O_E_F.opendoor_in4suc = true;
                            }
                            else if (readString.startsWith("040304") & readString.length() == 16){
                                //*******************STM4浓度****************************
                                double concen_4d = DataUtil.concen_trans(readString,16);
                                hm_single.put("c4",concen_4d);
                            }else if (readString.startsWith("040404") & readString.length() == 18){
                                //*******************STM4温湿度****************************
                                double tem_4d = DataUtil.tem_trans(readString,16);
                                hm_single.put("t4",tem_4d);

                                double hum_4d = DataUtil.hum_trans(readString,16);
                                hm_single.put("h4",hum_4d);
                            }else if(readString.startsWith("040004") & readString.length() == 18){
                                double tem_4d = DataUtil.tem_trans_(readString,16);
                                hm_single.put("t4",tem_4d);

                                double hum_4d = DataUtil.hum_trans_(readString,16);
                                hm_single.put("h4",hum_4d);
                            } else if(readString.startsWith("040504") & readString.length() == 16){
                                EventBus.getDefault().post("ok","auto_close");
                            }
                            else if(readString.startsWith("04A0")){
                                //*******************四号柜试剂******************************
                                RFID_Scan_C_O_E_F.sb_rfid_C_O_E_F.append(readString);
                            }
                        }

                        /**********************************************失电上电通知**********************************************/
                        else if(readString.startsWith("07")){
                            if(readString.startsWith("0701")){
                                //*******************失电通知*****************************
                                String diaoDian_T = sdf.format(new Date());
                                dbhelper.updatePower("powerDown", diaoDian_T);
                                System.out.println("diaodianT:  " + diaoDian_T);
                                WarningBean2 warningBean2 = new WarningBean2(dbhelper.queryId(),"",8,"01");
                                dbhelper.insertWarning2(warningBean2,true);              //主电掉电，备电开始工作
                                EventBus.getDefault().post("shidian","shiDian");

                            }else if(readString.startsWith("0702")){
                                //********************上电通知****************************
                                String shangdian_T = sdf.format(new Date());
                                dbhelper.updatePower("powerOn",shangdian_T);
                                System.out.println("shangdianT:  " + shangdian_T);

                                EventBus.getDefault().post("shangdian","shi_shangdian");

                            }
                        }

                        else if(readString.startsWith("A0A0")){
                            if(ScrapScan.operation_state!=null){
                                if(ScrapScan.operation_state.equals("baofei")){
                                    ScrapScan.sb_scrap_rfid.append(readString);}
                            }else{
                                Weighing_Scan.sb_weigh_rfid.append(readString);
                            }

                        }


                        else {

                            if(RFID_Scan_C_O_E_F.sb_rfid_C_O_E_F.length()>0){
//                                System.out.println("sb_rfid_C_O_E_F:    " + RFID_Scan_C_O_E_F.sb_rfid_C_O_E_F.toString());
                                RFID_Scan_C_O_E_F.sb_rfid_C_O_E_F.append(readString);
                            }if(Weighing_Scan.sb_weigh_rfid.length()>0){
//                                System.out.println("sb_weigh_rfid:   " + Weighing_Scan.sb_weigh_rfid.toString());
                                Weighing_Scan.sb_weigh_rfid.append(readString);
                            }if(ScrapScan.sb_scrap_rfid.length()>0){
                                ScrapScan.sb_scrap_rfid.append(readString);
                            }else{
                                System.out.println("Garbage Message");
                            }
                        }
                }
            }
        }
    }


    private String getScanLevel1(int num) {
        String level = null;
        try {
            if (num >= 0 && num < 50) {
                level = Cmd.GET_RFID_1L1;
            }else if (num >= 50 && num < 80) {
                level = Cmd.GET_RFID_1L2;
            }else if (num >= 80) {
                level = Cmd.GET_RFID_1L3;
            }
        } catch (Exception e) {
            e.printStackTrace();
            level = Cmd.GET_RFID_1L3;
        }
        return level;
    }

    private String getScanLevel2(int num) {
        String level = null;
        try {
            if (num >= 0 & num < 50) {
                level = Cmd.GET_RFID_2L1;
            }else if (num >= 50 & num < 80) {
                level = Cmd.GET_RFID_2L2;
            }else if (num >= 80) {
                level = Cmd.GET_RFID_2L3;
            }
        } catch (Exception e) {
            e.printStackTrace();
            level = Cmd.GET_RFID_2L3;
        }
        return level;
    }

    private String getScanLevel3(int num) {
        String level = null;
        try {
            if (num >= 0 && num < 50) {
                level = Cmd.GET_RFID_3L1;
            }else if (num >= 50 && num < 80) {
                level = Cmd.GET_RFID_3L2;
            }else if (num >= 80) {
                level = Cmd.GET_RFID_3L3;
            }
        } catch (Exception e) {
            e.printStackTrace();
            level = Cmd.GET_RFID_3L3;
        }
        return level;
    }

    private String getScanLevel4(int num) {
        String level = null;
        try {
            if (num >= 0 && num < 50) {
                level = Cmd.GET_RFID_4L1;
            }else if (num >= 50 && num < 80) {
                level = Cmd.GET_RFID_4L2;
            }else if (num >= 80) {
                level = Cmd.GET_RFID_4L3;
            }
        }catch (Exception e) {
            e.printStackTrace();
            level = Cmd.GET_RFID_4L3;
        }
        return level;
    }
}
