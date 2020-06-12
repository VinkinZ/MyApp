package com.sinano.rfidrccs.bean;

import java.io.Serializable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 一条试剂柜管理数据
 */
public class CabinetBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String departmentName;
    private String cabinetCode;
    private String cabinetType;
    private int cabinetPlace;
    private int validFlag;
    private String barCode;
    private int registerFlag;

    public CabinetBean(String departmentName, String cabinetCode, String cabinetType, int cabinetPlace, int validFlag) {
        this.departmentName = departmentName;
        this.cabinetCode = cabinetCode;
        this.cabinetType = cabinetType;
        this.cabinetPlace = cabinetPlace;
        this.validFlag = validFlag;
    }

    public CabinetBean(String cabinetCode, String cabinetType, int cabinetPlace, int validFlag, String barCode) {
        this.cabinetCode = cabinetCode;
        this.cabinetType = cabinetType;
        this.cabinetPlace = cabinetPlace;
        this.validFlag = validFlag;
        this.barCode = barCode;
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

    public String getCabinetCode() {
        return cabinetCode;
    }

    public void setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
    }

    public String getCabinetType() {
        return cabinetType;
    }

    public void setCabinetType(String cabinetType) {
        this.cabinetType = cabinetType;
    }

    public int getCabinetPlace() {
        return cabinetPlace;
    }

    public void setCabinetPlace(int cabinetPlace) {
        this.cabinetPlace = cabinetPlace;
    }

    public int getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(int validFlag) {
        this.validFlag = validFlag;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getRegisterFlag() {
        return registerFlag;
    }

    public void setRegisterFlag(int registerFlag) {
        this.registerFlag = registerFlag;
    }
}
