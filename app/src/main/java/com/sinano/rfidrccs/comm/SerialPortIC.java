package com.sinano.rfidrccs.comm;

import com.sinano.rfidrccs.utils.DataUtil;
import com.sinano.rfidrccs.utils.StaticVariable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30 15:07
 * Description: IC读卡器串口数据流
 */
public class SerialPortIC {
    private SerialPort serialPort = null;
    private static InputStream inputStreamIC = null;
    private static OutputStream outputStreamIC = null;
    private ReceiveThreadIC receiveThreadIC = null;
    private boolean isOpen = false;

    /**
     * 打开串口的方法
     */
    public void openSerialPortIC() {
        try {
            serialPort = new SerialPort(new File("dev/ttyS3"), 9600, 0);
            inputStreamIC = serialPort.getInputStream();
            outputStreamIC = serialPort.getOutputStream();
            isOpen = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        startThreadIC();
    }

    /**
     * 关闭串口的方法
     */
    public void closeSerialPortIC() {
        if (null != inputStreamIC) {
            try {
                inputStreamIC.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != outputStreamIC) {
            try {
                outputStreamIC.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        serialPort.close();
        isOpen = false;
    }

    /**
     * 发送IC串口数据
     * @param data 数据
     */
    public static void sendSerialPortIC(String data) {
        byte[] senddata = DataUtil.strHexToByteArr(data);
        try {
            outputStreamIC.write(senddata);
            outputStreamIC.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启数据接收线程
     */
    private void startThreadIC() {
        if (null == receiveThreadIC) {
            receiveThreadIC = new ReceiveThreadIC();
            receiveThreadIC.start();
        }
    }

    /**
     * 单开线程，接收串口输入流的数据
     */
    private class ReceiveThreadIC extends Thread {
        @Override
        public void run() {
            super.run();
            while (isOpen) {
                if (null == inputStreamIC) {
                    return;
                } else {
                    byte[] readData = new byte[32];
                    try {
                        int size = inputStreamIC.read(readData);
                        if (size > 0) {
                            String readString = DataUtil.byteArrToStrHex(readData, 0, size);
                            System.out.println("cardNum: " + readString);
                            if(StaticVariable.needCardNum){
                                StaticVariable.cardNum = readString;
                            }else if(StaticVariable.needWeighCardNum || StaticVariable.needAdmCardNum){
                                StaticVariable.weighCardNum = readString;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

