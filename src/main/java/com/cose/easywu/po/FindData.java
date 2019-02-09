package com.cose.easywu.po;

import java.util.List;

public class FindData {

    private List<FindType> findTypeList;
    private List<FindGoodsQueryPo> findNewestInfoList;
    private List<FindGoodsQueryPo> releaseFindGoodsList;
    private List<FindGoodsQueryPo> likeFindGoodsList;

    public FindData() {
    }

    public FindData(List<FindType> findTypeList, List<FindGoodsQueryPo> findNewestInfoList, List<FindGoodsQueryPo> releaseFindGoodsList, List<FindGoodsQueryPo> likeFindGoodsList) {
        this.findTypeList = findTypeList;
        this.findNewestInfoList = findNewestInfoList;
        this.releaseFindGoodsList = releaseFindGoodsList;
        this.likeFindGoodsList = likeFindGoodsList;
    }

    public List<FindType> getFindTypeList() {
        return findTypeList;
    }

    public void setFindTypeList(List<FindType> findTypeList) {
        this.findTypeList = findTypeList;
    }

    public List<FindGoodsQueryPo> getFindNewestInfoList() {
        return findNewestInfoList;
    }

    public void setFindNewestInfoList(List<FindGoodsQueryPo> findNewestInfoList) {
        this.findNewestInfoList = findNewestInfoList;
    }

    public List<FindGoodsQueryPo> getReleaseFindGoodsList() {
        return releaseFindGoodsList;
    }

    public void setReleaseFindGoodsList(List<FindGoodsQueryPo> releaseFindGoodsList) {
        this.releaseFindGoodsList = releaseFindGoodsList;
    }

    public List<FindGoodsQueryPo> getLikeFindGoodsList() {
        return likeFindGoodsList;
    }

    public void setLikeFindGoodsList(List<FindGoodsQueryPo> likeFindGoodsList) {
        this.likeFindGoodsList = likeFindGoodsList;
    }

    @Override
    public String toString() {
        return "FindData{" +
                "findTypeList=" + findTypeList +
                ", findNewestInfoList=" + findNewestInfoList +
                ", releaseFindGoodsList=" + releaseFindGoodsList +
                ", likeFindGoodsList=" + likeFindGoodsList +
                '}';
    }
}
