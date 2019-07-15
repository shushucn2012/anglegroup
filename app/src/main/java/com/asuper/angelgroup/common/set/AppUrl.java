package com.asuper.angelgroup.common.set;

/**
 * APP通信地址
 *
 * @author super
 */
public class AppUrl {
    /**
     * app通信访问主地址
     */
    public static String host = "";

    public static String releaseHost = "";

    /**
     * app通信访问测试地址
     */
//    public static final String demoHost = "http://101.201.155.117:8677";//测试服务器
    public static final String demoHost = "http://118.31.228.95:8301";

    //public static final String demoHost = "http://www.cs.waibaodashi.com";
    public static final String demoHost_share = "http://www.waibaodashi.com";

    public static final String SHARE_APP_ICON = "http://a3.qpic.cn/psb?/4b8a92ff-3677-40b5-80ba-465cc9ff57bc/CBXG4gVqXhGaKiqYHMD2RkGgLBRTAWX4mXfR6eTYvFQ!/b/dIUBAAAAAAAA&bo=rACsAAAAAAADByI!&rf=viewer_4";

    /**
     * 请求阿里云oss加密后的url
     */
    public static final String GET_ALIYUN_SEC_URL = "/service/oss/upload";

    public static final String login = "/service/login/login";

    public static final String register = "/service/login/register";

    public static final String contentUserInfo = "/service/home/contentUserInfo";

    public static final String getUser = "/service/user/getUser";

    public static final String updateBaseInfo = "/service/login/updateBaseInfo";

    public static final String myFriendsCircle = "/service/home/myFriendsCircle";

    public static final String releaseBlog = "/service/home/releaseBlog";

    public static final String addComment = "/service/home/addComment";

    public static final String commentList = "/service/home/commentList";

    public static final String addPraise = "/service/home/addPraise";

    public static final String delPraise = "/service/home/delPraise";

    public static final String getIdentityLabels = "/service/identity_child/getIdentityLabels";

    public static final String addIdentityLabels = "/service/identity_child/addIdentityLabels";

    public static final String getCitysByProvince = "/service/city/getCitysByProvince";

    public static final String getMyFirends = "/service/identity_child/getMyFirends";

    public static final String searchUserInfo = "/service/user/searchUserInfo";

    public static final String getOther = "/service/user/getOther";

    public static final String sendAddFriendApply = "/service/identity_child/sendAddFriendApply";

    public static final String getSendFriendApplyRecords = "/service/identity_child/getSendFriendApplyRecords";

    public static final String agreeAddFriendApply = "/service/identity_child/agreeAddFriendApply";

    public static final String myFriendsContent = "/service/home/myFriendsContent";

    public static final String deleteMyFriend = "/service/identity_child/deleteMyFriend";

    public static final String sendFocusApply = "/service/identity_child/sendFocusApply";

    public static final String refuseFocusApply = "/service/identity_child/refuseFocusApply";

    public static final String getMyFocuUsers = "/service/identity_child/getMyFocuUsers";

    public static final String deleteItem = "/service/home/deleteItem";

    public static final String getMessageByUser = "/service/message/getMessageByUser";

    public static final String retransmissionBlog = "/service/home/retransmissionBlog";

    public static final String getMessageCount = "/service/message/getMessageCount";

    /**
     * 获取上传授权信息
     */
    public static final String GET_ALIYUN_AUTH = "/service/home/getAliyunAuth";

    /**
     * 发布视频
     */
    public static final String RELEASE_VIDEO = "/service/home/releaseVideo";

    public static final String getPlayAuth = "/service/home/getPlayAuth";

    public static final String retransmissionVideo = "/service/home/retransmissionVideo";
}
