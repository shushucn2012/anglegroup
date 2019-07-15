package com.asuper.angelgroup.widget.videorecorder;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/14.
 */

public class VedioBaseBean implements Serializable{
    private String url;
    private boolean isvideo;
    private String videosize;
    private String filesize;
    private boolean isCheckBox=false;

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        isCheckBox = checkBox;
    }

    public String getVideosize() {
        return videosize;
    }

    public void setVideosize(String videosize) {
        this.videosize = videosize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isvideo() {
        return isvideo;
    }

    public void setIsvideo(boolean isvideo) {
        this.isvideo = isvideo;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }
}
