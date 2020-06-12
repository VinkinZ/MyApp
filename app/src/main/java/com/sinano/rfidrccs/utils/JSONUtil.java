package com.sinano.rfidrccs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30
 * Description: JOSN数据工具类
 */
public class JSONUtil extends JSON{
    /**
     * 把json格式字符串转化为JsonObject
     * @param string json类型String
     * @return JsonObject
     */
    public static JSONObject str2JsonObject(String string){
        return JSONObject.parseObject(string);
    }

    /**
     * 把json格式字符串转化为JsonArray
     * @param string json类型String
     * @return JsonArray
     */
    public static JSONArray str2JsonArray(String string){
        return JSONArray.parseArray(string);
    }

    public static String jsonObject2Str(JSONObject jsonObject){
        return JSON.toJSONString(jsonObject);
    }

    public static String jsonArray2Str(JSONArray jsonArray){
        return JSON.toJSONString(jsonArray);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return 指定的java对象
     */
    public static <T> T getJsonToBean(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 功能描述：把java对象转换成JSON数据
     * @param object java对象
     * @return JSON数据
     */
    public static String getBeanToJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 功能描述：把JSON数据转换成指定的java对象列表
     * @param jsonData JSON数据
     * @param clazz 指定的java对象
     * @return List<T>
     */
    public static <T> List<T> getJsonToList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }

    /**
     * 功能描述：把JSON数据转换成较为复杂的List<Map<String, Object>>
     * @param jsonData JSON数据
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> getJsonToListMap(String jsonData) {
        return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {});
    }
}
