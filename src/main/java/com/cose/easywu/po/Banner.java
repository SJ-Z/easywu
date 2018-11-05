package com.cose.easywu.po;

public class Banner {

    private String ban_id;
    private String ban_img;
    private int ban_index;

    public String getBan_id() {
        return ban_id;
    }

    public void setBan_id(String ban_id) {
        this.ban_id = ban_id;
    }

    public String getBan_img() {
        return ban_img;
    }

    public void setBan_img(String ban_img) {
        this.ban_img = ban_img;
    }

    public int getBan_index() {
        return ban_index;
    }

    public void setBan_index(int ban_index) {
        this.ban_index = ban_index;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "ban_id='" + ban_id + '\'' +
                ", ban_img='" + ban_img + '\'' +
                ", ban_index=" + ban_index +
                '}';
    }
}
