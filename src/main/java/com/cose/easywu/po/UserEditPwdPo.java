package com.cose.easywu.po;

public class UserEditPwdPo {

    private String u_email;
    private String u_oldpwd;
    private String u_newpwd;

    public UserEditPwdPo() {
    }

    public UserEditPwdPo(String u_email, String u_oldpwd, String u_newpwd) {
        this.u_email = u_email;
        this.u_oldpwd = u_oldpwd;
        this.u_newpwd = u_newpwd;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_oldpwd() {
        return u_oldpwd;
    }

    public void setU_oldpwd(String u_oldpwd) {
        this.u_oldpwd = u_oldpwd;
    }

    public String getU_newpwd() {
        return u_newpwd;
    }

    public void setU_newpwd(String u_newpwd) {
        this.u_newpwd = u_newpwd;
    }

    @Override
    public String toString() {
        return "UserEditPwdPo{" +
                "u_email='" + u_email + '\'' +
                ", u_oldpwd='" + u_oldpwd + '\'' +
                ", u_newpwd='" + u_newpwd + '\'' +
                '}';
    }
}
