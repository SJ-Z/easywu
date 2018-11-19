package com.cose.easywu.service.impl;

import com.cose.easywu.mapper.HomeMapper;
import com.cose.easywu.po.*;
import com.cose.easywu.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;

    @Override
    public HomeData getHomeData() {
        List<Banner> bannerList = homeMapper.selectAllBanner();
        List<Type> typeList = homeMapper.selectAllType();
        List<GoodsQueryPo> goodsQueryPoList = homeMapper.selectNewestGoods();
        return new HomeData(bannerList, typeList, goodsQueryPoList);
    }

    @Override
    public void release(String g_id, String g_name, String g_desc, double g_price, double g_originalPrice,
                        List<String> filenames, String g_t_id, String g_u_id) {
        Goods goods = new Goods(g_id, g_name, g_desc, g_price, g_originalPrice);
        int picLen = filenames.size();
        if (picLen == 1) {
            goods.setG_pic1(filenames.get(0));
        } else if (picLen == 2) {
            goods.setG_pic1(filenames.get(0));
            goods.setG_pic2(filenames.get(1));
        } else if (picLen == 3) {
            goods.setG_pic1(filenames.get(0));
            goods.setG_pic2(filenames.get(1));
            goods.setG_pic3(filenames.get(2));
        }

        Type type = new Type();
        type.setT_id(g_t_id);
        goods.setG_type(type);

        User user = new User();
        user.setU_id(g_u_id);
        goods.setG_user(user);

        goods.setG_state(0);
        goods.setG_updateTime(new Date());

        homeMapper.insertGoods(goods);
    }

    @Override
    public List<GoodsQueryPo> getNewestGoodsList() {
        return homeMapper.selectNewestGoods();
    }
}
