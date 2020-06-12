package com.sinano.rfidrccs.utils;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;

import com.example.myapplication.R;
import com.sinano.rfidrccs.business.AccessReagentToxicWeigh;
import com.sinano.rfidrccs.db.CabinetTable;
import com.sinano.rfidrccs.db.HelperTable;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.ui.dialog.DialogNoSelection;
import com.sinano.rfidrccs.ui.dialog.DialogOneSelection;
import com.sinano.rfidrccs.ui.dialog.DialogThreeSelection;
import com.sinano.rfidrccs.ui.dialog.DialogTwoSelection;
import com.sinano.rfidrccs.ui.dialog.DialogTwoSelection_;


/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/6/1 9:10
 * Description: 弹窗显示
 */
public class DialogShow {
    private Context context;
    private DialogNoSelection dnos;
    private DialogOneSelection dones;
    private DialogTwoSelection_ dtwos_;
    private DialogThreeSelection dthrees;

    public DialogShow(Context context) {
        this.context = context;
    }

    public void showDialog(String dTitle, String dMsg) {
        dnos = new DialogNoSelection(context);
        dnos.setTitle(dTitle);
        dnos.setMsg(dMsg);
        dnos.show();
    }

    public void showDialog(String dTitle, String dMsg, String dBtnN) {
        dones = new DialogOneSelection(context);
        dones.setTitle(dTitle);
        dones.setMsg(dMsg);
        dones.setNegativeOnclickListener(dBtnN, new DialogOneSelection.onNegativeOnclickListener() {
            @Override
            public void onNegativeClick() {
                dones.dismiss();
            }
        });
        dones.show();
    }

    public void showDialog(String dTitle, String dMsg, String dBtnN, final Handler handler) {
        dones = new DialogOneSelection(context);
        dones.setTitle(dTitle);
        dones.setMsg(dMsg);
        dones.setNegativeOnclickListener(dBtnN, new DialogOneSelection.onNegativeOnclickListener() {
            @Override
            public void onNegativeClick() {
                handler.sendEmptyMessage(0x00);
            }
        });
        dones.show();

    }

    public void showWeighingDialog(){
        dones = new DialogOneSelection(context);
        dones.setTitle("请进行试剂称重操作");
        dones.setMsg("确认电子秤已开\n按电子秤“去皮”按键\n放入需称重试剂\n关好主控柜柜门");
        dones.setNegativeOnclickListener("我已放好", new DialogOneSelection.onNegativeOnclickListener() {
            @Override
            public void onNegativeClick() {
                VoicePlayer.playVoice(context, R.raw.weigh_wrong);
            }
        });
        dones.show();
    }

    public void showSplashDialog(final HelperTable helperTable, final CabinetTable cabinetTable, final Thread thread) {
        dtwos_ = new DialogTwoSelection_(context);
        dtwos_.setTitle("初始化信息录入");
        dtwos_.setStr1("柜体编号：");
        dtwos_.setHint1("请输入柜体编号");
        dtwos_.setStr1("场所编号：");
        dtwos_.setHint2("请输入场所编号");
        dtwos_.setNegativeOnclickListener("取消", new DialogTwoSelection.onNegativeOnclickListener() {
            @Override
            public void onNegativeClick() {
                dtwos_.dismiss();
            }
        });
        dtwos_.setPositiveOnclickListener("确定", new DialogTwoSelection.onPositiveOnclickListener() {
            @Override
            public void onPositiveClick() {
                ContentValues cv1 = helperTable.getContentValues(Item.INITIAL_CODE, dtwos_.getEditDia1().getText().toString());
                helperTable.updateOneByItem(Item.SETTING, "设置", cv1);
                ContentValues cv2 = cabinetTable.getContentValues(Item.BAR_CODE, dtwos_.getEditDia2().getText().toString());
                cabinetTable.updateOneByItem(Item.CABINET_PLACE, "1", cv2);
                dtwos_.dismiss();
                ToastShow.showShortToast(context, "已保存柜体编号：" + dtwos_.getEditDia1().getText().toString() +
                                                       "，场所编号：" + dtwos_.getEditDia2().getText().toString());
                thread.start();
            }
        });
        dtwos_.show();
    }

    public void showSetCabTypeDialog(final Handler handler) {
        dtwos_ = new DialogTwoSelection_(context);
        dtwos_.setTitle("柜体属性配置");
        dtwos_.setStr1("用户名：");
        dtwos_.setHint1("请输入维护账号");
        dtwos_.setStr1("密码：");
        dtwos_.setHint2("请输入维护密码");
        dtwos_.setNegativeOnclickListener("取消", new DialogTwoSelection.onNegativeOnclickListener() {
            @Override
            public void onNegativeClick() {
                dtwos_.dismiss();
            }
        });
        dtwos_.setPositiveOnclickListener("确定", new DialogTwoSelection.onPositiveOnclickListener() {
            @Override
            public void onPositiveClick() {
                if (dtwos_.getEditDia1().getText().toString().equals("123") && dtwos_.getEditDia1().getText().toString().equals("123456")) {
                    dtwos_.dismiss();
                    handler.sendEmptyMessage(0x01);
                } else {
                    VoicePlayer.playVoice(context, R.raw.verification_failed);
                    dtwos_.getEditDia1().setText("");
                    dtwos_.getEditDia2().setText("");
                }
            }
        });
        dtwos_.show();
    }

    public void showToxicOptDialog(final Handler handler) {
        dthrees = new DialogThreeSelection(context);
        dthrees.setTitle("正在存取有毒试剂");
        dthrees.setMsg("请选择操作类型");
        dthrees.setCancelable(false);
        dthrees.setFirstOnclickListener("取用试剂", new DialogThreeSelection.onFirstOnclickListener(){
            @Override
            public void onFirstClick() {
                StaticVariable.optType = "qu";
                AccessReagentToxicWeigh artw = new AccessReagentToxicWeigh(context, handler);
                artw.onQuYong();
                dthrees.dismiss();
            }
        });
        dthrees.setSecondOnclickListener("归还试剂", new DialogThreeSelection.onSecondOnclickListener() {
            @Override
            public void onSecondClick() {
                StaticVariable.optType = "huan";
                AccessReagentToxicWeigh artw = new AccessReagentToxicWeigh(context, handler);
                artw.weighReagent();
                dthrees.dismiss();
            }
        });
        dthrees.setNegativeOnclickListener("取消操作", new DialogThreeSelection.onNegativeOnclickListener() {
            @Override
            public void onNegativeClick() {
                StaticVariable.optType = null;
                dthrees.dismiss();
            }
        });
        dthrees.show();
    }


    public void closeNoSectDialog() {
        dnos.dismiss();
    }

    public void closeOneSectDialog() {
        dones.dismiss();
    }

    public void closeTwoSectDialog() {
        dtwos_.dismiss();
    }

}
