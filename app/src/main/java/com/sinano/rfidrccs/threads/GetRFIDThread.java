package com.sinano.rfidrccs.threads;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import com.sinano.rfidrccs.bean.OperationBean;
import com.sinano.rfidrccs.bean.ReagentEntryBean;
import com.sinano.rfidrccs.bean.WarningBean;
import com.sinano.rfidrccs.db.CabinetTable;
import com.sinano.rfidrccs.db.HelperTable;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.OperationTable;
import com.sinano.rfidrccs.db.PersonTable;
import com.sinano.rfidrccs.db.ReagentEntryTable;
import com.sinano.rfidrccs.db.ReagentTable;
import com.sinano.rfidrccs.db.WarningTable;
import com.sinano.rfidrccs.utils.CabinetType;
import com.sinano.rfidrccs.utils.DataUtil;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


import static com.sinano.rfidrccs.utils.StaticVariable.admID;
import static com.sinano.rfidrccs.utils.StaticVariable.cabinetOrderNum;
import static com.sinano.rfidrccs.utils.StaticVariable.cabinetOrderNumEntry;
import static com.sinano.rfidrccs.utils.StaticVariable.fireGet;
import static com.sinano.rfidrccs.utils.StaticVariable.isToxic;
import static com.sinano.rfidrccs.utils.StaticVariable.isToxicWeigh;
import static com.sinano.rfidrccs.utils.StaticVariable.needRfid;
import static com.sinano.rfidrccs.utils.StaticVariable.optID;
import static com.sinano.rfidrccs.utils.StaticVariable.optType;
import static com.sinano.rfidrccs.utils.StaticVariable.reagentEntry;
import static com.sinano.rfidrccs.utils.StaticVariable.reagentReturn;
import static com.sinano.rfidrccs.utils.StaticVariable.reagentWeight;
import static com.sinano.rfidrccs.utils.StaticVariable.rfid;
import static com.sinano.rfidrccs.utils.StaticVariable.weighReagentCodeTaken;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/10 0010
 * Description:
 */
public class GetRFIDThread implements Runnable {
    private Handler handler;
    private List<String> rfidReadList;  //存放试剂标签的列表
    private List<String> rfidList;
    private ReagentTable tReg;
    private CabinetTable tCab;
    private OperationTable tOpt;
    private WarningTable tWarn;
    private PersonTable tPerson;
    private HelperTable tHelper;
    private ReagentEntryTable tRegEntry;
    private boolean isMisPlaced = true;
    private boolean overTake = true;


    @Override
    public void run() {
        int t = 0;
        while (needRfid) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (null != optType) {
                if(t == 8 && isToxicWeigh && optType.equals("qu")){
                    System.out.println(t);
                }
            }
            if((rfid.toString().startsWith("A0",2)) && rfid.toString().endsWith("A004017410D7")){
                EventBus.getDefault().post("update","main_btndoor_enable");  // 主开门使能
                EventBus.getDefault().post("update","adm1_btndoor_enable");  // 入库开门使能
                // 试剂StringBuilder转化成List
                if (rfid.toString().startsWith("01A0")) {
                    cabinetOrderNum = "1";
                } else if(rfid.toString().startsWith("02A0")) {
                    cabinetOrderNum = "2";
                } else if(rfid.toString().startsWith("03A0")) {
                    cabinetOrderNum = "3";
                } else if(rfid.toString().startsWith("04A0")) {
                    cabinetOrderNum = "4";
                }
                rfidReadList = DataUtil.rfidTrans(rfid.toString(), 54);  // 读到的试剂
                rfidList = tReg.queryReagentComparable(rfidList, cabinetOrderNum);  // 新增或减的试剂
                System.out.println("rfidList: " + rfidList);
                int cabinetCode = CabinetType.getCabIndex(tCab.queryCabinetType(cabinetOrderNum));

                if (null == optType) {
                    List<String> listTake = new ArrayList<>();
                    for (String label : rfidList) {
                        // 归还试剂
                        if (rfidReadList.contains(label) && tReg.queryReagentState(Item.REAL_STATUS, Item.REAGENT_CODE, label) == 0) {
                            tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAL_STATUS, 1));  // 更新试剂实时状态：在柜
                            tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.CABINET_CODE, cabinetOrderNum));  // 更新试剂柜号
                            if (tReg.queryReagentState(Item.REAGENT_STATUS, Item.REAGENT_CODE, label) == 3) {  // 报废试剂不可存放
                                tOpt.insert(tOpt.getInsertContentValuesMisPlace(new OperationBean(optID, label, cabinetOrderNum, 4, "错放")));
                                tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "错放"));

                                tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(optID, cabinetOrderNum, 2, "II")), oo6.havePlayed);
                                tPerson.updatePersonScore(optID, 2, oo6.havePlayed);
                                if(isToxic) {
                                    tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 2, "II")), oo6.havePlayed);
                                    tPerson.updatePersonScore(admID, 2, oo6.havePlayed);
                                }
                                oo6.play(handler, 0x006);  // 报废试剂不可存放
                            }else if (tReg.queryReagentState(Item.REAGENT_STATUS, Item.REAGENT_CODE, label) == 3) {  // 未入库试剂不可存放
                                tOpt.insert(tOpt.getInsertContentValuesMisPlace(new OperationBean(optID, label, cabinetOrderNum, 4, "错放")));
                                tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "错放"));

                                tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(optID, cabinetOrderNum, 2, "II")), oo8.havePlayed);
                                tPerson.updatePersonScore(optID, 2, oo8.havePlayed);
                                if(isToxic) {
                                    tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 2, "II")), oo6.havePlayed);
                                    tPerson.updatePersonScore(admID, 2, oo8.havePlayed);
                                }
                                oo8.play(handler, 0x008);  // 未入库试剂不可存放
                            } else if (tReg.queryReagentState(Item.REAGENT_STATUS, Item.REAGENT_CODE, label) == 1){  // 已入库试剂存放
                                if ((String.valueOf(label.charAt(0))).equals(String.valueOf(cabinetCode))) {
                                    if (!(label.equals(reagentReturn)) && isToxicWeigh) {  // 归还试剂与称重试剂不一致
                                        tOpt.insert(tOpt.getInsertContentValuesMisPlace(new OperationBean(optID, label, cabinetOrderNum, 4, "错放")));
                                        tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "错放"));

                                        tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(optID, cabinetOrderNum, 1, "I")), oo10.havePlayed);  // 试剂未称重报警
                                        tPerson.updatePersonScore(optID, 3, oo10.havePlayed);  // 试剂未称重扣分
                                        tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 1, "I")), oo10.havePlayed);
                                        tPerson.updatePersonScore(admID, 3, oo10.havePlayed);  // 试剂未称重扣分
                                        oo10.play(handler,0x010);  // 归还试剂与称重试剂不一致
                                    } else {
                                        tOpt.insert(tOpt.getInsertContentValues(new OperationBean(optID, label, cabinetOrderNum, 3)));
                                        tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "正常"));
                                    }
                                } else {  // 归还错放
                                    tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "错放"));
                                    tOpt.insert(tOpt.getInsertContentValuesMisPlace(new OperationBean(optID, label, cabinetOrderNum, 4, "错放")));
                                    if (isMisPlaced) { // 试剂错放报警扣分
                                        tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(optID, cabinetOrderNum, 2, "II")), oo1.havePlayed);
                                        tPerson.updatePersonScore(optID, 2, oo1.havePlayed);
                                        if (isToxic) {
                                            tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 2, "II")), oo1.havePlayed);
                                            tPerson.updatePersonScore(admID, 2, oo1.havePlayed);
                                        }
                                        oo1.play(handler,0x001);  // 错放试剂
                                        isMisPlaced = false;
                                    }
                                }
                            } else {
                                oo7.play(handler,0x007);  // 语音延时播报，防止重叠，播报存在无法识别试剂
                            }
                        }
                        // 取用试剂
                        else if (rfidReadList.contains(label) && tReg.queryReagentState(Item.REAL_STATUS, Item.REAGENT_CODE, label) == 1) {
                            tOpt.insert(tOpt.getInsertContentValues(new OperationBean(optID, label, cabinetOrderNum, 2)));
                            tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAL_STATUS, 0));  // 更新试剂实时状态：不在柜
                            listTake.add(label);
                            if (isToxicWeigh) {
                                if (listTake.size() > 1 && overTake) {  // 有毒柜取用过多报警扣分
                                    tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(optID, cabinetOrderNum, 3, "II")), oo9.havePlayed);
                                    tPerson.updatePersonScore(optID, 2, oo9.havePlayed);
                                    tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 3, "II")), oo9.havePlayed);
                                    tPerson.updatePersonScore(admID, 2, oo9.havePlayed);
                                    overTake = false;
                                    oo9.play(handler,0x009);
                                }
                            } else {
                                if (listTake.size() > Integer.parseInt(tHelper.queryInHelper(Item.MAX_AMOUNT)) && overTake) {  // 有毒不称重柜、普通柜取用过多报警扣分
                                    tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(optID, cabinetOrderNum, 3, "II")), oo2.havePlayed);
                                    tPerson.updatePersonScore(optID, 2, oo2.havePlayed);
                                    if (isToxic) {
                                        tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 3, "II")), oo2.havePlayed);
                                        tPerson.updatePersonScore(admID, 2, oo2.havePlayed);
                                    }
                                    overTake = false;
                                    oo2.play(handler,0x002);
                                }
                            }
                        }
                    }
                    weighReagentCodeTaken = listTake;  // 存放取用的有毒称重试剂
                } else if ("ruku".equals(optType)) {  // 入库
                    List<String> listTake = new ArrayList<>();
                    for (String label : rfidList) {
                        if (rfidReadList.contains(label) && tReg.queryReagentState(Item.REAL_STATUS, Item.REAGENT_CODE, label) == 0) {  // 存放
                            tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAL_STATUS, 1));  // 更新试剂实时状态：在柜
                            tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.CABINET_CODE, cabinetOrderNum));  // 更新试剂柜号
                            if (tReg.queryReagentState(Item.REAGENT_STATUS, Item.REAGENT_CODE, label) == 3) {  //试剂已报废
                                tOpt.insert(tOpt.getInsertContentValues(new OperationBean(admID, label, cabinetOrderNum, 1, "错放")));
                                tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "错放"));
                                tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 2, "II")), oo61.havePlayed);
                                tPerson.updatePersonScore(admID, 2, oo61.havePlayed);
                                oo61.play(handler, 0x0061);
                            } else if (tReg.queryReagentState(Item.REAGENT_STATUS, Item.REAGENT_CODE, label) == 1) {  //试剂已入库
                                tOpt.insert(tOpt.getInsertContentValues(new OperationBean(admID, label, cabinetOrderNum, 1, "错放")));
                                tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "错放"));
                                tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 2, "II")), oo5.havePlayed);
                                tPerson.updatePersonScore(admID, 2, oo5.havePlayed);
                                oo5.play(handler,0x005);
                            } else if (tReg.queryReagentState(Item.REAGENT_STATUS, Item.REAGENT_CODE, label) == 0) {  //未入库试剂入库
                                if ((String.valueOf(label.charAt(0))).equals(String.valueOf(cabinetCode))) {
                                    if (null == cabinetOrderNumEntry) {  // 非剧毒试剂正常入库
                                        tOpt.insert(tOpt.getInsertContentValues(new OperationBean(admID, label, cabinetOrderNum, 0)));
                                        tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "正常"));
                                        tRegEntry.insert(tRegEntry.getInsertContentValues(new ReagentEntryBean(label)));
                                    } else {
                                        if (!(label.equals(reagentEntry))) {   // 入库试剂与称重试剂不一致
                                            tOpt.insert(tOpt.getInsertContentValues(new OperationBean(admID, label, cabinetOrderNum, 1, "错放")));
                                            tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "错放"));
                                            tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 1, "I")), oo12.havePlayed);
                                            tPerson.updatePersonScore(admID, 3, oo12.havePlayed);
                                            oo12.play(handler, 0x012);
                                        } else {  // 剧毒试剂正常入库
                                            tOpt.insert(tOpt.getInsertContentValues(new OperationBean(admID, label, cabinetOrderNum, 0, tReg.queryReagentWeight(label))));
                                            tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "正常"));
                                            tRegEntry.insert(tRegEntry.getInsertContentValues(new ReagentEntryBean(label)));
                                        }
                                    }
                                } else {  // 入库错放
                                    tOpt.insert(tOpt.getInsertContentValues(new OperationBean(admID, label, cabinetOrderNum, 1, "错放")));
                                    tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAGENT_FLAG, "错放"));
                                    if (isMisPlaced) {
                                        tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 2, "I")), oo111.havePlayed);
                                        tPerson.updatePersonScore(admID, 2, oo111.havePlayed);
                                        oo111.play(handler, 0x00111);
                                        isMisPlaced = false;
                                    }
                                }
                            } else{
                                oo71.play(handler, 0x0071);  // 语音延时播报，防止重叠，播报存在无法识别试剂
                            }
                        } else if (!(rfidReadList.contains(label)) && tReg.queryReagentState(Item.REAL_STATUS, Item.REAGENT_CODE, label) == 1) {  // 入库时取用
                            tOpt.insert(tOpt.getInsertContentValues(new OperationBean(optID, label, cabinetOrderNum, 2)));
                            tReg.updateOneByItem(Item.REAGENT_CODE, label, tReg.getContentValues(Item.REAL_STATUS, 0));  // 更新试剂实时状态：不在柜
                            listTake.add(label);
                            if (isToxicWeigh) {
                                if (listTake.size() > 1 && overTake) {  // 有毒柜取用过多报警扣分
                                    tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 3, "II")), oo9.havePlayed);
                                    tPerson.updatePersonScore(admID, 2, oo9.havePlayed);
                                    overTake = false;
                                    oo9.play(handler,0x009);
                                }
                            } else {
                                if (listTake.size() > Integer.parseInt(tHelper.queryInHelper(Item.MAX_AMOUNT)) && overTake) {  // 有毒不称重柜、普通柜取用过多报警扣分
                                    tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, cabinetOrderNum, 3, "II")), oo2.havePlayed);
                                    tPerson.updatePersonScore(admID, 2, oo2.havePlayed);
                                    overTake = false;
                                    oo2.play(handler,0x002);
                                }
                            }
                        }
                    }

                    // 灭火器对比报警
                    List<String> fireList = tReg.queryReagentFire();
                    fireList.removeAll(fireGet);
                    if (fireList.size() > 0) {
                        tWarn.insertWarning(tWarn.getInsertContentValues(new WarningBean(admID, "", 9, "09")), true);
                    }

                    /*List<String> listNotStorage = dbHelper.queryReagentNotStorag(CounterNum);
                    if(listNotStorage.size()>0 & incounterSuccess){
                        //存在未入库试剂
                        //语音延时播报，防止重叠
                        final Timer t = new Timer();
                        t.schedule(new TimerTask() {
                            public void run() {
                                try {
                                    handler.sendEmptyMessage(0x004);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                rm_take.play_release(mediaPlayerInCountNoEnding);
                                t.cancel();
                            }
                        }, 4000);
                    }else if(incounterSuccess){
                        final Timer t = new Timer();
                        t.schedule(new TimerTask() {
                            public void run() {
                                rm_take.play_release(mediaPlayerInCountEnding);
                                t.cancel();
                            }
                        }, 4000);
                    }*/
                }
                EventBus.getDefault().post("update","main_in_and_out");
                EventBus.getDefault().post("update","main_warning");
                EventBus.getDefault().post("update","main_opt_null");
                if (null != optType) {
                    if (optType.equals("qu") && takeReagent) {
                        reagentWeight = 0;
                        handler.sendEmptyMessage(0x003);
                        EventBus.getDefault().post("update","main_btncz_enable");
                        EventBus.getDefault().post("warn","qu_no_weigh");
                    }
                }
                mediaPlayerScanEnd = MediaPlayer.create(context, R.raw.scan_end);
                rm_take.play_release(mediaPlayerScanEnd);
                rfid = new StringBuffer();
                RFID_List = null;
                OperationID=null;
                operation_state=null;
                RFID_Scan_C_O_E_F.sb_rfid_return = null;
                sb_rfid_inCounter = null;
                SecondID = null;
                Weighing_Scan.counterNumOfInCounter = null;
                waitID[0] = false;
                Counter_T=false;
                counterAtr_T=false;
                try {
                    dialog_scanning.close_dialog0();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if(Quit == 100){
                    operation_state=null;
                    rfid = new StringBuffer();
                    sb_rfid_inCounter = null;
                    RFID_Scan_C_O_E_F.sb_rfid_return=null;
                    RFID_List = null;
                    SecondID=null;
                    OperationID=null;
                    waitID[0] = false;
                    Counter_T=false;
                    counterAtr_T=false;
                    EventBus.getDefault().post("Enable","door_Enabled");//设置开门开关使能
                    EventBus.getDefault().post("ok","inCounter_door_Enabled");//设置入库开门使能
                    EventBus.getDefault().post("ok","OperationIDNull");
                    rm_nullRFID.play_release(mediaPlayerRFIDisNull);      //播放误放试剂语音
                    try {
                        dialog_scanning.close_dialog0();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            t++;
        }
    }
}


