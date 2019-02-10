package com.cose.easywu.po;

import java.util.List;

public class FindData {

    private List<FindType> findTypeList;
    private List<FindGoodsQueryPo> newestFindPeopleList;
    private List<FindGoodsQueryPo> newestFindGoodsList;
    private List<FindGoodsQueryPo> releaseFindGoodsList;
    private List<FindGoodsQueryPo> likeFindGoodsList;

    public FindData() {
    }

    public FindData(List<FindType> findTypeList, List<FindGoodsQueryPo> newestFindPeopleList, List<FindGoodsQueryPo> newestFindGoodsList, List<FindGoodsQueryPo> releaseFindGoodsList, List<FindGoodsQueryPo> likeFindGoodsList) {
        this.findTypeList = findTypeList;
        this.newestFindPeopleList = newestFindPeopleList;
        this.newestFindGoodsList = newestFindGoodsList;
        this.releaseFindGoodsList = releaseFindGoodsList;
        this.likeFindGoodsList = likeFindGoodsList;
    }

    public List<FindType> getFindTypeList() {
        return findTypeList;
    }

    public void setFindTypeList(List<FindType> findTypeList) {
        this.findTypeList = findTypeList;
    }

    public List<FindGoodsQueryPo> getNewestFindPeopleList() {
        return newestFindPeopleList;
    }

    public void setNewestFindPeopleList(List<FindGoodsQueryPo> newestFindPeopleList) {
        this.newestFindPeopleList = newestFindPeopleList;
    }

    public List<FindGoodsQueryPo> getNewestFindGoodsList() {
        return newestFindGoodsList;
    }

    public void setNewestFindGoodsList(List<FindGoodsQueryPo> newestFindGoodsList) {
        this.newestFindGoodsList = newestFindGoodsList;
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
                ", newestFindPeopleList=" + newestFindPeopleList +
                ", newestFindGoodsList=" + newestFindGoodsList +
                ", releaseFindGoodsList=" + releaseFindGoodsList +
                ", likeFindGoodsList=" + likeFindGoodsList +
                '}';
    }
}
