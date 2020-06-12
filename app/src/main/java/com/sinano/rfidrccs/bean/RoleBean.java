package com.sinano.rfidrccs.bean;

import java.io.Serializable;

/**
 * Author: Vinkin
 * Email: zwj96812@163.com
 * Date: 2020/5/25
 * Description: 一条角色数据
 */
public class RoleBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int roleCode;
    private String roleName;
    private int authLevel;

    public RoleBean(int roleCode, String roleName, int authLevel) {
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.authLevel = authLevel;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(int authLevel) {
        this.authLevel = authLevel;
    }
}
