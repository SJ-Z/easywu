package com.cose.easywu.po;

import java.util.List;

public class HomeData {

    private List<Banner> banner_info;
    private List<Type> type_info;
    private List<GoodsQueryPo> newest_info;
    private List<GoodsQueryPo> goodsLikeList;

    public HomeData() {
    }

    public HomeData(List<Banner> banner_info, List<Type> type_info, List<GoodsQueryPo> newest_info, List<GoodsQueryPo> goodsLikeList) {
        this.banner_info = banner_info;
        this.type_info = type_info;
        this.newest_info = newest_info;
        this.goodsLikeList = goodsLikeList;
    }

    public List<Banner> getBanner_info() {
        return banner_info;
    }

    public void setBanner_info(List<Banner> banner_info) {
        this.banner_info = banner_info;
    }

    public List<Type> getType_info() {
        return type_info;
    }

    public void setType_info(List<Type> type_info) {
        this.type_info = type_info;
    }

    public List<GoodsQueryPo> getNewest_info() {
        return newest_info;
    }

    public void setNewest_info(List<GoodsQueryPo> newest_info) {
        this.newest_info = newest_info;
    }

    public List<GoodsQueryPo> getGoodsLikeList() {
        return goodsLikeList;
    }

    public void setGoodsLikeList(List<GoodsQueryPo> goodsLikeList) {
        this.goodsLikeList = goodsLikeList;
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "banner_info=" + banner_info +
                ", type_info=" + type_info +
                ", newest_info=" + newest_info +
                ", goodsLikeList=" + goodsLikeList +
                '}';
    }
}
