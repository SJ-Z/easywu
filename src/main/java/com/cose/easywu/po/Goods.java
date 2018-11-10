package com.cose.easywu.po;

import java.util.Date;

public class Goods {

    private String g_id;
    private String g_name;
    private String g_desc;
    private double g_price;
    private double g_originalPrice;
    private String g_pic1;
    private String g_pic2;
    private String g_pic3;
    private int g_state;
    private Date g_updateTime;
    private int g_like;
    private Type g_type;
    private User g_user;
    private User g_buyer;

    public Goods() {
    }

    public Goods(String g_id, String g_name, String g_desc, double g_price, double g_originalPrice) {
        this.g_id = g_id;
        this.g_name = g_name;
        this.g_desc = g_desc;
        this.g_price = g_price;
        this.g_originalPrice = g_originalPrice;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getG_desc() {
        return g_desc;
    }

    public void setG_desc(String g_desc) {
        this.g_desc = g_desc;
    }

    public double getG_price() {
        return g_price;
    }

    public void setG_price(double g_price) {
        this.g_price = g_price;
    }

    public double getG_originalPrice() {
        return g_originalPrice;
    }

    public void setG_originalPrice(double g_originalPrice) {
        this.g_originalPrice = g_originalPrice;
    }

    public String getG_pic1() {
        return g_pic1;
    }

    public void setG_pic1(String g_pic1) {
        this.g_pic1 = g_pic1;
    }

    public String getG_pic2() {
        return g_pic2;
    }

    public void setG_pic2(String g_pic2) {
        this.g_pic2 = g_pic2;
    }

    public String getG_pic3() {
        return g_pic3;
    }

    public void setG_pic3(String g_pic3) {
        this.g_pic3 = g_pic3;
    }

    public int getG_state() {
        return g_state;
    }

    public void setG_state(int g_state) {
        this.g_state = g_state;
    }

    public Date getG_updateTime() {
        return g_updateTime;
    }

    public void setG_updateTime(Date g_updateTime) {
        this.g_updateTime = g_updateTime;
    }

    public int getG_like() {
        return g_like;
    }

    public void setG_like(int g_like) {
        this.g_like = g_like;
    }

    public Type getG_type() {
        return g_type;
    }

    public void setG_type(Type g_type) {
        this.g_type = g_type;
    }

    public User getG_user() {
        return g_user;
    }

    public void setG_user(User g_user) {
        this.g_user = g_user;
    }

    public User getG_buyer() {
        return g_buyer;
    }

    public void setG_buyer(User g_buyer) {
        this.g_buyer = g_buyer;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "g_id='" + g_id + '\'' +
                ", g_name='" + g_name + '\'' +
                ", g_desc='" + g_desc + '\'' +
                ", g_price=" + g_price +
                ", g_originalPrice=" + g_originalPrice +
                ", g_pic1='" + g_pic1 + '\'' +
                ", g_pic2='" + g_pic2 + '\'' +
                ", g_pic3='" + g_pic3 + '\'' +
                ", g_state=" + g_state +
                ", g_updateTime=" + g_updateTime +
                ", g_like=" + g_like +
                ", g_type=" + g_type +
                ", g_user=" + g_user +
                ", g_buyer=" + g_buyer +
                '}';
    }
}
