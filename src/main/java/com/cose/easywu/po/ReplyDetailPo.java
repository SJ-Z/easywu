package com.cose.easywu.po;

public class ReplyDetailPo {

    private ReplyDetailBean replyDetailBean;
    private int comment_id;

    public ReplyDetailPo() {
    }

    public ReplyDetailPo(ReplyDetailBean replyDetailBean, int comment_id) {
        this.replyDetailBean = replyDetailBean;
        this.comment_id = comment_id;
    }

    public ReplyDetailBean getReplyDetailBean() {
        return replyDetailBean;
    }

    public void setReplyDetailBean(ReplyDetailBean replyDetailBean) {
        this.replyDetailBean = replyDetailBean;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }
}
