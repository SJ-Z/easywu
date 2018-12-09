package com.cose.easywu.po;

import java.util.Date;

public class ReplyDetailBean {

    private int id;
    private String nickName;
    private String userPhoto;
    private String content;
    private Date createTime;

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ReplyDetailBean{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }

}
