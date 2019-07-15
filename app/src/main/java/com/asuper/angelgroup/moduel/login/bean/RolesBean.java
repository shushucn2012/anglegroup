package com.asuper.angelgroup.moduel.login.bean;

import java.util.List;

/**
 * Created by shubei on 2017/11/27.
 */

public class RolesBean  {


    /**
     * createBy : admin
     * createDate : 1510414213000
     * createId : 1
     * id : 1
     * identityCode : 001
     * identityName : 父母家人
     * labels : [{"createBy":"admin","createDate":1510414270000,"id":"1","identityId":1,"isCheck":"0","lableName":"自闭症","updateBy":"星宝"},{"createBy":"admin","createDate":1510414279000,"id":"4","identityId":1,"isCheck":"0","lableName":"发育迟缓","updateBy":"发育迟缓"},{"createBy":"admin","createDate":1510414270000,"id":"1","identityId":1,"isCheck":"0","lableName":"自闭症","updateBy":"星宝"},{"createBy":"admin","createDate":1510414289000,"id":"8","identityId":2,"isCheck":"0","lableName":"教育"},{"createBy":"admin","createDate":1510414270000,"id":"1","identityId":1,"isCheck":"0","lableName":"自闭症","updateBy":"星宝"},{"createBy":"admin","createDate":1510414287000,"id":"7","identityId":2,"isCheck":"0","lableName":"康复"},{"createBy":"admin","createDate":1510414273000,"id":"2","identityId":1,"isCheck":"0","lableName":"唐氏综合症","updateBy":"唐宝"},{"createBy":"admin","createDate":1510414276000,"id":"3","identityId":1,"isCheck":"0","lableName":"脑瘫","updateBy":"脑瘫"},{"createBy":"admin","createDate":1510414282000,"id":"5","identityId":1,"isCheck":"0","lableName":"综合","updateBy":"综合"},{"createBy":"admin","createDate":1510414284000,"id":"6","identityId":1,"isCheck":"0","lableName":"其他","updateBy":"其他"},{"createBy":"admin","createDate":1510414291000,"id":"9","identityId":2,"isCheck":"0","lableName":"公益"},{"createBy":"admin","createDate":1510414294000,"id":"10","identityId":2,"isCheck":"0","lableName":"心理支援"},{"createBy":"admin","createDate":1510414295000,"id":"11","identityId":2,"isCheck":"0","lableName":"其他"},{"createBy":"admin","createDate":1510414297000,"id":"12","identityId":2,"isCheck":"0","lableName":"法律支援"}]
     */

    private String createBy;
    private long createDate;
    private String createId;
    private String id;
    private String identityCode;
    private String identityName;
    private List<LabelsBean> labels;

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

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public List<LabelsBean> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelsBean> labels) {
        this.labels = labels;
    }

    public static class LabelsBean {
        /**
         * createBy : admin
         * createDate : 1510414270000
         * id : 1
         * identityId : 1
         * isCheck : 0
         * lableName : 自闭症
         * updateBy : 星宝
         */

        private String createBy;
        private long createDate;
        private String id;
        private int identityId;
        private String isCheck;
        private String lableName;
        private String updateBy;

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

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }
    }
}
