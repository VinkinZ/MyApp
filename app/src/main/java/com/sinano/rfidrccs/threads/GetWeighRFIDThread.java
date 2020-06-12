package com.sinano.rfidrccs.threads;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;

import com.example.myapplication.R;
import com.sinano.rfidrccs.bean.OperationBean;
import com.sinano.rfidrccs.bean.WarningBean;
import com.sinano.rfidrccs.comm.Cmd;
import com.sinano.rfidrccs.comm.SerialPortSTM32;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.OperationTable;
import com.sinano.rfidrccs.db.PersonTable;
import com.sinano.rfidrccs.db.ReagentTable;
import com.sinano.rfidrccs.db.WarningTable;
import com.sinano.rfidrccs.utils.VoicePlayer;

import org.simple.eventbus.EventBus;

import static com.sinano.rfidrccs.utils.StaticVariable.admID;
import static com.sinano.rfidrccs.utils.StaticVariable.cabinetOrderNum;
import static com.sinano.rfidrccs.utils.StaticVariable.needWeighRfid;
import static com.sinano.rfidrccs.utils.StaticVariable.optID;
import static com.sinano.rfidrccs.utils.StaticVariable.weighID;
import static com.sinano.rfidrccs.utils.StaticVariable.optType;
import static com.sinano.rfidrccs.utils.StaticVariable.reagentEntry;
import static com.sinano.rfidrccs.utils.StaticVariable.reagentReturn;
import static com.sinano.rfidrccs.utils.StaticVariable.resumeWeighVariable;
import static com.sinano.rfidrccs.utils.StaticVariable.weighReagentCodeTaken;
import static com.sinano.rfidrccs.utils.StaticVariable.reagentWeight;
import static com.sinano.rfidrccs.utils.StaticVariable.weighRfid;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/8
 * Description: 读取称重试剂RFID线程，点击试剂称重后启动线程
 */
public class GetWeighRFIDThread implements Runnable{
    private Context context;
    private Handler handler;
    private OperationTable tOpt = new OperationTable();
    private ReagentTable tReg = new ReagentTable();
    private WarningTable tWarn = new WarningTable();
    private PersonTable tPerson = new PersonTable();

    public GetWeighRFIDThread(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void run() {
        int t = 0;
        while (needWeighRfid) {
            try {
                Thread.sleep(500);
                t++;
                if (t == 60) {
                    handler.sendEmptyMessage(0x00);
                    resumeWeighVariable();
                }
                if (null != weighRfid) {
                    if (weighRfid.toString().startsWith("A0A0") && weighRfid.toString().endsWith("A004017410D7") && weighRfid.length() > 26 && reagentWeight > 15 && null != optType) {
                        String reagentCode = weighRfid.toString().substring(2, weighRfid.toString().length() - 12).substring(18, 42);  // 切分string
                        System.out.println("reagentCode: " + reagentCode);
                        switch (optType) {
                            case "qu": {
                                weighID = optID;
                                // 插入操作记录
                                tOpt.insert(tOpt.getInsertContentValuesWeight(new OperationBean(weighID, reagentCode, cabinetOrderNum, 2, reagentWeight)));
                                // 更新试剂重量
                                tReg.updateOneByItem(Item.REAGENT_CODE, reagentCode, tReg.getContentValues(reagentCode, reagentWeight));
                                EventBus.getDefault().post("update", "main_in_and_out");  // 出入库表更新
                                // 称重试剂与取用试剂不一致
                                if (!(weighReagentCodeTaken.get(0).equals(reagentCode))) {
                                    // 插入预警记录
                                    tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(weighID, cabinetOrderNum, 1, "I")), true);
                                    tPerson.updatePersonScore(weighID, 3, true);  // 实验员扣分
                                    // 插入预警记录
                                    tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 1, "I")), true);
                                    tPerson.updatePersonScore(admID, 3, true);  // 管理员扣分
                                    EventBus.getDefault().post("update", "main_warning");  // 预警表更新
                                    handler.sendEmptyMessage(0x01);  // 弹窗、语音
                                }
                                weighID = null;
                                admID = null;
                                VoicePlayer.playVoice(context, R.raw.finish_weigh_and_take_reagent);  // 称重完成语音
                                EventBus.getDefault().post("update", "main_btncz_disable");  // 设置称重按钮不使能
                                break;
                            }
                            case "huan": {
                                weighID = optID;
                                reagentReturn = reagentCode;
                                // 插入操作记录
                                tOpt.insert(tOpt.getInsertContentValuesWeight(new OperationBean(weighID, reagentCode, cabinetOrderNum, 3, reagentWeight)));
                                // 更新试剂重量
                                tReg.updateOneByItem(Item.REAGENT_CODE, reagentCode, tReg.getContentValues(reagentCode, reagentWeight));
                                EventBus.getDefault().post("update", "main_in_and_out");  // 出入库表更新
                                // 刷管理员卡，打开柜门
                                switch (cabinetOrderNum) {
                                    case "1":
                                        EventBus.getDefault().post("open_door", "cabfrag1_open_door");
                                        break;
                                    case "2":
                                        EventBus.getDefault().post("open_door", "cabfrag2_open_door");
                                        break;
                                    case "3":
                                        EventBus.getDefault().post("open_door", "cabfrag3_open_door");
                                        break;
                                    case "4":
                                        EventBus.getDefault().post("open_door", "cabfrag4_open_door");
                                        break;
                                }
                                weighID = null;
                                EventBus.getDefault().post("update", "main_btncz_disable");  //设置称重按钮不使能
                                break;
                            }
                            case "ruku":
                                weighID = admID;
                                reagentEntry = reagentCode;
                                // 更新试剂重量
                                tReg.updateOneByItem(Item.REAGENT_CODE, reagentCode, tReg.getContentValues(reagentCode, reagentWeight));
                                // 直接打开柜门
                                switch (cabinetOrderNum) {
                                    case "1":
                                        SerialPortSTM32.sendOrder(Cmd.OPEN_DOOR_1);
                                        break;
                                    case "2":
                                        SerialPortSTM32.sendOrder(Cmd.OPEN_DOOR_2);
                                        break;
                                    case "3":
                                        SerialPortSTM32.sendOrder(Cmd.OPEN_DOOR_3);
                                        break;
                                    case "4":
                                        SerialPortSTM32.sendOrder(Cmd.OPEN_DOOR_4);
                                        break;
                                }
                                VoicePlayer.playVoice(context, R.raw.door_open_weigh_reagent_entry);
                                break;
                        }
                        resumeWeighVariable();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        resumeWeighVariable();
    }

    public static void interrupt(Thread thread) {
        if (null != thread && !thread.isInterrupted()) {
            thread.interrupt();
        }
    }
}


