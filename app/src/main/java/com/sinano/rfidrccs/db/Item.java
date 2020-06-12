package com.sinano.rfidrccs.db;

/**
 * Author: Vinkin
 * Email:zwj96812@163.com
 * Date: 2020/5/24
 * Description: 表字段类
 */
public class Item {
    public String text = "";
    public String type = "";

    public Item() {
    }

    public Item(String text, String type) {
        this.text = text;
        this.type = type;
    }

    // 字段类型
    public static final String ITEM_TYPE_VARCHAR = "item_type_varchar";
    public static final String ITEM_TYPE_VARCHAR_D = "item_type_varchar_d";
    public static final String ITEM_TYPE_VARCHAR_NN = "item_type_varchar_nn";
    public static final String ITEM_TYPE_VARCHAR_UNN = "item_type_varchar_unn";
    public static final String ITEM_TYPE_INTEGER = "item_type_integer";
    public static final String ITEM_TYPE_INTEGER_D0 = "item_type_integer_d0";
    public static final String ITEM_TYPE_INTEGER_NN = "item_type_integer_nn";
    public static final String ITEM_TYPE_INTEGER_UNN = "item_type_integer_unn";
    public static final String ITEM_TYPE_DOUBLE = "item_type_double";
    public static final String ITEM_TYPE_DOUBLE_D0 = "item_type_double_d0";
    public static final String ITEM_TYPE_TIMESTAMP_D = "item_type_timestamp_d";
    public static final String ITEM_TYPE_TIMESTAMP_NND = "item_type_timestamp_nnd";
    public static final String ITEM_TYPE_TEXT = "item_type_text";

    // 通用字段
    public static final String _ID = "_id";
    public static final String SEND_FLAG = "sendFlag";


    // 人员管理表字段
    //public static final String _ID = "_id";
    public static final String USER_ID = "userId";
    public static final String UNIT = "unit";
    public static final String DEPARTMENT_NAME = "departmentName";
    public static final String USER_NAME = "userName";
    public static final String ARP = "arp";
    public static final String ROLE_CODE = "roleCode";
    public static final String SAFE_SCORE = "safeScore";
    public static final String VALID_FLAG = "validFlag";
    //public static final String SEND_FLAG = "sendFlag";


    // 试剂管理表字段
    //public static final String _ID = "_id";
    public static final String REAGENT_CODE = "reagentCode";
    //public static final String DEPARTMENT_NAME = "departmentName";
    public static final String REAGENT_NAME = "reagentName";
    public static final String SPECIFICATION = "specification";
    public static final String MASTER_METERING = "masterMetering";
    public static final String ITEM_NUMBER = "itemNumber";
    public static final String RESIDUAL_AMOUNT = "residualAmount";
    public static final String CABINET_CODE = "cabinetCode";
    public static final String REAGENT_TYPE = "reagentType";
    public static final String SUPPLIER = "supplier";
    public static final String REAGENT_FLAG ="reagentFlag";
    public static final String REAL_STATUS = "realStatus";
    //public static final String USER_ID = "userId";
    public static final String REAGENT_STATUS = "reagentStatus";
    //public static final String SEND_FLAG = "sendFlag";


    // 操作记录表字段
    //public static final String _ID = "_id";
    //public static final String USER_ID = "userId";
    //public static final String REAGENT_CODE = "reagentCode";
    public static final String OPERATE_TIME= "operateTime";
    //public static final String CABINET_CODE = "cabinetCode";
    public static final String OPERATE_STATE= "operateState";
    public static final String REAL_WEIGHT = "realWeight";
    public static final String MISPLACE = "misplace";
    //public static final String SEND_FLAG = "sendFlag";


    // 环境信息表字段
    //public static final String _ID = "_id";
    //public static final String DEPARTMENT_NAME = "departmentName";
    public static final String TEMPERATURE01 = "temperature01";
    public static final String HUMIDITY01 = "humidity01";
    public static final String VOC01 = "voc01";
    public static final String TEMPERATURE02 = "temperature02";
    public static final String HUMIDITY02 = "humidity02";
    public static final String VOC02 = "voc02";
    public static final String TEMPERATURE03 = "temperature03";
    public static final String HUMIDITY03 = "humidity03";
    public static final String VOC03 = "voc03";
    public static final String TEMPERATURE04 = "temperature04";
    public static final String HUMIDITY04 = "humidity04";
    public static final String VOC04 = "voc04";
    public static final String ENVIRONMENT_WARNING = "environmentWarning";
    public static final String UPDATE_TIME = "updateTime";
    //public static final String SEND_FLAG = "sendFlag";


    // 预警信息表字段
    //public static final String _ID = "_id";
    //public static final String USER_ID = "userId";
    //public static final String CABINET_CODE = "cabinetCode";
    public static final String WARNING_ID = "warningId";
    public static final String WARNING_ID_STRING = "warningIdString";
    public static final String WARNING_TIME = "warningTime";
    public static final String WARNING_LEVEL = "warningLevel";
    //public static final String SEND_FLAG = "sendFlag";


    // 角色管理表字段
    //public static final String _ID = "_id";
    //public static final String ROLE_CODE = "roleCode";
    public static final String ROLE_NAME = "roleName";
    public static final String AUTH_LEVEL = "authLevel";


    // 试剂柜管理表字段
    //public static final String _ID = "_id";
    //public static final String DEPARTMENT_CODE = "departmentCode";
    //public static final String CABINET_NAME = "cabinetName";
    //public static final String CABINET_CODE = "cabinetCode";
    public static final String CABINET_TYPE = "cabinetType";
    public static final String CABINET_PLACE = "cabinetPlace";
    //public static final String VALID_FLAG = "validFlag";
    //public static final String SEND_FLAG = "sendFlag";
    public static final String BAR_CODE = "barCode";
    public static final String REGISTER_FLAG = "registerFlag";


    // 上位机信息表字段
    //public static final String _ID = "_id";
    public static final String IP = "ip";
    public static final String MASK = "mask";
    public static final String URL = "URL";
    public static final String FIRE_URL = "fireURL";
    //public static final String SEND_FLAG = "sendFlag";


    // 数据更新时间表字段
    //public static final String _ID = "_id";
    public static final String TIME_UPDATE = "timeUpdate";
    public static final String PERSON_UPDATE_TIME = "personUpdateTime";
    public static final String REAGENT_UPDATE_TIME = "reagentUpdateTime";
    public static final String OPERATION_UPDATE_TIME = "operationUpdateTime";
    public static final String ENVIRONMENT_UPDATE_TIME = "environmentUpdateTime";
    public static final String WARNING_UPDATE_TIME = "warningUpdateTime";
    public static final String WARNING_ID_UPDATE_TIME = "warningIdUpdateTime";
    public static final String CABINET_UPDATE_TIME = "cabinetUpdateTime";
    public static final String REAGENT1_DOWNLOAD_TIME = "reagent1DownloadTime";
    public static final String REAGENT2_DOWNLOAD_TIME = "reagent2DownloadTime";
    public static final String REAGENT3_DOWNLOAD_TIME = "reagent3DownloadTime";
    public static final String REAGENT4_DOWNLOAD_TIME = "reagent4DownloadTime";
    public static final String PERSON_DOWNLOAD_TIME = "personDownloadTime";



    // 试剂入库时间字段
    //public static final String _ID = "_id";
    //public static final String REAGENT_CODE = "reagentCode";
    public static final String ENTRY_TIME = "entryTime";


    // 内容信息表字段
    //public static final String _ID = "_id";
    //public static final String VALID_FLAG = "validFlag";
    public static final String VALID_FLAG_CONTENT = "validFlagContent";
    //public static final String REAL_STATUS = "realStatus";
    public static final String REAL_STATUS_CONTENT = "realStatusContent";
    //public static final String REAGENT_STATUS = "reagentStatus";
    public static final String REAGENT_STATUS_CONTENT = "reagentStatusContent";
    //public static final String OPERATE_STATE = "operateState";
    public static final String OPERATE_STATE_CONTENT = "operateStateContent";
    //public static final String WARNING_ID = "warningId";
    public static final String WARNING_ID_CONTENT = "warningIdContent";
    //public static final String CABINET_TYPE = "cabinetType";
    public static final String CABINET_TYPE_CONTENT = "cabinetTypeContent";
    //public static final String CABINET_PLACE = "cabinetPlace";
    public static final String CABINET_PLACE_CONTENT = "cabinetPlaceContent";
    public static final String FIRE_EXTINGUISHER = "fireExtinguisher";
    public static final String FIRE_EXTINGUISHER_NAME = "fireExtinguisherName";
    public static final String SETTING = "setting";
    public static final String MAX_AMOUNT = "maxAmount";
    public static final String CHECK_TIME = "checkTime";
    public static final String POWER_OFF= "powerOff";
    public static final String POWER_ON = "powerOn";
    public static final String INITIAL_CODE = "initialCode";
}
