package com.cose.easywu.service;

import com.cose.easywu.po.GoodsQueryPo;
import com.cose.easywu.po.HomeData;

import java.util.List;

public interface HomeService {

    HomeData getHomeData();
    void release(String g_id, String g_name, String g_desc, double g_price, double g_originalPrice,
                 List<String> filenames, String g_t_id, String g_u_id);
    void release(String g_id, String g_name, String g_desc, double g_price, double g_originalPrice,
                 String g_t_id, String g_u_id);
    List<GoodsQueryPo> getNewestGoodsList();
}
