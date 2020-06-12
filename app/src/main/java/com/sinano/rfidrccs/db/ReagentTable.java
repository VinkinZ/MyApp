package com.sinano.rfidrccs.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.sinano.rfidrccs.bean.ReagentBean;
import com.sinano.rfidrccs.db.Item;
import com.sinano.rfidrccs.db.SQLiteManager;
import com.sinano.rfidrccs.db.Table;
import com.sinano.rfidrccs.db.TableOpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 试剂表操作
 */
public class ReagentTable extends Table implements TableOpt {
    private static final String TAG = "ReagentTable";

    public ReagentTable() {
        initTable();
    }

    @Override
    public void initTable() {
        this.tableName = REAGENT_TABLE;
        this.keyItem = Item._ID;
        this.items.add(new Item(Item.REAGENT_CODE, Item.ITEM_TYPE_VARCHAR_UNN));
        this.items.add(new Item(Item.DEPARTMENT_NAME, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.REAGENT_NAME, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.SPECIFICATION, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.MASTER_METERING, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.ITEM_NUMBER, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.RESIDUAL_AMOUNT, Item.ITEM_TYPE_DOUBLE_D0));
        this.items.add(new Item(Item.CABINET_CODE, Item.ITEM_TYPE_VARCHAR_D));
        this.items.add(new Item(Item.REAGENT_TYPE, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.SUPPLIER, Item.ITEM_TYPE_VARCHAR));
        this.items.add(new Item(Item.REAGENT_FLAG, Item.ITEM_TYPE_VARCHAR_D));
        this.items.add(new Item(Item.REAL_STATUS, Item.ITEM_TYPE_INTEGER_D0));
        this.items.add(new Item(Item.USER_ID, Item.ITEM_TYPE_VARCHAR_D));
        this.items.add(new Item(Item.REAGENT_STATUS, Item.ITEM_TYPE_INTEGER_NN));
        this.items.add(new Item(Item.SEND_FLAG, Item.ITEM_TYPE_INTEGER_D0));
    }

    /**
     * 初始化写入数据
     * @param bean 单条数据
     * @return 内容
     */
    @Override
    public ContentValues getInsertContentValues(Object bean) {
        ReagentBean reagentBean = (ReagentBean) bean;
        ContentValues contentValues = new ContentValues();
        contentValues.put("reagentCode", reagentBean.getReagentCode());
        contentValues.put("departmentName",reagentBean.getDepartmentName());
        contentValues.put("reagentName",reagentBean.getReagentName());
        contentValues.put("specification",reagentBean.getSpecification());
        contentValues.put("masterMetering",reagentBean.getMasterMetering());
        contentValues.put("itemNumber",reagentBean.getItemNumber());
        contentValues.put("residualAmount",reagentBean.getResidualAmount());
        contentValues.put("cabinetCode",reagentBean.getCabinetCode());
        contentValues.put("reagentType",reagentBean.getReagentType());
        contentValues.put("supplier",reagentBean.getSupplier());
        contentValues.put("reagentStatus",reagentBean.getReagentStatus());
        return contentValues;
    }

    /**
     * 查询试剂状态
     * @param need 查询内容
     * @param item 字段
     * @param content 值
     * @return 标志
     */
    public int queryReagentState(String need, String item, String content) {
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, new String[]{need},
            item + "=?", new String[]{content}, null, null, null)) {
            if (null != cursor && cursor.moveToFirst()) {
                int result = cursor.getInt(cursor.getColumnIndex(need));
                Log.d(TAG, "queryInReagent: " + result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 查询试剂柜号
     * @param strReagentCode 试剂码
     * @return 试剂柜号
     */
    public String queryReagentCabinet(String strReagentCode) {
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, new String[]{Item.CABINET_CODE},
            Item.REAGENT_CODE + "=?", new String[]{strReagentCode}, null, null, null)) {
            if (null != cursor && cursor.moveToFirst()) {
                String strCabinet = cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE));
                Log.d(TAG, "queryReagentCabinet: " + strCabinet);
                return strCabinet;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询柜内试剂数量
     * @param strCabinetCode 柜号
     * @return 数量
     */
    public int queryReagentCount(String strCabinetCode) {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.REAGENT_CODE + " from "
            + Table.REAGENT_TABLE + " where " + Item.REAL_STATUS + "=1 and " + Item.CABINET_CODE + "=?", new String[]{strCabinetCode})) {
            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext()) {
                    count += 1;
                }
                return count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 查询试剂重量
     * @param strReagentCode 试剂码
     * @return 重量
     */
    public double queryReagentWeight(String strReagentCode) {
        String[] columns = {Item.RESIDUAL_AMOUNT};
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, columns, Item.REAGENT_CODE + "=?",
            new String[]{strReagentCode}, null, null, null)) {
            if (null != cursor && cursor.moveToFirst()) {
                double weight = cursor.getDouble(cursor.getColumnIndex(Item.RESIDUAL_AMOUNT));
                Log.d(TAG, "queryReagentWeight: " + weight);
                return weight;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 查询某个试剂信息
     * @param strReagentCode 试剂码
     * @return list
     */
    public List<ReagentBean> queryReagentRecord(String strReagentCode) {
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, null, Item.REAGENT_CODE + "=?",
            new String[]{strReagentCode},null,null,null)) {
            List<ReagentBean> list = cursorToListReagent(cursor);
            for (ReagentBean reagentBean : list) {
                Log.d(TAG, "queryReagentRecord: " + reagentBean.toString());
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询试剂帮助方法
     * @param cursor 游标
     * @return list
     */
    private List<ReagentBean> cursorToListReagent(Cursor cursor) {
        List<ReagentBean> list = new ArrayList<>();
        while(cursor.moveToNext()){
            String reagentCode = cursor.getString(cursor.getColumnIndex(Item.REAGENT_CODE));
            String departmentName = cursor.getString(cursor.getColumnIndex(Item.DEPARTMENT_NAME));
            String reagentName = cursor.getString(cursor.getColumnIndex(Item.REAGENT_NAME));
            String specification = cursor.getString(cursor.getColumnIndex(Item.SPECIFICATION));
            String masterMetering = cursor.getString(cursor.getColumnIndex(Item.MASTER_METERING));
            String itemNumber = cursor.getString(cursor.getColumnIndex(Item.ITEM_NUMBER));
            Double residualAmount = cursor.getDouble(cursor.getColumnIndex(Item.RESIDUAL_AMOUNT));
            String cabinetCode = cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE));
            String reagentType = cursor.getString(cursor.getColumnIndex(Item.REAGENT_TYPE));
            String supplier = cursor.getString(cursor.getColumnIndex(Item.SUPPLIER));
            int reagentStatus = cursor.getInt(cursor.getColumnIndex(Item.REAGENT_STATUS));

            ReagentBean reagentBean = new ReagentBean(reagentCode,departmentName,reagentName,specification,masterMetering,
                                                      itemNumber,residualAmount,cabinetCode,reagentType,supplier,reagentStatus);
            list.add(reagentBean);
        }
        return list;
    }

    /**
     * 查询未归还试剂
     * @return 试剂列表
     */
    public List<String> queryReagentTake() {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.REAGENT_CODE + " from " +
            tableName + " where " + Item.REAGENT_STATUS + "=1 and " + Item.REAL_STATUS + "=0", new String[]{})) {
            if (null != cursor) {
                List<String> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String reagentCode = cursor.getString(cursor.getColumnIndex(Item.REAGENT_CODE));
                    list.add(reagentCode);
                }
                Log.d(TAG, "queryReagentTake: " + list);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询未入库试剂
     * @return 试剂列表
     */
    public List<String> queryReagentNotEntry() {
        try (Cursor cursor = SQLiteManager.getInstance().db.query(tableName, null, Item.REAGENT_STATUS + "=0",
            new String[]{}, null, null, null)) {
            if (null != cursor) {
                List<String> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String reagentCode = cursor.getString(cursor.getColumnIndex(Item.REAGENT_CODE));
                    list.add(reagentCode);
                }
                Log.d(TAG, "queryReagentNotEntry: " + list);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询某个试剂柜未入库试剂
     * @param strCabinetCode 柜号
     * @return 试剂list
     */
    public List<String> queryReagentNotEntry(String strCabinetCode) {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.REAGENT_CODE + " from "+
            tableName + " where "+ Item.REAGENT_STATUS + "=0 and "+ Item.CABINET_CODE + "=?", new String[]{strCabinetCode})) {
            if (null != cursor) {
                List<String> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String reagentCode = cursor.getString(cursor.getColumnIndex(Item.REAGENT_CODE));
                    list.add(reagentCode);
                }
                Log.d(TAG, "queryReagentNotEntry: " + list);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询打包需要上传的试剂信息
     * @return list
     */
    public List<ReagentBean> queryReagentPost() {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.reagentStatus,a.realStatus," +
            "a.cabinetCode,a.reagentCode,b.userId,b.operateTime,a.residualAmount from " + tableName +
            " as a inner join " + Table.OPERATION_TABLE + " as b on a.reagentCode = b.reagentCode where a.sendFlag=0 " +
            "group by a.reagentCode order by b.operateTime DESC", new String[]{})) {
            if (cursor != null) {
                List<ReagentBean> listSend = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int reagentStatus = cursor.getInt(cursor.getColumnIndex(Item.REAGENT_STATUS));
                    int realStatus = cursor.getInt(cursor.getColumnIndex(Item.REAL_STATUS));
                    String cabinetCode = cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE));
                    String reagentCode = cursor.getString(cursor.getColumnIndex(Item.REAGENT_CODE));
                    String userId = cursor.getString(cursor.getColumnIndex(Item.USER_ID));
                    String operateTime = cursor.getString(cursor.getColumnIndex(Item.OPERATE_TIME));
                    double residualAmount = cursor.getDouble(cursor.getColumnIndex(Item.RESIDUAL_AMOUNT));

                    if (reagentStatus == 1) {
                        if (realStatus == 0) {
                            reagentStatus = 0;
                        } else if (realStatus == 1) {
                            reagentStatus = 2;
                        }
                    } else if (reagentStatus == 0) {
                        reagentStatus = -1;
                    }

                    ReagentBean reagentBean = new ReagentBean(reagentCode, residualAmount, cabinetCode, realStatus,
                                                              userId, reagentStatus, operateTime);
                    listSend.add(reagentBean);
                }
                return listSend;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询试剂对比算法
     * @param list1 扫到的试剂码
     * @param strCabinetCode 柜号
     * @return 返回某柜的取用或归还试剂的list列表
     */
    public List<String> queryReagentComparable(List<String> list1, String strCabinetCode) {
        List<String> list0 = new ArrayList<>();
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select " + Item.REAGENT_CODE + " from " +
            tableName + " where " + Item.REAL_STATUS + "=1 and " + Item.CABINET_CODE + "=?", new String[]{strCabinetCode})) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String reagentCode = cursor.getString(cursor.getColumnIndex(Item.REAGENT_CODE));
                    list0.add(reagentCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("在库试剂: " + list0);
        System.out.println("在库试剂数量: "+list0.size());

        List<String> diff = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>(list0.size() + list1.size());
        List<String> maxList = list0;
        List<String> minList = list1;
        if (list1.size() > list0.size()) {
            maxList = list1;
            minList = list0;
        }
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            Integer count = map.get(string);
            if (count != null) {
                map.put(string, ++count);
                continue;
            }
            map.put(string, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    /**
     * 查询所有试剂的灭火器标志位
     * @return list
     */
    public List<String> queryReagentFire() {
        List<String> list = new ArrayList<>();
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select a.reagentCode from " +
            tableName + " as a where a.reagentStatus=1", new String[]{})) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String reagentCode = cursor.getString(cursor.getColumnIndex(Item.REAGENT_CODE));
                    Log.d(TAG, "queryReagentFire: 试剂码" + reagentCode );
                    String subReagentCode = reagentCode.substring(16, 18);
                    Log.d(TAG, "queryReagentFire: 灭火器码" + subReagentCode);
                    switch (subReagentCode) {
                        case "27":
                            break;
                        case "28":
                            break;
                        case "29":
                            break;
                        case "30":
                            list.add("01");
                            list.add("09");
                            break;
                        case "31":
                            list.add("01");
                            list.add("09");
                            list.add("12");
                            list.add("20");
                            break;
                        case "32":
                            list.add("01");
                            list.add("16");
                            list.add("20");
                            break;
                        case "33":
                            list.add("01");
                            list.add("09");
                            list.add("16");
                            list.add("20");
                            break;
                        case "34":
                            list.add("01");
                            list.add("09");
                            list.add("12");
                            list.add("17");
                            list.add("20");
                            break;
                        case "35":
                            list.add("01");
                            list.add("09");
                            list.add("12");
                            break;
                        case "36":
                            list.add("01");
                            list.add("09");
                            list.add("16");
                            list.add("17");
                            list.add("19");
                            list.add("20");
                            break;
                        case "37":
                            list.add("09");
                            list.add("12");
                            list.add("17");
                            list.add("20");
                            break;
                        case "38":
                            list.add("01");
                            list.add("19");
                            list.add("20");
                            break;
                        case "39":
                            list.add("01");
                            list.add("09");
                            list.add("17");
                            break;
                        case "40":
                            list.add("09");
                            list.add("12");
                            list.add("19");
                            list.add("20");
                            break;
                        case "41":
                            list.add("01");
                            list.add("09");
                            list.add("12");
                            list.add("19");
                            break;
                        case "42":
                            list.add("01");
                            list.add("20");
                            break;
                        case "43":
                            list.add("09");
                            list.add("16");
                            list.add("17");
                            list.add("20");
                            break;
                        case "44":
                            list.add("12");
                            list.add("19");
                            list.add("20");
                            break;
                        case "45":
                            list.add("09");
                            list.add("20");
                            break;
                        case "46":
                            list.add("01");
                            list.add("09");
                            list.add("20");
                            break;
                        case "47":
                            list.add("19");
                            list.add("20");
                            break;
                        case "48":
                            list.add("01");
                            list.add("12");
                            list.add("20");
                            break;
                        case "49":
                            list.add("01");
                            list.add("09");
                            list.add("12");
                            list.add("17");
                            break;
                        case "50":
                            list.add("09");
                            list.add("12");
                            list.add("17");
                            break;
                        case "51":
                            list.add("01");
                            list.add("09");
                            list.add("12");
                            list.add("19");
                            list.add("20");
                            break;
                        case "52":
                            list.add("09");
                            list.add("17");
                            break;
                        case "53":
                            list.add("01");
                            list.add("09");
                            list.add("16");
                            list.add("17");
                            break;
                        case "54":
                            list.add("01");
                            list.add("09");
                            list.add("12");
                            list.add("16");
                            list.add("17");
                            list.add("20");
                            break;
                        case "55":
                            list.add("01");
                            list.add("09");
                            list.add("12");
                            list.add("16");
                            list.add("20");
                            break;
                        case "56":
                            list.add("01");
                            list.add("17");
                            list.add("20");
                            break;
                        case "57":
                            list.add("01");
                            list.add("16");
                            break;
                        case "58":
                            list.add("09");
                            list.add("17");
                            list.add("20");
                            break;
                        case "59":
                            list.add("19");
                            list.add("23");
                            break;
                        case "60":
                            list.add("17");
                            list.add("19");
                            list.add("20");
                            break;
                        case "61":
                            list.add("17");
                            list.add("20");
                            break;
                        case "62":
                            list.add("01");
                            list.add("19");
                            break;
                        case "63":
                            list.add("01");
                            list.add("17");
                            list.add("19");
                            list.add("20");
                            break;
                        case "64":
                            list.add("20");
                            list.add("25");
                            break;
                        case "65":
                            list.add("09");
                            list.add("17");
                            list.add("19");
                            list.add("20");
                            break;
                        case "66":
                            list.add("20");
                            list.add("21");
                            break;
                        case "67":
                            list.add("20");
                            list.add("21");
                            list.add("22");
                            break;
                        case "68":
                            list.add("20");
                            list.add("22");
                            break;
                        case "69":
                            list.add("01");
                            list.add("09");
                            list.add("16");
                            list.add("19");
                            list.add("20");
                            break;
                        case "70":
                            list.add("09");
                            list.add("19");
                            list.add("20");
                            break;
                        case "71":
                            list.add("12");
                            list.add("17");
                            break;
                        case "72":
                            list.add("17");
                            list.add("19");
                            break;
                        case "73":
                            list.add("20");
                            list.add("23");
                            break;
                        case "74":
                            list.add("01");
                            list.add("09");
                            list.add("16");
                            break;
                        case "75":
                            list.add("01");
                            list.add("12");
                            list.add("17");
                            list.add("20");
                            break;
                        case "76":
                            list.add("01");
                            list.add("06");
                            list.add("09");
                            list.add("16");
                            break;
                        case "77":
                            list.add("09");
                            list.add("16");
                            list.add("20");
                            break;
                        case "78":
                            list.add("09");
                            list.add("12");
                            list.add("19");
                            break;
                        case "79":
                            list.add("12");
                            list.add("17");
                            list.add("20");
                            break;
                        case "80":
                            list.add("01");
                            list.add("09");
                            list.add("16");
                            list.add("17");
                            list.add("20");
                            break;
                        case "81":
                            list.add("09");
                            list.add("12");
                            list.add("20");
                            break;
                        case "82":
                            list.add("01");
                            list.add("09");
                            list.add("17");
                            list.add("20");
                            break;
                        case "83":
                            list.add("12");
                            list.add("20");
                            break;
                        case "84":
                            list.add("01");
                            list.add("06");
                            list.add("09");
                            list.add("12");
                            list.add("20");
                            break;
                        case "85":
                            list.add("01");
                            list.add("06");
                            list.add("16");
                            list.add("20");
                            break;
                        case "86":
                            list.add("01");
                            list.add("06");
                            list.add("09");
                            list.add("12");
                            break;
                        case "87":
                            list.add("01");
                            list.add("06");
                            list.add("20");
                            break;
                        case "88":
                            list.add("01");
                            list.add("09");
                            list.add("16");
                            list.add("19");
                            break;
                        case "89":
                            list.add("01");
                            list.add("06");
                            list.add("09");
                            list.add("20");
                            break;
                        case "90":
                            list.add("01");
                            list.add("06");
                            list.add("09");
                            list.add("16");
                            list.add("20");
                            break;
                        case "91":
                            list.add("06");
                            list.add("09");
                            list.add("12");
                            list.add("20");
                            break;
                        case "92":
                            list.add("09");
                            list.add("16");
                            list.add("17");
                            list.add("19");
                            break;
                        case "93":
                            list.add("06");
                            list.add("09");
                            list.add("12");
                            break;
                        case "94":
                            list.add("01");
                            list.add("09");
                            list.add("19");
                            list.add("20");
                            break;
                        case "95":
                            list.add("09");
                            list.add("19");
                            break;
                        case "96":
                            list.add("01");
                            list.add("16");
                            list.add("17");
                            list.add("20");
                            break;
                        default:
                            list.add(subReagentCode);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("灭火器码: " + list);
        HashSet hashSet = new HashSet(list);
        list.clear();
        list.addAll(hashSet);
        System.out.println("灭火器码（不重复）: " + list);
        return list;
    }

    /**
     * 适配领用试剂表
     * @param strCabinetCode 柜号
     * @return 游标
     */
    public Cursor adapterReagentOutCab(String strCabinetCode) {
        return SQLiteManager.getInstance().db.rawQuery("select distinct a._id,c.userName,a.reagentName,a.reagentType," +
            "a.specification,a.masterMetering,a.itemNumber,a.residualAmount,a.supplier,b.operateTime from " + tableName +
            " as a inner join "+ Table.OPERATION_TABLE + " as b on a.reagentCode=b.reagentCode inner join "+
            Table.PERSON_TABLE+" as c on c.userId=b.userId where a.realStatus=0 and a.cabinetCode=? group by a.reagentCode " +
            "order by b.operateTime DESC", new String[]{strCabinetCode});
    }

    /**
     * 适配在柜试剂表
     * @param strCabinetCode 柜号
     * @return 游标
     */
    public Cursor adapterReagentInCab(String strCabinetCode) {
        return SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.reagentName,a.reagentType,a.specification," +
               "a.masterMetering,a.itemNumber,a.residualAmount,a.reagentFlag,c.userName from " + tableName + " as a inner join " +
               Table.OPERATION_TABLE + " as b on a.reagentCode=b.reagentCode inner join " + Table.PERSON_TABLE +
               " as c on b.userId=c.userId where a.realStatus=1 and a.cabinetCode=? group by a.reagentCode order by b.operateTime" +
               " DESC", new String[]{strCabinetCode});
    }

    /**
     * 适配未入库试剂表
     * @return 游标
     */
    public Cursor adapterReagentNotEntry() {
        return SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.reagentName,a.specification,a.masterMetering," +
                "a.itemNumber,a.cabinetCode,b.realStatusContent from " +  tableName + " as a inner join " + Table.HELPER_TABLE +
                " as b on a.realStatus=b.realStatus where a.reagentStatus=0 group by a.reagentCode order by a.cabinetCode", new String[]{});
    }

    /**
     * 适配已入库试剂表
     * @return 游标
     */
    public Cursor adapterReagentHasEntry() {
        return SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.reagentName,a.specification,a.masterMetering," +
                "a.residualAmount,a.cabinetCode,d.realStatusContent,c.userName from " + tableName + " as a inner join "+
                Table.OPERATION_TABLE + " as b on a.reagentCode=b.reagentCode inner join " + Table.PERSON_TABLE +
                " as c on b.userId=c.userId inner join " + Table.HELPER_TABLE + " as d on a.realStatus=d.realStatus where " +
                "a.reagentStatus=1 group by a.reagentCode order by b.operateTime DESC", new String[]{});
    }

    /**
     * 适配已入库试剂
     * @return list
     */
    public List<String> adapterReagentHasEntryList() {
        try (Cursor cursor = SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.reagentName,a.specification," +
                "a.masterMetering,a.residualAmount,a.cabinetCode,d.realStatusContent,c.userName from " + tableName +
                " as a inner join " + Table.OPERATION_TABLE + " as b on a.reagentCode=b.reagentCode inner join " +
                Table.PERSON_TABLE + " as c on b.userId=c.userId inner join " + Table.HELPER_TABLE + " as d on a.realStatus=d.realStatus" +
                " where a.reagentStatus=1 group by a.reagentCode order by b.operateTime DESC", new String[]{})) {
            if (cursor != null) {
                List<String> list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    String reagentName = cursor.getString(cursor.getColumnIndex(Item.REAGENT_NAME));
                    String specification = cursor.getString(cursor.getColumnIndex(Item.SPECIFICATION));
                    String masterMetering = cursor.getString(cursor.getColumnIndex(Item.MASTER_METERING));
                    String residualAmount = cursor.getString(cursor.getColumnIndex(Item.RESIDUAL_AMOUNT));
                    String cabinetCode = cursor.getString(cursor.getColumnIndex(Item.CABINET_CODE));
                    String realStatusContent = cursor.getString(cursor.getColumnIndex(Item.REAL_STATUS_CONTENT));
                    String useName = cursor.getString(cursor.getColumnIndex(Item.USER_NAME));
                    list.add(reagentName);
                    list.add(specification);
                    list.add(masterMetering);
                    list.add(residualAmount);
                    list.add(cabinetCode);
                    list.add(realStatusContent);
                    list.add(useName);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 适配报废试剂表
     * @return 游标
     */
    public Cursor adapterDisCard() {
        return SQLiteManager.getInstance().db.rawQuery("select distinct a._id,a.reagentName,a.itemNumber,a.supplier,d.storageTime," +
                "a.cabinetCode,a.masterMetering,c.userName,b.operateTime,e.realStatusContent from "
                + tableName + " as a inner join " + Table.OPERATION_TABLE + " as b on a.reagentCode=b.reagentCode"
                + " inner join "+ Table.REAGENT_ENTRY_TABLE + " as d on a.reagentCode=d.reagentCode"
                + " inner join "+ Table.CABINET_TABLE + " as e on a.realStatus=e.realStatus"
                + " inner join "+ Table.PERSON_TABLE+" as c on c.userId=b.userId where a.reagentStatus=3 group by a.reagentCode order by b.operateTime DESC", new String[]{});
    }

}
