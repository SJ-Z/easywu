package com.cose.easywu.mapper;

import com.cose.easywu.po.Goods;
import com.cose.easywu.po.GoodsQueryPo;
import com.cose.easywu.po.UpdateLikeGoodsPo;

import java.util.List;

public interface GoodsMapper {

    void insertGoods(Goods goods);
    List<GoodsQueryPo> selectNewestGoods();
    List<GoodsQueryPo> selectGoodsLike(String u_id);
    void addLikeGoods(UpdateLikeGoodsPo updateLikeGoodsPo);
    void cancelLikeGoods(UpdateLikeGoodsPo updateLikeGoodsPo);
}