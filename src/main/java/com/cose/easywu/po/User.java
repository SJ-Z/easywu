package com.cose.easywu.po;

public class User {
    private String u_id;
    private String u_email;
    private String u_nick;
    private String u_pwd;
    private String u_photo;
    private int u_state; // 用户账号状态：0未激活，1已激活，2被停用，3已冻结
    private String u_code; // 用户账号激活码

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_nick() {
        return u_nick;
    }

    public void setU_nick(String u_nick) {
        this.u_nick = u_nick;
    }

    public String getU_pwd() {
        return u_pwd;
    }

    public void setU_pwd(String u_pwd) {
        this.u_pwd = u_pwd;
    }

    public String getU_photo() {
        return u_photo;
    }

    public void setU_photo(String u_photo) {
        this.u_photo = u_photo;
    }

    public int getU_state() {
        return u_state;
    }

    public void setU_state(int u_state) {
        this.u_state = u_state;
    }

    public String getU_code() {
        return u_code;
    }

    public void setU_code(String u_code) {
        this.u_code = u_code;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id='" + u_id + '\'' +
                ", u_email='" + u_email + '\'' +
                ", u_nick='" + u_nick + '\'' +
                ", u_pwd='" + u_pwd + '\'' +
                ", u_photo='" + u_photo + '\'' +
                ", u_state=" + u_state +
                ", u_code='" + u_code + '\'' +
                '}';
    }
}
