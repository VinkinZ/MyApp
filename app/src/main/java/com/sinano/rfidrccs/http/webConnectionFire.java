package com.sinano.rfidrccs.http;

import com.alibaba.fastjson.JSONObject;
import com.sinano.rfidrccs.utils.JSONUtil;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30
 * Description: 灭火器信息下载
 */
public class webConnectionFire {
    private String url_Fire;

    public webConnectionFire(String url_fire){
        this.url_Fire = url_fire;
    }

    // 灭火器信息下载（Post）
    public JSONObject getAnnihilator(String barCode){

        JSONObject jo_annihilator = new JSONObject();
        OkHttpClient ohc_annihilator = HttpHelper.getInstance().getOkHttpClient();

        try {
            FormBody formBody = new FormBody.Builder()
                    .add("params",barCode)
                    .build();

            Request req_annihilator = new Request.Builder().url(url_Fire).addHeader("userId","admin").post(formBody).build();
            Response res_annihilator = ohc_annihilator.newCall(req_annihilator).execute();

            if (res_annihilator.isSuccessful()){
                jo_annihilator = JSONUtil.str2JsonObject(res_annihilator.body().string());
            }else {
                jo_annihilator = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jo_annihilator;
    }
}
