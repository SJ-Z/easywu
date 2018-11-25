package com.cose.easywu.po;

public class UpdateLikeGoodsPo {

    private String g_id;
    private String u_id;
    private int count;

    public UpdateLikeGoodsPo() {
    }

    public UpdateLikeGoodsPo(String g_id, String u_id, int count) {
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
}
