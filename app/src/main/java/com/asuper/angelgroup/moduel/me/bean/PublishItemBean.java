package com.asuper.angelgroup.moduel.me.bean;

import java.util.List;

/**
 * Created by shubei on 2017/11/18.
 */

public class PublishItemBean {


    /**
     * authority : 3
     * authorityName : 日记
     * childAge : 9个月14天
     * childMalady : 发育迟缓
     * childName : 订单
     * cityName : 郑州市
     * classifyType : 0
     * composeType : 1
     * dateTag : 3小时之前
     * issuerHeadPic : http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124151545674_132.jpg
     * issuerName : 疯癫
     * itemCommentList : [{"content":"JJ哦YY图笨哦YY","createDate":1511694198000,"currDate":1511701343000,"id":166,"isReply":false,"itemId":123,"mobile":"13512341234","parentId":165,"parentMobile":"13512341234","parentUserName":"疯癫","requirementId":123,"userId":31537,"userName":"疯癫","userUrl":"http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124151545674_132.jpg"},{"content":"不错很叼呀","createDate":1511688002000,"currDate":1511701343000,"id":165,"isReply":false,"itemId":123,"mobile":"13512341234","requirementId":123,"userId":31537,"userName":"疯癫","userUrl":"http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124151545674_132.jpg"}]
     * itemCommentNum : 0
     * itemId : 123
     * itemMediaList : [{"mediaUrl":"http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124154854039_641.jpg?x-oss-process=style/logo_img"}]
     * itemReadNum : 0
     * praiseList : [{"pictureUrl":"http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124151545674_132.jpg","praiseTime":1511701337000,"requirementId":123,"userId":31537,"userName":"疯癫"}]
     * relationName : 妈
     * returnList : []
     * summray : 生活在这个城市中的人，在其或漫长或短暂的生命中，多少都受过他人的恶行相待。其中相当一部分恶行，仅能通过道德加以苛责。彼时彼地，法律显得既苍白又无力。我们也许会同情，会愤怒，但不会想到去	击杀那	些原本与我们无关的作恶者。别人的苦难，终究是别人的，我们的克制，多半源自于不曾感同身受。然而，一旦有人这么做了，我们的内心却难免会感到快慰。民众如是，警察亦如是
     * userInfo : {"expert":false,"focus":false,"photo":"http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124151545674_132.jpg","userId":31537,"userMobile":"13512341234","userName":"疯癫"}
     */

    private String authority;
    private String authorityName;
    private String childAge;
    private String childMalady;
    private String childName;
    private String cityName;
    private String classifyType; //类型  0:图文；1:视频;2:推广
    private String composeType;
    private String dateTag;
    private String issuerHeadPic;
    private String issuerName;
    private String itemCommentNum;
    private int itemId;
    private String itemReadNum;
    private String relationName;
    private String summray;
    private UserInfoBean userInfo;
    private List<ItemCommentListBean> itemCommentList;
    private List<ItemMediaListBean> itemMediaList;
    private List<PraiseListBean> praiseList;
    private List<ReturnBean> returnList;
    private String positionalTitle;
    private String returnId;
    private String returnSummary;
    private String returnUserId;
    private String returnUserName;
    private boolean isDongThingAreaShowing;

    /**
     * 是否是显示全部
     */
    private boolean isShowAll;
    /**
     * 文字是否超出范围
     */
    private Boolean hasEllipsis;

    /**
     * 是否是显示全部2
     */
    private boolean isShowAll2;
    /**
     * 文字是否超出范围2
     */
    private Boolean hasEllipsis2;

    public boolean isShowAll2() {
        return isShowAll2;
    }

    public void setShowAll2(boolean showAll2) {
        isShowAll2 = showAll2;
    }

    public Boolean getHasEllipsis2() {
        return hasEllipsis2;
    }

    public void setHasEllipsis2(Boolean hasEllipsis2) {
        this.hasEllipsis2 = hasEllipsis2;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getReturnSummary() {
        return returnSummary;
    }

    public void setReturnSummary(String returnSummary) {
        this.returnSummary = returnSummary;
    }

    public String getReturnUserId() {
        return returnUserId;
    }

    public void setReturnUserId(String returnUserId) {
        this.returnUserId = returnUserId;
    }

    public String getReturnUserName() {
        return returnUserName;
    }

    public void setReturnUserName(String returnUserName) {
        this.returnUserName = returnUserName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

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

    public String getClassifyType() {
        return classifyType;
    }

    public void setClassifyType(String classifyType) {
        this.classifyType = classifyType;
    }

    public String getComposeType() {
        return composeType;
    }

    public void setComposeType(String composeType) {
        this.composeType = composeType;
    }

    public String getDateTag() {
        return dateTag;
    }

    public void setDateTag(String dateTag) {
        this.dateTag = dateTag;
    }

    public String getIssuerHeadPic() {
        return issuerHeadPic;
    }

    public void setIssuerHeadPic(String issuerHeadPic) {
        this.issuerHeadPic = issuerHeadPic;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getItemCommentNum() {
        return itemCommentNum;
    }

    public void setItemCommentNum(String itemCommentNum) {
        this.itemCommentNum = itemCommentNum;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemReadNum() {
        return itemReadNum;
    }

    public void setItemReadNum(String itemReadNum) {
        this.itemReadNum = itemReadNum;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getSummray() {
        return summray;
    }

    public void setSummray(String summray) {
        this.summray = summray;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public List<ItemCommentListBean> getItemCommentList() {
        return itemCommentList;
    }

    public void setItemCommentList(List<ItemCommentListBean> itemCommentList) {
        this.itemCommentList = itemCommentList;
    }

    public List<ItemMediaListBean> getItemMediaList() {
        return itemMediaList;
    }

    public void setItemMediaList(List<ItemMediaListBean> itemMediaList) {
        this.itemMediaList = itemMediaList;
    }

    public List<PraiseListBean> getPraiseList() {
        return praiseList;
    }

    public void setPraiseList(List<PraiseListBean> praiseList) {
        this.praiseList = praiseList;
    }

    public List<ReturnBean> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<ReturnBean> returnList) {
        this.returnList = returnList;
    }

    public String getPositionalTitle() {
        return positionalTitle;
    }

    public void setPositionalTitle(String positionalTitle) {
        this.positionalTitle = positionalTitle;
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
    }

    public Boolean getHasEllipsis() {
        return hasEllipsis;
    }

    public void setHasEllipsis(Boolean hasEllipsis) {
        this.hasEllipsis = hasEllipsis;
    }

    public boolean isDongThingAreaShowing() {
        return isDongThingAreaShowing;
    }

    public void setDongThingAreaShowing(boolean dongThingAreaShowing) {
        isDongThingAreaShowing = dongThingAreaShowing;
    }

    public static class UserInfoBean {
        /**
         * expert : false
         * focus : false
         * photo : http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124151545674_132.jpg
         * userId : 31537
         * userMobile : 13512341234
         * userName : 疯癫
         */

        private boolean expert;
        private boolean focus;
        private String photo;
        private int userId;
        private String userMobile;
        private String userName;

        public boolean isExpert() {
            return expert;
        }

        public void setExpert(boolean expert) {
            this.expert = expert;
        }

        public boolean isFocus() {
            return focus;
        }

        public void setFocus(boolean focus) {
            this.focus = focus;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class ItemCommentListBean {
        /**
         * content : JJ哦YY图笨哦YY
         * createDate : 1511694198000
         * currDate : 1511701343000
         * id : 166
         * isReply : false
         * itemId : 123
         * mobile : 13512341234
         * parentId : 165
         * parentMobile : 13512341234
         * parentUserName : 疯癫
         * requirementId : 123
         * userId : 31537
         * userName : 疯癫
         * userUrl : http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124151545674_132.jpg
         */

        private String content;
        private long createDate;
        private long currDate;
        private int id;
        private boolean isReply;
        private int itemId;
        private String mobile;
        private int parentId;
        private String parentMobile;
        private String parentUserName;
        private int parentUserId;
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

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getParentMobile() {
            return parentMobile;
        }

        public void setParentMobile(String parentMobile) {
            this.parentMobile = parentMobile;
        }

        public String getParentUserName() {
            return parentUserName;
        }

        public void setParentUserName(String parentUserName) {
            this.parentUserName = parentUserName;
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

        public int getParentUserId() {
            return parentUserId;
        }

        public void setParentUserId(int parentUserId) {
            this.parentUserId = parentUserId;
        }
    }

    public static class ItemMediaListBean {
        /**
         * mediaUrl : http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124154854039_641.jpg?x-oss-process=style/logo_img
         */
        private String mediaId;
        private String mediaUrl;

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
        }

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }
    }

    public static class PraiseListBean {
        /**
         * pictureUrl : http://park61.oss-cn-zhangjiakou.aliyuncs.com/test/20171124151545674_132.jpg
         * praiseTime : 1511701337000
         * requirementId : 123
         * userId : 31537
         * userName : 疯癫
         */

        private String pictureUrl;
        private long praiseTime;
        private int requirementId;
        private int userId;
        private String userName;

        public PraiseListBean() {
        }

        public PraiseListBean(int userId, String userName) {
            this.userId = userId;
            this.userName = userName;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public long getPraiseTime() {
            return praiseTime;
        }

        public void setPraiseTime(long praiseTime) {
            this.praiseTime = praiseTime;
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
    }
}
