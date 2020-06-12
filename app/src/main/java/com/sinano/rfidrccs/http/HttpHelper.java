package com.sinano.rfidrccs.http;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/30
 * Description: 单例模式获取HttpHelper对象
 */
public class HttpHelper {

    private OkHttpClient okHttpClient;

    private HttpHelper(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        int DEFAULT_TIMEOUT = 15;
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient = builder.build();
    }

    public static HttpHelper getInstance(){
        return HttpHelperHolder.instance;
    }

    OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private static class HttpHelperHolder{
        private static HttpHelper instance = new HttpHelper();
    }
}
