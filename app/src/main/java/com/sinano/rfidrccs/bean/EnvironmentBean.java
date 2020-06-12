package com.sinano.rfidrccs.bean;

import java.io.Serializable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 一条环境信息数据
 */
public class EnvironmentBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String departmentName;
    private double temperature01;
    private double humidity01;
    private double voc01;
    private double temperature02;
    private double humidity02;
    private double voc02;
    private double temperature03;
    private double humidity03;
    private double voc03;
    private double temperature04;
    private double humidity04;
    private double voc04;
    private String environmentWarning;
    private String updateTime;

    public EnvironmentBean(String departmentName,
                           double temperature01, double humidity01, double voc01,
                           double temperature02, double humidity02, double voc02,
                           double temperature03, double humidity03, double voc03,
                           double temperature04, double humidity04, double voc04,
                           String environmentWarning) {
        this.departmentName = departmentName;
        this.temperature01 = temperature01;
        this.humidity01 = humidity01;
        this.voc01 = voc01;
        this.temperature02 = temperature02;
        this.humidity02 = humidity02;
        this.voc02 = voc02;
        this.temperature03 = temperature03;
        this.humidity03 = humidity03;
        this.voc03 = voc03;
        this.temperature04 = temperature04;
        this.humidity04 = humidity04;
        this.voc04 = voc04;
        this.environmentWarning = environmentWarning;
    }

    public EnvironmentBean(double temperature01, double humidity01, double voc01, String environmentWarning, String updateTime) {
        this.temperature01 = temperature01;
        this.humidity01 = humidity01;
        this.voc01 = voc01;
        this.environmentWarning = environmentWarning;
        this.updateTime = updateTime;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public double getTemperature01() {
        return temperature01;
    }

    public void setTemperature01(double temperature01) {
        this.temperature01 = temperature01;
    }

    public double getHumidity01() {
        return humidity01;
    }

    public void setHumidity01(double humidity01) {
        this.humidity01 = humidity01;
    }

    public double getVoc01() {
        return voc01;
    }

    public void setVoc01(double voc01) {
        this.voc01 = voc01;
    }

    public double getTemperature02() {
        return temperature02;
    }

    public void setTemperature02(double temperature02) {
        this.temperature02 = temperature02;
    }

    public double getHumidity02() {
        return humidity02;
    }

    public void setHumidity02(double humidity02) {
        this.humidity02 = humidity02;
    }

    public double getVoc02() {
        return voc02;
    }

    public void setVoc02(double voc02) {
        this.voc02 = voc02;
    }

    public double getTemperature03() {
        return temperature03;
    }

    public void setTemperature03(double temperature03) {
        this.temperature03 = temperature03;
    }

    public double getHumidity03() {
        return humidity03;
    }

    public void setHumidity03(double humidity03) {
        this.humidity03 = humidity03;
    }

    public double getVoc03() {
        return voc03;
    }

    public void setVoc03(double voc03) {
        this.voc03 = voc03;
    }

    public double getTemperature04() {
        return temperature04;
    }

    public void setTemperature04(double temperature04) {
        this.temperature04 = temperature04;
    }

    public double getHumidity04() {
        return humidity04;
    }

    public void setHumidity04(double humidity04) {
        this.humidity04 = humidity04;
    }

    public double getVoc04() {
        return voc04;
    }

    public void setVoc04(double voc04) {
        this.voc04 = voc04;
    }

    public String getEnvironmentWarning() {
        return environmentWarning;
    }

    public void setEnvironmentWarning(String environmentWarning) {
        this.environmentWarning = environmentWarning;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
