package com.cose.easywu.mapper;

import com.cose.easywu.po.Goods;
import com.cose.easywu.po.GoodsQueryPo;
import com.cose.easywu.po.UpdateGoodsPo;

import java.util.List;

public interface GoodsMapper {

    void insertGoods(Goods goods);
    void updateGoods(Goods goods);
    List<GoodsQueryPo> selectNewestGoods();
    List<GoodsQueryPo> selectGoodsLike(String u_id);
    List<GoodsQueryPo> selectReleaseGoodsList(String u_id);
    void insertLikeGoods(UpdateGoodsPo updateGoodsPo);
    void deleteLikeGoods(UpdateGoodsPo updateGoodsPo);
    void updateGoodsLike(UpdateGoodsPo updateGoodsPo);
    int updateGoodsUpdateTime(UpdateGoodsPo updateGoodsPo);
    int updateGoodsState(UpdateGoodsPo updateGoodsPo);
}
