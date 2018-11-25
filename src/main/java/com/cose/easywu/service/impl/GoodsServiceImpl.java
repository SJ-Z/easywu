package com.cose.easywu.service.impl;

import com.cose.easywu.mapper.GoodsMapper;
import com.cose.easywu.po.*;
import com.cose.easywu.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void setLikeGoods(String g_id, String u_id, boolean like) {
        UpdateLikeGoodsPo updateLikeGoodsPo;
        if (like) {
            updateLikeGoodsPo = new UpdateLikeGoodsPo(g_id, u_id, 1);
        } else {
            updateLikeGoodsPo = new UpdateLikeGoodsPo(g_id, u_id, -1);
        }
        if (like) {
            goodsMapper.insertLikeGoods(updateLikeGoodsPo);
        } else {
            goodsMapper.deleteLikeGoods(updateLikeGoodsPo);
        }
        goodsMapper.updateGoodsLike(updateLikeGoodsPo);
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

        goodsMapper.insertGoods(goods);
    }

    @Override
    public List<GoodsQueryPo> getNewestGoodsList() {
        return goodsMapper.selectNewestGoods();
    }
}
