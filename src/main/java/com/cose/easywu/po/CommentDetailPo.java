package com.cose.easywu.po;

import java.util.Date;

public class CommentDetailPo {

    private int id;
    private String content;
    private int gc_id;
    private String g_id;
    private String u_id;
    private Date createTime;

    public CommentDetailPo() {
    }

    public CommentDetailPo(String content, int gc_id, String g_id, String u_id, Date createTime) {
        this.content = content;
        this.gc_id = gc_id;
        this.g_id = g_id;
        this.u_id = u_id;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGc_id() {
        return gc_id;
    }

    public void setGc_id(int gc_id) {
        this.gc_id = gc_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }
}
