package com.sinano.rfidrccs.bean;

import java.io.Serializable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 一条人员数据
 */
public class PersonBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String unit;
    private String departmentName;
    private String userName;
    private String arp;
    private int roleCode;
    private int safeScore;
    private int validFlag;


    public PersonBean(String userId, String unit, String departmentName, String userName,
                      String arp, int roleCode, int safeScore, int validFlag) {
        this.userId = userId;
        this.unit = unit;
        this.departmentName = departmentName;
        this.userName = userName;
        this.arp = arp;
        this.roleCode = roleCode;
        this.safeScore = safeScore;
        this.validFlag = validFlag;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArp() {
        return arp;
    }

    public void setArp(String arp) {
        this.arp = arp;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }

    public int getSafeScore() {
        return safeScore;
    }

    public void setSafeScore(int safeScore) {
        this.safeScore = safeScore;
    }

    public int getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(int validFlag) {
        this.validFlag = validFlag;
    }

}
