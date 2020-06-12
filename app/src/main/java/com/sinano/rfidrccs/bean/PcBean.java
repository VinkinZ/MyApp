package com.sinano.rfidrccs.bean;

import java.io.Serializable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 一条上位机信息
 */
public class PcBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ip;
    private String mask;
    private String url;
    private String fireUrl;

    public PcBean(String ip, String mask, String url, String fireUrl) {
        this.ip = ip;
        this.mask = mask;
        this.url = url;
        this.fireUrl = fireUrl;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFireUrl() {
        return fireUrl;
    }

    public void setFireUrl(String fireUrl) {
        this.fireUrl = fireUrl;
    }
}
