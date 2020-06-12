package com.sinano.rfidrccs.bean;

import java.io.Serializable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 一条操作记录
 */
public class OperationBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String reagentCode;
    private String operateTime;
    private String cabinetCode;
    private int operateState;
    private int reagentStatus;
    private int realStatus;
    private double realWeight;
    private String misplace;


    public OperationBean(String userId, String reagentCode, String cabinetCode, int operateState) {
        this.userId = userId;
        this.reagentCode = reagentCode;
        this.cabinetCode = cabinetCode;
        this.operateState = operateState;
    }

    public OperationBean(String userId, String reagentCode, String cabinetCode, int operateState, double realWeight) {
        this.userId = userId;
        this.reagentCode = reagentCode;
        this.cabinetCode = cabinetCode;
        this.operateState = operateState;
        this.realWeight = realWeight;
    }

    public OperationBean(String userId, String reagentCode, String cabinetCode, int operateState, String misplace) {
        this.userId = userId;
        this.reagentCode = reagentCode;
        this.cabinetCode = cabinetCode;
        this.operateState = operateState;
        this.misplace = misplace;
    }

    public OperationBean(String userId, String reagentCode, String operateTime, String cabinetCode,
                         int operateState, int reagentStatus, int realStatus, double realWeight) {
        this.userId = userId;
        this.reagentCode = reagentCode;
        this.operateTime = operateTime;
        this.cabinetCode = cabinetCode;
        this.operateState = operateState;
        this.reagentStatus = reagentStatus;
        this.realStatus = realStatus;
        this.realWeight = realWeight;
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

    public String getReagentCode() {
        return reagentCode;
    }

    public void setReagentCode(String reagentCode) {
        this.reagentCode = reagentCode;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getCabinetCode() {
        return cabinetCode;
    }

    public void setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
    }

    public int getOperateState() {
        return operateState;
    }

    public void setOperateState(int operateState) {
        this.operateState = operateState;
    }

    public double getRealWeight() {
        return realWeight;
    }

    public void setRealWeight(double realWeight) {
        this.realWeight = realWeight;
    }

    public String getMisplace() {
        return misplace;
    }

    public void setMisplace(String misplace) {
        this.misplace = misplace;
    }

    public int getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(int realStatus) {
        this.realStatus = realStatus;
    }

    public int getReagentStatus() {
        return reagentStatus;
    }

    public void setReagentStatus(int reagentStatus) {
        this.reagentStatus = reagentStatus;
    }
}
