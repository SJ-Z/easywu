package com.cose.easywu.controller;

import com.alibaba.fastjson.JSONObject;
import com.cose.easywu.po.HomeData;
import com.cose.easywu.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    // 获取主页数据
    @RequestMapping("/home")
    public @ResponseBody String editNick() {
        HomeData homeData = homeService.getHomeData();
        String content = JSONObject.toJSONString(homeData);
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
