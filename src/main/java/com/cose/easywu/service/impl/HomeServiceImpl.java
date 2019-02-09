package com.cose.easywu.service.impl;

import com.cose.easywu.mapper.GoodsMapper;
import com.cose.easywu.mapper.HomeMapper;
import com.cose.easywu.po.*;
import com.cose.easywu.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public HomeData getHomeData(String u_id) {
        List<Banner> bannerList = homeMapper.selectAllBanner();
        List<Type> typeList = homeMapper.selectAllType();

        Page page = new Page(5, 0);
        Map<String, Integer> pageMap = new HashMap<>();
        pageMap.put("pageSize", page.getPageSize());
        pageMap.put("startPos", page.getPageSize() * page.getPageCode());
        List<GoodsQueryPo> goodsQueryPoList = goodsMapper.selectNewestGoods(pageMap);

        List<GoodsQueryPo> goodsLikeList = goodsMapper.selectGoodsLike(u_id);
        List<GoodsQueryPo> releaseGoodsList = goodsMapper.selectReleaseGoodsList(u_id);
        List<GoodsQueryPo> buyGoodsList = goodsMapper.selectBuyGoodsList(u_id);
        return new HomeData(bannerList, typeList, goodsQueryPoList, goodsLikeList, releaseGoodsList, buyGoodsList);
    }

    @Override
    public FindData getFindHomeData(String u_id) {
        List<FindType> findTypeList = homeMapper.selectAllFindType();

        Page page = new Page(5, 0);
        Map<String, Integer> pageMap = new HashMap<>();
        pageMap.put("pageSize", page.getPageSize());
        pageMap.put("startPos", page.getPageSize() * page.getPageCode());
        List<FindGoodsQueryPo> findGoodsQueryPoList = goodsMapper.selectNewestFindGoods(pageMap);
        List<FindGoodsQueryPo> releaseFindGoodsList = goodsMapper.selectReleaseFindGoodsList(u_id);
        List<FindGoodsQueryPo> likeFindGoodsList = goodsMapper.selectLikeFindGoodsList(u_id);
        return new FindData(findTypeList, findGoodsQueryPoList, releaseFindGoodsList, likeFindGoodsList);
    }

}
