package com.asuper.angelgroup.moduel.book.bean;

import java.io.Serializable;

/**
 * Created by shubei on 2017/7/31.
 */

public class AddRequestBean implements Serializable {


    /**
     * createBy : 31562
     * createDate : 1512123515000
     * curPage : 1
     * end : 5
     * friendId : 31563
     * id : 22
     * nextPage : 0
     * pageSize : 5
     * pageTotal : 0
     * petName : 测试2231
     * pictureUrl : http://park61.oss-cn-zhangjiakou.aliyuncs.com/client/25549e6e-0192-424e-99dc-91b6e8ef900b.jpg
     * previousPage : 1
     * remark :
     * start : 0
     * status : 等待同意
     * total : 0
     * userId : 31563
     */

    private String createBy;
    private long createDate;
    private int curPage;
    private int end;
    private int friendId;
    private String id;
    private int nextPage;
    private int pageSize;
    private int pageTotal;
    private String petName;
    private String pictureUrl;
    private int previousPage;
    private String remark;
    private int start;
    private String status;
    private int total;
    private int userId;
    private String resume;
    private int type; //0:申请方;1:接受方
    private int focusUserId;
    private String childAge;
    private String childMalady;
    private String childName;
    private String cityName;
    private String relationName;
    private String positionalTitle;

    public String getChildAge() {
        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge;
    }

    public String getChildMalady() {
        return childMalady;
    }

    public void setChildMalady(String childMalady) {
        this.childMalady = childMalady;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFocusUserId() {
        return focusUserId;
    }

    public void setFocusUserId(int focusUserId) {
        this.focusUserId = focusUserId;
    }

    public String getPositionalTitle() {
        return positionalTitle;
    }

    public void setPositionalTitle(String positionalTitle) {
        this.positionalTitle = positionalTitle;
    }
}
