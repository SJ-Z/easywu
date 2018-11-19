package com.cose.easywu.mapper;

import com.cose.easywu.po.Banner;
import com.cose.easywu.po.Goods;
import com.cose.easywu.po.GoodsQueryPo;
import com.cose.easywu.po.Type;

import java.util.List;

public interface HomeMapper {

    List<Banner> selectAllBanner();
    List<Type> selectAllType();
    void insertGoods(Goods goods);
    List<GoodsQueryPo> selectNewestGoods();
}
