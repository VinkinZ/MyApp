package com.sinano.rfidrccs.comm;

import com.sinano.rfidrccs.business.AccessReagentToxicWeigh;
import com.sinano.rfidrccs.ui.act.MainActivity;
import com.sinano.rfidrccs.utils.StaticVariable;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30 15:07
 * Description: 电子秤串口数据流
 */
public class SerialPortWeigh {

    private SerialPort serialPort = null;
    private InputStream inputStreamWeigh = null;
    private OutputStream outputStreamWeigh = null;
    private ReceiveThreadWeight receiveThreadWeight = null;
    private boolean isOpen = false;

    public void openSerialPortWeight() {
        try {
            serialPort = new SerialPort(new File("dev/ttyS1"),9600,0);
            inputStreamWeigh = serialPort.getInputStream();
            outputStreamWeigh = serialPort.getOutputStream();
            isOpen = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        getSerial();
    }

    public void closeSerialPortWeight() {
        if (inputStreamWeigh != null){
            try {
                inputStreamWeigh.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStreamWeigh != null){
            try{
                outputStreamWeigh.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        serialPort.close();
        isOpen = false;
    }

    private void getSerial(){
        if(receiveThreadWeight == null) {
            receiveThreadWeight = new ReceiveThreadWeight();
        }
        receiveThreadWeight.start();
    }

    private class ReceiveThreadWeight extends Thread {
        @Override
        public void run(){
            super.run();
            while(isOpen){
                if (inputStreamWeigh == null) {
                    return;
                }
                byte[] readData = new byte[32];
                try {
                    int size = inputStreamWeigh.read(readData);
                    if (size > 0) {
                        String readString = new String(readData);
                        try {
                            StaticVariable.reagentWeight = Double.parseDouble(readString);
                            if (Double.parseDouble(readString) > 0.15) {
                                EventBus.getDefault().post("got_weight","main_weigh_dialog_dismiss");
                                EventBus.getDefault().post("got_weight","adm1_entry_no_weigh_resume_ui");
                                if (null != MainActivity.tTakeNoWeigh1) {
                                    MainActivity.tTakeNoWeigh1.cancel();
                                    MainActivity.tTakeNoWeigh1.purge();
                                    MainActivity.tTakeNoWeigh1 = null;
                                }
                                if (null != MainActivity.tTakeNoWeigh2) {
                                    MainActivity.tTakeNoWeigh2.cancel();
                                    MainActivity.tTakeNoWeigh2.purge();
                                    MainActivity.tTakeNoWeigh2 = null;
                                }
                                if (null != AccessReagentToxicWeigh.tReturnNoWeigh) {
                                    AccessReagentToxicWeigh.tReturnNoWeigh.cancel();
                                    AccessReagentToxicWeigh.tReturnNoWeigh.purge();
                                    AccessReagentToxicWeigh.tReturnNoWeigh = null;
                                }
                                /*try {
                                    MainActivity.tTakeNoWeigh1.cancel();
                                    MainActivity.tTakeNoWeigh1.purge();
                                    MainActivity.tTakeNoWeigh1 = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    MainActivity.tTakeNoWeigh2.cancel();
                                    MainActivity.tTakeNoWeigh2.purge();
                                    MainActivity.tTakeNoWeigh2 = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    AccessReagentToxicWeigh.tReturnNoWeigh.cancel();
                                    AccessReagentToxicWeigh.tReturnNoWeigh.purge();
                                    AccessReagentToxicWeigh.tReturnNoWeigh = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }*/
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
