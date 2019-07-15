package com.asuper.angelgroup.moduel.login.bean;


import java.io.Serializable;

/**
 * Created by shubei on 2017/6/24.
 */

public class UserBean implements Serializable {


    /**
     * accountStatus : 1
     * cityId : 187
     * cityName : 郑州市
     * expert : false
     * id : 31562
     * inviteCode : 267742
     * inviter : 31536
     * isVerifyMobile : 1
     * loginName : 18062512222
     * loveShopId : 267736
     * mobile : 18062512222
     * name : 测试账户2222
     * outHave : 0
     * petName : 测试账户2222
     * pictureUrl : http://park61.oss-cn-zhangjiakou.aliyuncs.com/client/d6f316fe-5486-443a-9684-49aea2badb28.jpg
     * rechargeTypeCode : 001
     * userChildVO : {"age":"1岁1个月","birthday":1477929600000,"hasApplyVote":false,"hasEaResult":false,"id":153,"name":"孩子2222","petName":"孩子2222","pictureUrl":"http://park61.oss-cn-zhangjiakou.aliyuncs.com/client/d6f316fe-5486-443a-9684-49aea2badb28.jpg","sex":0}
     * userMobile : 18062512222
     * userRelationConstantVO : {"id":"2","relationName":"爸爸"}
     */

    private int accountStatus;
    private int cityId;
    private String cityName;
    private boolean expert;
    private int id;
    private String inviteCode;
    private int inviter;
    private int isVerifyMobile;
    private String loginName;
    private int loveShopId;
    private String mobile;
    private String name;
    private int outHave;
    private String petName;
    private String pictureUrl;
    private String rechargeTypeCode; //001 父母；002 人士
    private UserChildVOBean userChildVO;
    private String userMobile;
    private int sex;//1男2女
    private String resume;//个性签名
    private String positionalTitle; //角色
    private UserRelationConstantVOBean userRelationConstantVO;
    private UserIdentityLabel userIdentityLabel;
    private boolean foucs;
    private boolean friend;

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public boolean isExpert() {
        return expert;
    }

    public void setExpert(boolean expert) {
        this.expert = expert;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public int getInviter() {
        return inviter;
    }

    public void setInviter(int inviter) {
        this.inviter = inviter;
    }

    public int getIsVerifyMobile() {
        return isVerifyMobile;
    }

    public void setIsVerifyMobile(int isVerifyMobile) {
        this.isVerifyMobile = isVerifyMobile;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public int getLoveShopId() {
        return loveShopId;
    }

    public void setLoveShopId(int loveShopId) {
        this.loveShopId = loveShopId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOutHave() {
        return outHave;
    }

    public void setOutHave(int outHave) {
        this.outHave = outHave;
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

    public String getRechargeTypeCode() {
        return rechargeTypeCode;
    }

    public void setRechargeTypeCode(String rechargeTypeCode) {
        this.rechargeTypeCode = rechargeTypeCode;
    }

    public UserChildVOBean getUserChildVO() {
        return userChildVO;
    }

    public void setUserChildVO(UserChildVOBean userChildVO) {
        this.userChildVO = userChildVO;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public UserRelationConstantVOBean getUserRelationConstantVO() {
        return userRelationConstantVO;
    }

    public void setUserRelationConstantVO(UserRelationConstantVOBean userRelationConstantVO) {
        this.userRelationConstantVO = userRelationConstantVO;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPositionalTitle() {
        return positionalTitle;
    }

    public void setPositionalTitle(String positionalTitle) {
        this.positionalTitle = positionalTitle;
    }

    public UserIdentityLabel getUserIdentityLabel() {
        return userIdentityLabel;
    }

    public void setUserIdentityLabel(UserIdentityLabel userIdentityLabel) {
        this.userIdentityLabel = userIdentityLabel;
    }

    public boolean isFoucs() {
        return foucs;
    }

    public void setFoucs(boolean foucs) {
        this.foucs = foucs;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public static class UserChildVOBean {
        /**
         * age : 1岁1个月
         * birthday : 1477929600000
         * hasApplyVote : false
         * hasEaResult : false
         * id : 153
         * name : 孩子2222
         * petName : 孩子2222
         * pictureUrl : http://park61.oss-cn-zhangjiakou.aliyuncs.com/client/d6f316fe-5486-443a-9684-49aea2badb28.jpg
         * sex : 0
         */

        private String age;
        private long birthday;
        private boolean hasApplyVote;
        private boolean hasEaResult;
        private int id;
        private String name;
        private String petName;
        private String pictureUrl;
        private int sex;

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public boolean isHasApplyVote() {
            return hasApplyVote;
        }

        public void setHasApplyVote(boolean hasApplyVote) {
            this.hasApplyVote = hasApplyVote;
        }

        public boolean isHasEaResult() {
            return hasEaResult;
        }

        public void setHasEaResult(boolean hasEaResult) {
            this.hasEaResult = hasEaResult;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }

    public static class UserRelationConstantVOBean {
        /**
         * id : 2
         * relationName : 爸爸
         */

        private String id;
        private String relationName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRelationName() {
            return relationName;
        }

        public void setRelationName(String relationName) {
            this.relationName = relationName;
        }
    }
}
