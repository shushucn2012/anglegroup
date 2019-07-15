package com.asuper.angelgroup.moduel.home.bean;

import java.util.List;

/**
 * Created by shubei on 2017/12/6.
 */

public class NewMsgBean {


    /**
     * count : 36
     * messages : [{"senderPicture":"http://park61.oss-cn-zhangjiakou.aliyuncs.com/grow/A6146E2D-97B8-49AE-9547-FA64681B8A78.jpg"},{"senderPicture":"http://park61.oss-cn-zhangjiakou.aliyuncs.com/grow/A6146E2D-97B8-49AE-9547-FA64681B8A78.jpg"}]
     * time : 1512492636344
     */

    private int count;
    private long time;
    private List<MessagesBean> messages;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<MessagesBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesBean> messages) {
        this.messages = messages;
    }

    public static class MessagesBean {
        /**
         * senderPicture : http://park61.oss-cn-zhangjiakou.aliyuncs.com/grow/A6146E2D-97B8-49AE-9547-FA64681B8A78.jpg
         */

        private String senderPicture;

        public String getSenderPicture() {
            return senderPicture;
        }

        public void setSenderPicture(String senderPicture) {
            this.senderPicture = senderPicture;
        }
    }
}
