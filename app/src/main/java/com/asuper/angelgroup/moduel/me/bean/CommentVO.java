package com.asuper.angelgroup.moduel.me.bean;

/**
 * Created by shubei on 2017/12/10.
 */

public class CommentVO {


    /**
     * content : 真的不不不困咯
     * createDate : 1512894117000
     * currDate : 1512894533000
     * id : 343
     * isReply : false
     * itemId : 450
     * mobile : 18062512225
     * requirementId : 450
     * userId : 31573
     * userName : 开空调
     * userUrl : http://park61.oss-cn-zhangjiakou.aliyuncs.com/client/e17b9a8d-09e2-4f09-bea9-9220a4dcda65.jpg
     */

    private String content;
    private long createDate;
    private long currDate;
    private int id;
    private boolean isReply;
    private int itemId;
    private String mobile;
    private int requirementId;
    private int userId;
    private String userName;
    private String userUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getCurrDate() {
        return currDate;
    }

    public void setCurrDate(long currDate) {
        this.currDate = currDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIsReply() {
        return isReply;
    }

    public void setIsReply(boolean isReply) {
        this.isReply = isReply;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(int requirementId) {
        this.requirementId = requirementId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }
}
