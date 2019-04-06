package com.cose.easywu.po;

import java.util.Date;

public class UpdateGoodsPo {

    private String g_id;
    private String u_id;
    private int count;
    private Date updateTime;
    private int state;

    public UpdateGoodsPo() {
    }

    public UpdateGoodsPo(String g_id) {
        this.g_id = g_id;
    }

    public UpdateGoodsPo(String g_id, String u_id) {
        this.g_id = g_id;
        this.u_id = u_id;
    }

    public UpdateGoodsPo(String g_id, String u_id, Date updateTime) {
        this.g_id = g_id;
        this.u_id = u_id;
        this.updateTime = updateTime;
    }

    public UpdateGoodsPo(String g_id, String u_id, int count) {
        this.g_id = g_id;
        this.u_id = u_id;
        this.count = count;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UpdateGoodsPo{" +
                "g_id='" + g_id + '\'' +
                ", u_id='" + u_id + '\'' +
                ", count=" + count +
                ", updateTime=" + updateTime +
                ", state=" + state +
                '}';
    }
}
