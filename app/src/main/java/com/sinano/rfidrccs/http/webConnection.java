package com.sinano.rfidrccs.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinano.rfidrccs.utils.JSONUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30
 * Description: 上位机接口工具类
 */
public class webConnection {

    private static String url_basic;

    public webConnection(String url_basic){
        webConnection.url_basic = url_basic;
    }

    // 注册试剂柜（Post）
    public JSONObject registerCabinet(Map controllerData, List containerDataList, String createTime) {

        OkHttpClient ohc_regis = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_regisRes = new JSONObject();

        Map<String,Object> regisReq_map = new HashMap<>();

        try{
            regisReq_map.put("controllerData", controllerData);
            regisReq_map.put("containerDataList", containerDataList);
            regisReq_map.put("createTime", createTime);

            System.out.println("regisreq:    " + JSON.toJSONString(regisReq_map));

            RequestBody rbRegis = FormBody.create(MediaType.parse("application/json;charset=utf-8"), JSON.toJSONString(regisReq_map));
            Request reqRegis = new Request.Builder().url(url_basic + "AutheTestCabinet").post(rbRegis).build();

            Response resRegis = ohc_regis.newCall(reqRegis).execute();

            if(resRegis.isSuccessful()){
                jo_regisRes = JSONUtil.str2JsonObject(resRegis.body().string());
            }else{
                jo_regisRes = null;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return jo_regisRes;
    }

    // 更新试剂柜（Post）
    public static JSONObject updateCabinet(Map controllerData, List containerDataList, String updateTime) {

        OkHttpClient ohc_uc = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_ucRes = new JSONObject();

        Map<String,Object> uc_map = new HashMap<>();

        try{

            uc_map.put("controllerData", controllerData);
            uc_map.put("containerDataList", containerDataList);
            uc_map.put("updateTime", updateTime);

            System.out.println("ucreq:    " + JSON.toJSONString(uc_map));

            RequestBody rbuc = FormBody.create(MediaType.parse("application/json;charset=utf-8"), JSON.toJSONString(uc_map));
            Request requc = new Request.Builder().url(url_basic + "updateTestCabinet").post(rbuc).build();

            Response resuc = ohc_uc.newCall(requc).execute();

            if(resuc.isSuccessful()){
                jo_ucRes = JSONUtil.str2JsonObject(resuc.body().string());
            }else{
                jo_ucRes = null;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return jo_ucRes;
    }

    // 报送试剂最新信息（Post）
    public JSONObject updateReagent(String controllerCode, List data) {

        OkHttpClient ohc_ur = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_urRes = new JSONObject();

        Map<String,Object> ur_map = new HashMap<>();

        try{
            ur_map.put("controllerCode", controllerCode);
            ur_map.put("data", data);

            System.out.println("urreq:   " + JSON.toJSONString(ur_map));

            RequestBody rb_ur = FormBody.create(MediaType.parse("application/json;charset=utf-8"), JSON.toJSONString(ur_map));
            Request req_ur = new Request.Builder().url(url_basic + "synchAllCabinet").post(rb_ur).build();

            Response res_ur = ohc_ur.newCall(req_ur).execute();
            if (res_ur.isSuccessful()){
                jo_urRes = JSONUtil.str2JsonObject(res_ur.body().string());
            }else{
                jo_urRes = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jo_urRes;
    }

    // 出入库记录信息上传（Post）
    public JSONObject depotRecord(String controllerCode,List data) {

        OkHttpClient ohc_depot = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_drRes = new JSONObject();

        Map<String,Object> dr_map = new HashMap<>();

        try{
            dr_map.put("controllerCode",controllerCode);
            dr_map.put("data",data);

            System.out.println("drreq:    " + JSON.toJSONString(dr_map));

            RequestBody rb_dr = FormBody.create(MediaType.parse("application/json;charset=utf-8"),JSON.toJSONString(dr_map));
            Request reqDr = new Request.Builder().url(url_basic + "cabinetData").post(rb_dr).build();

            Response resDr  = ohc_depot.newCall(reqDr).execute();
            if(resDr.isSuccessful()){
                jo_drRes = JSONUtil.str2JsonObject(resDr.body().string());
            }else{
                jo_drRes = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jo_drRes;
    }

    // 报警信息上传（Post）
    public JSONObject warning(String controllerCode, List data) {

        OkHttpClient ohc_warning = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_warRes = new JSONObject();

        Map<String,Object> warn_map = new HashMap<>();

        try{
            warn_map.put("controllerCode", controllerCode);
            warn_map.put("data", data);

            System.out.println("warnreq:     " + JSON.toJSONString(warn_map));

            RequestBody rb_war = FormBody.create(MediaType.parse("application/json;charset=utf-8"), JSON.toJSONString(warn_map));
            Request reqWar = new Request.Builder().url(url_basic + "alarm").post(rb_war).build();

            Response resWar = ohc_warning.newCall(reqWar).execute();
            if(resWar.isSuccessful()){
                jo_warRes = JSONUtil.str2JsonObject(resWar.body().string());
            }else{
                jo_warRes = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jo_warRes;
    }

    // 人员违规预警上传（Post）
    public JSONObject staffWarning(String controllerCode, List data) {

        OkHttpClient ohc_sw = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_swRes = new JSONObject();

        Map<String,Object> sw_map = new HashMap<>();

        try{
            sw_map.put("controllerCode", controllerCode);
            sw_map.put("data", data);

            System.out.println("swreq:     " + JSON.toJSONString(sw_map));

            RequestBody rb_sw = FormBody.create(MediaType.parse("application/json;charset=utf-8"), JSON.toJSONString(sw_map));
            Request reqSw = new Request.Builder().url(url_basic + "alarmperson").post(rb_sw).build();

            Response resSw = ohc_sw.newCall(reqSw).execute();

            if(resSw.isSuccessful()) {
                jo_swRes = JSONUtil.str2JsonObject(resSw.body().string());
            }else{
                jo_swRes = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jo_swRes;
    }

    // 环境信息上传（Post）
    public JSONObject environment(String containerCode, List data) {

        OkHttpClient ohc_envir = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_enRes = new JSONObject();

        Map<String,Object> envi_map = new HashMap<>();

        try {
            envi_map.put("containerCode",containerCode);
            envi_map.put("data",data);

            System.out.println("envireq:    " + JSON.toJSONString(envi_map));

            RequestBody rbEn = FormBody.create(MediaType.parse("application/json;charset=utf-8"),JSON.toJSONString(envi_map));
            Request reqEn = new Request.Builder().url(url_basic + "tempSensors").post(rbEn).build();

            Response resEn = ohc_envir.newCall(reqEn).execute();

            System.out.println("envi_code:   " + resEn.code());

            if (resEn.isSuccessful()){
                jo_enRes = JSONUtil.str2JsonObject(resEn.body().string());
            }else{
                jo_enRes = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jo_enRes;
    }

    // 试剂基本信息下载（待入试剂柜）（Get）
    public JSONObject reagentInfo(String startTime, String endTime, String containerCode){

        OkHttpClient ohc_reaInfo = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_reaRes = new JSONObject();

        try {

            Request reqRea = new Request.Builder().url(url_basic + "reagentInfo?startTime="+startTime+"&endTime="+endTime+"&containerCode="+containerCode+"&status=-1").build();

            System.out.println("reqRea:   " + reqRea.toString());
            Response resRea = ohc_reaInfo.newCall(reqRea).execute();

            if(resRea.isSuccessful()){
                jo_reaRes = JSONUtil.str2JsonObject(resRea.body().string());
            }else {
                jo_reaRes = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jo_reaRes;
    }

    // 通过试剂柜编号获取相关人员信息（Get）
    public JSONObject userInfo_ByCon(String containerCode, String startTime, String endTime) {

        OkHttpClient ohc_userInfo = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_userResByCon = new JSONObject();

        try {
            Request reqUser = new Request.Builder().url(url_basic + "userListByContainerCode?containerCode="+containerCode+"&startTime="+startTime+"&endTime="+endTime).build();

            System.out.println("reqUser:   " + reqUser.toString());
            Response resUser = ohc_userInfo.newCall(reqUser).execute();

            if(resUser.isSuccessful()){
                jo_userResByCon = JSONUtil.str2JsonObject(resUser.body().string());
            }else {
                jo_userResByCon = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jo_userResByCon;
    }

    // APP版本接口（Get）
    public JSONObject versionUpdate() {
        OkHttpClient ohc_version = HttpHelper.getInstance().getOkHttpClient();
        JSONObject jo_versionRes = new JSONObject();

        try {
            Request reqVersion = new Request.Builder().url(url_basic + "getContainerVersion").build();

            Response resVersion = ohc_version.newCall(reqVersion).execute();

            if(resVersion.isSuccessful()){
                jo_versionRes = JSONUtil.str2JsonObject(resVersion.body().string());
            }else {
                jo_versionRes = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jo_versionRes;
    }

}
