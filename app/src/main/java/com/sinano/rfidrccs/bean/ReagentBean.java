package com.sinano.rfidrccs.bean;

import java.io.Serializable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 一条试剂数据
 */
public class ReagentBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reagentCode;
    private String departmentName;
    private String reagentName;
    private String specification;
    private String masterMetering;
    private String itemNumber;
    private double residualAmount;
    private String cabinetCode;
    private String reagentType;
    private String supplier;
    private String reagentFlag;
    private int realStatus;
    private String userId;
    private int reagentStatus;
    private String operateTime;

    public ReagentBean(String reagentCode, String departmentName, String reagentName, String specification,
                       String masterMetering, String itemNumber, double residualAmount, String cabinetCode,
                       String reagentType, String supplier, int reagentStatus) {
        this.reagentCode = reagentCode;
        this.departmentName = departmentName;
        this.reagentName = reagentName;
        this.specification = specification;
        this.masterMetering = masterMetering;
        this.itemNumber = itemNumber;
        this.residualAmount = residualAmount;
        this.cabinetCode = cabinetCode;
        this.reagentType = reagentType;
        this.supplier = supplier;
        this.reagentStatus = reagentStatus;
    }

    public ReagentBean(String reagentCode, double residualAmount, String cabinetCode, int realStatus,
                       String userId, int reagentStatus, String operateTime) {
        this.reagentCode = reagentCode;
        this.residualAmount = residualAmount;
        this.cabinetCode = cabinetCode;
        this.realStatus = realStatus;
        this.userId = userId;
        this.reagentStatus = reagentStatus;
        this.operateTime = operateTime;
    }

    @Override
    public String toString() {
        return "ReagentBean{" +
                "reagentCode='" + reagentCode + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", reagentName='" + reagentName + '\'' +
                ", specification='" + specification + '\'' +
                ", masterMetering='" + masterMetering + '\'' +
                ", itemNumber='" + itemNumber + '\'' +
                ", residualAmount=" + residualAmount +
                ", cabinetCode='" + cabinetCode + '\'' +
                ", reagentType='" + reagentType + '\'' +
                ", supplier='" + supplier + '\'' +
                ", reagentFlag='" + reagentFlag + '\'' +
                ", realStatus=" + realStatus +
                ", userId='" + userId + '\'' +
                ", reagentStatus=" + reagentStatus +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getReagentCode() {
        return reagentCode;
    }

    public void setReagentCode(String reagentCode) {
        this.reagentCode = reagentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getReagentName() {
        return reagentName;
    }

    public void setReagentName(String reagentName) {
        this.reagentName = reagentName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getMasterMetering() {
        return masterMetering;
    }

    public void setMasterMetering(String masterMetering) {
        this.masterMetering = masterMetering;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public double getResidualAmount() {
        return residualAmount;
    }

    public void setResidualAmount(double residualAmount) {
        this.residualAmount = residualAmount;
    }

    public String getCabinetCode() {
        return cabinetCode;
    }

    public void setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
    }

    public String getReagentType() {
        return reagentType;
    }

    public void setReagentType(String reagentType) {
        this.reagentType = reagentType;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getReagentFlag() {
        return reagentFlag;
    }

    public void setReagentFlag(String reagentFlag) {
        this.reagentFlag = reagentFlag;
    }

    public int getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(int realStatus) {
        this.realStatus = realStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getReagentStatus() {
        return reagentStatus;
    }

    public void setReagentStatus(int reagentStatus) {
        this.reagentStatus = reagentStatus;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
