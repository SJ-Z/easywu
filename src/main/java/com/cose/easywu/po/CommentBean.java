package com.cose.easywu.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentBean {

    private int id;
    private String g_id;
    private List<CommentDetailBean> list;

    public CommentBean() {
    }

    public CommentBean(String g_id) {
        this.g_id = g_id;
    }

    public CommentBean(String g_id, CommentDetailBean commentDetailBean) {
        this.g_id = g_id;
        list = new ArrayList<>();
        list.add(commentDetailBean);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public List<CommentDetailBean> getList() {
        return list;
    }

    public void setList(List<CommentDetailBean> list) {
        this.list = list;
    }

    public void addToList(List<CommentDetailBean> newList) {
        HashMap<Integer, CommentDetailBean> map = new HashMap<>();
        for (CommentDetailBean commentDetailBean : list) {
            map.put(commentDetailBean.getId(), commentDetailBean);
        }
        for (CommentDetailBean commentDetailBean : newList) {
            if (!map.containsKey(commentDetailBean.getId())) {
                map.put(commentDetailBean.getId(), commentDetailBean);
            }
        }
        list.clear();
        list.addAll(map.values());
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "id=" + id +
                ", g_id='" + g_id + '\'' +
                ", list=" + list +
                '}';
    }

}
