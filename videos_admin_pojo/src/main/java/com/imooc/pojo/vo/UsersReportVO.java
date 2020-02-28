package com.imooc.pojo.vo;

import java.util.Date;

public class UsersReportVO {

    private String id;

    private String title;

    private String content;

    private String dealUsername;

    private String dealVideoId;

    private String videoPath;

    private Integer status;

    private String submitUsername;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDealVideoId() {
        return dealVideoId;
    }

    public void setDealVideoId(String dealVideoId) {
        this.dealVideoId = dealVideoId == null ? null : dealVideoId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDealUsername() {
        return dealUsername;
    }

    public void setDealUsername(String dealUsername) {
        this.dealUsername = dealUsername;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSubmitUsername() {
        return submitUsername;
    }

    public void setSubmitUsername(String submitUsername) {
        this.submitUsername = submitUsername;
    }
}