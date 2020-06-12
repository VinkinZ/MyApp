package com.sinano.rfidrccs.threads;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;

import com.example.myapplication.R;
import com.sinano.rfidrccs.bean.OperationBean;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.OperationTable;
import com.sinano.rfidrccs.db.ReagentTable;
import com.sinano.rfidrccs.utils.DataUtil;
import com.sinano.rfidrccs.utils.VoicePlayer;

import org.simple.eventbus.EventBus;

import java.util.List;

import static com.sinano.rfidrccs.utils.StaticVariable.admID;
import static com.sinano.rfidrccs.utils.StaticVariable.isScrap;
import static com.sinano.rfidrccs.utils.StaticVariable.needScrapRfid;
import static com.sinano.rfidrccs.utils.StaticVariable.resumeScrapVariable;
import static com.sinano.rfidrccs.utils.StaticVariable.scrapRfid;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/9 0009
 * Description: 读取报废试剂RFID线程，点击试剂报废后启动线程
 */
public class GetScrapRFIDThread implements Runnable {
    private Context context;
    private Handler handler;
    private ReagentTable tReg = new ReagentTable();
    private OperationTable tOpt = new OperationTable();

    public GetScrapRFIDThread(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void run() {
        int t = 0;
        while (needScrapRfid) {
            try {
                Thread.sleep(500);
                t++;
                // 未报废
                if (t == 60) {
                    handler.sendEmptyMessage(0x00);  // 弹窗、语音
                    resumeScrapVariable();
                }
                if (null != scrapRfid) {
                    // 读到空值
                    if (scrapRfid.toString().equals("A0A00401913892A004017410D7")) {
                        handler.sendEmptyMessage(0x00);  // 弹窗、语音
                        resumeScrapVariable();
                    } else if (scrapRfid.toString().startsWith("A0A0") && scrapRfid.toString().endsWith("A004017410D7") && scrapRfid.length() > 26 && isScrap) {
                        List<String> scrapRfidList = DataUtil.rfidTrans(scrapRfid.toString(), 54);
                        for (int i = 0; i < scrapRfidList.size(); i++) {
                            // 更新试剂发送标志位
                            tReg.updateOneByItem(Item.REAGENT_CODE, scrapRfidList.get(i), tReg.getContentValues(Item.SEND_FLAG, 0));
                            // 更新试剂状态：报废
                            tReg.updateOneByItem(Item.REAGENT_CODE, scrapRfidList.get(i), tReg.getContentValues(Item.REAGENT_STATUS, 3));
                            // 更新试剂实时状态：不在柜
                            tReg.updateOneByItem(Item.REAGENT_CODE, scrapRfidList.get(i), tReg.getContentValues(Item.REAL_STATUS, 0));
                            // 插入操作记录
                            tOpt.insert(tOpt.getInsertContentValues(new OperationBean(admID, scrapRfidList.get(i), tReg.queryReagentCabinet(scrapRfidList.get(i)), 5)));
                        }
                        VoicePlayer.playVoice(context, R.raw.scrap_success);
                        EventBus.getDefault().post("update", "main_in_and_out");  // 出入库表更新
                        resumeScrapVariable();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        resumeScrapVariable();
    }
}


