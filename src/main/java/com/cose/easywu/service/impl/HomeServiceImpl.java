package com.cose.easywu.service.impl;

import com.cose.easywu.mapper.HomeMapper;
import com.cose.easywu.po.Banner;
import com.cose.easywu.po.HomeData;
import com.cose.easywu.po.Type;
import com.cose.easywu.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeMapper homeMapper;

    @Override
    public HomeData getHomeData() {
        List<Banner> banner = homeMapper.selectAllBanner();
        List<Type> type = homeMapper.selectAllType();
        return new HomeData(banner, type);
    }
}
