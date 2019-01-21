package com.cose.easywu.po;

import java.util.Date;

public class ReplyDetailPo {

    private int id;
    private String u_id;
    private String reply;
    private String origin_uid;
    private int comment_id;
    private Date createTime;

    public ReplyDetailPo() {
    }

    public ReplyDetailPo(String u_id, String reply, String origin_uid, int comment_id, Date createTime) {
        this.u_id = u_id;
        this.reply = reply;
        this.origin_uid = origin_uid;
        this.comment_id = comment_id;
        this.createTime = createTime;
    }

    public String getOrigin_uid() {
        return origin_uid;
    }

    public void setOrigin_uid(String origin_uid) {
        this.origin_uid = origin_uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
