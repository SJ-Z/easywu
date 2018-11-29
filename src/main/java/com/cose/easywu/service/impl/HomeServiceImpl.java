package com.cose.easywu.service.impl;

import com.cose.easywu.mapper.GoodsMapper;
import com.cose.easywu.mapper.HomeMapper;
import com.cose.easywu.po.*;
import com.cose.easywu.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public HomeData getHomeData(String u_id) {
        List<Banner> bannerList = homeMapper.selectAllBanner();
        List<Type> typeList = homeMapper.selectAllType();
        List<GoodsQueryPo> goodsQueryPoList = goodsMapper.selectNewestGoods();
        List<GoodsQueryPo> goodsLikeList = goodsMapper.selectGoodsLike(u_id);
        List<GoodsQueryPo> releaseGoodsList = goodsMapper.selectReleaseGoodsList(u_id);
        return new HomeData(bannerList, typeList, goodsQueryPoList, goodsLikeList, releaseGoodsList);
    }

}
