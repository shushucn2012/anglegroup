package com.asuper.angelgroup.moduel.login.bean;

/**
 * Created by shubei on 2017/12/4.
 */

public class UserIdentityLabel {


    /**
     * id : 12
     * identityId : 2
     * isCheck : 0
     * lableName : 法律支援
     */

    private String id;
    private int identityId;
    private String isCheck;
    private String lableName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdentityId() {
        return identityId;
    }

    public void setIdentityId(int identityId) {
        this.identityId = identityId;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }
}
