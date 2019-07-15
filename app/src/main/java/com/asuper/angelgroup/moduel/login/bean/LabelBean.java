package com.asuper.angelgroup.moduel.login.bean;

import java.io.Serializable;

/**
 * Created by shubei on 2017/6/29.
 */

public class LabelBean implements Serializable {

    private int roleId;
    private String roleName;
    private boolean isChosen;

    public LabelBean() {
    }

    public LabelBean(int roleId, String roleName, boolean isChosen) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.isChosen = isChosen;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }
}
