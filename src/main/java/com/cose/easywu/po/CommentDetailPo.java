package com.cose.easywu.po;

public class CommentDetailPo {

    private CommentDetailBean commentDetailBean;
    private int gc_id;

    public CommentDetailPo(CommentDetailBean commentDetailBean, int gc_id) {
        this.commentDetailBean = commentDetailBean;
        this.gc_id = gc_id;
    }

    public CommentDetailBean getCommentDetailBean() {
        return commentDetailBean;
    }

    public void setCommentDetailBean(CommentDetailBean commentDetailBean) {
        this.commentDetailBean = commentDetailBean;
    }

    public int getGc_id() {
        return gc_id;
    }

    public void setGc_id(int gc_id) {
        this.gc_id = gc_id;
    }
}
