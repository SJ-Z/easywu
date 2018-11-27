package com.cose.easywu.service;

import com.cose.easywu.po.GoodsQueryPo;

import java.util.Date;
import java.util.List;

public interface GoodsService {

    void release(String g_id, String g_name, String g_desc, double g_price, double g_originalPrice,
                 List<String> filenames, String g_t_id, String g_u_id);
    List<GoodsQueryPo> getNewestGoodsList();
    void setLikeGoods(String g_id, String u_id, boolean like);
    boolean polishGoods(String g_id, String u_id, Date updateTime);
    boolean userDeleteGoods(String g_id, String u_id);
}
