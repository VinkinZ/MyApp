package com.sinano.rfidrccs.bean;

import java.io.Serializable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 一条入库试剂数据
 */
public class ReagentEntryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reagentCode;

    public ReagentEntryBean(String reagentCode) {
        this.reagentCode = reagentCode;
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
}
