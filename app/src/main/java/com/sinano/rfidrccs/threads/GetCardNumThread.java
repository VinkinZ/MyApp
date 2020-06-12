package com.sinano.rfidrccs.threads;

import android.os.Handler;

import com.sinano.rfidrccs.db.PersonTable;

import static com.sinano.rfidrccs.utils.StaticVariable.admID;
import static com.sinano.rfidrccs.utils.StaticVariable.cardNum;
import static com.sinano.rfidrccs.utils.StaticVariable.makeCardNumNull;
import static com.sinano.rfidrccs.utils.StaticVariable.makeNeedCardNumFalse;
import static com.sinano.rfidrccs.utils.StaticVariable.makeNeedCardNumTrue;
import static com.sinano.rfidrccs.utils.StaticVariable.makeOptIDNull;
import static com.sinano.rfidrccs.utils.StaticVariable.needCardNum;
import static com.sinano.rfidrccs.utils.StaticVariable.optID;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/3
 * Description: 等待卡号线程
 */
public class GetCardNumThread implements Runnable {
    private Handler handler;
    private int needLevel;

    public GetCardNumThread(Handler handler, int needLevel) {
        this.handler = handler;
        this.needLevel = needLevel;
    }

    @Override
    public void run() {
        makeCardNumNull();
        makeOptIDNull();
        makeNeedCardNumTrue();

        int t = 0;
        PersonTable tPerson = new PersonTable();
        while (needCardNum) {
            try {
                Thread.sleep(500);
                t++;
                // 超时退出
                if (t == 20) {
                    makeNeedCardNumFalse();
                    handler.sendEmptyMessage(0x00);
                }
                // 判断卡号
                if (null != cardNum) {
                    if (needLevel == 1) {
                        // 人员身份是否有效、是否有权限
                        if (tPerson.queryPersonValid(cardNum) == 1 && tPerson.queryPersonAuth(cardNum) > 0) {
                            // 人员是否有安全信誉分值
                            if (tPerson.queryPersonScore(cardNum) > 0) {
                                handler.sendEmptyMessage(0x01);
                                optID = cardNum;
                            } else {
                                handler.sendEmptyMessage(0x02);
                            }
                        } else {
                            handler.sendEmptyMessage(0x03);
                        }
                        makeCardNumNull();
                        makeNeedCardNumFalse();
                    } else if (needLevel == 2) {
                        // 管理员或安全员身份是否有效、是否有权限
                        if ((tPerson.queryPersonRole(cardNum).equals("安全员") ||
                            tPerson.queryPersonRole(cardNum).equals("系统管理员")) &&
                            tPerson.queryPersonValid(cardNum) == 1 && tPerson.queryPersonAuth(cardNum) > 0) {
                            // 人员是否有安全信誉分值
                            if (tPerson.queryPersonScore(cardNum) > 0) {
                                handler.sendEmptyMessage(0x04);
                                admID = cardNum;
                            } else {
                                handler.sendEmptyMessage(0x02);
                            }
                        } else {
                            handler.sendEmptyMessage(0x03);
                        }
                        makeCardNumNull();
                        makeNeedCardNumFalse();
                    } else if (needLevel == 3){
                        // 管理员或安全员身份是否有效、是否有权限
                        if (tPerson.queryPersonRole(cardNum).equals("系统管理员") &&
                            tPerson.queryPersonValid(cardNum) == 1 && tPerson.queryPersonAuth(cardNum) > 0) {
                            // 人员是否有安全信誉分值
                            if (tPerson.queryPersonScore(cardNum) > 0) {
                                handler.sendEmptyMessage(0x01);
                                admID = cardNum;
                            } else {
                                handler.sendEmptyMessage(0x02);
                            }
                        } else {
                            handler.sendEmptyMessage(0x03);
                        }
                        makeCardNumNull();
                        makeNeedCardNumFalse();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        makeCardNumNull();
        makeNeedCardNumFalse();
    }

    public static void interrupt(Thread thread) {
        if (null != thread && !thread.isInterrupted()) {
            thread.interrupt();
        }
    }
}
