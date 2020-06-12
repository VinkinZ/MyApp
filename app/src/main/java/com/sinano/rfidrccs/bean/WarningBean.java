package com.sinano.rfidrccs.bean;

import java.io.Serializable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 一条预警记录数据
 */
public class WarningBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String cabinetCode;
    private int warningId;
    private String warningIdString;
    private String warningTime;
    private String warningLevel;

    public WarningBean(String userId, String cabinetCode, int warningId, String warningLevel) {
        this.userId = userId;
        this.cabinetCode = cabinetCode;
        this.warningId = warningId;
        this.warningLevel = warningLevel;
    }

    public WarningBean(String userId, String cabinetCode, int warningId, String warningTime, String warningLevel) {
        this.userId = userId;
        this.cabinetCode = cabinetCode;
        this.warningId = warningId;
        this.warningTime = warningTime;
        this.warningLevel = warningLevel;
    }

    public WarningBean(String cabinetCode, String warningIdString, String warningTime) {
        this.cabinetCode = cabinetCode;
        this.warningIdString = warningIdString;
        this.warningTime = warningTime;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCabinetCode() {
        return cabinetCode;
    }

    public void setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
    }

    public int getWarningId() {
        return warningId;
    }

    public void setWarningId(int warningId) {
        this.warningId = warningId;
    }

    public String getWarningIdString() {
        return warningIdString;
    }

    public void setWarningIdString(String warningIdString) {
        this.warningIdString = warningIdString;
    }

    public String getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(String warningTime) {
        this.warningTime = warningTime;
    }

    public String getWarningLevel() {
        return warningLevel;
    }

    public void setWarningLevel(String warningLevel) {
        this.warningLevel = warningLevel;
    }
}
