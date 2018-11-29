package com.cose.easywu.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cose.easywu.po.GoodsQueryPo;
import com.cose.easywu.service.GoodsService;
import com.cose.easywu.utils.CommonUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    // 删除商品
    @RequestMapping("/deleteGoods")
    public @ResponseBody
    String deleteGoods(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String g_id = jsonObject.getString("g_id");
        String u_id = jsonObject.getString("u_id");
        String content;
        if (goodsService.userDeleteGoods(g_id, u_id)) {
            content = "{'code':'1', 'msg':'商品删除成功'}";
        } else {
            content = "{'code':'0', 'msg':'商品删除失败'}";
        }
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 擦亮商品
    @RequestMapping("/polishGoods")
    public @ResponseBody
    String polishGoods(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String g_id = jsonObject.getString("g_id");
        String u_id = jsonObject.getString("u_id");
        Date updateTime = new Date();
        String content;
        if (goodsService.polishGoods(g_id, u_id, updateTime)) {
            content = "{'code':'1', 'msg':'" + updateTime.getTime() + "'}";
        } else {
            content = "{'code':'0', 'msg':'擦亮失败'}";
        }
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 修改收藏的商品
    @RequestMapping("/setLikeGoods")
    public @ResponseBody
    String setLikeGoods(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String g_id = jsonObject.getString("g_id");
        String u_id = jsonObject.getString("u_id");
        boolean like = jsonObject.getBoolean("like");
        goodsService.setLikeGoods(g_id, u_id, like);
        String content;
        if (like) {
            content = "{'code':'1', 'msg':'收藏成功'}";
        } else {
            content = "{'code':'1', 'msg':'取消收藏成功'}";
        }
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 获取最新发布
    @RequestMapping("/newestGoods")
    public @ResponseBody
    String getNewestGoods() {
        List<GoodsQueryPo> newestGoodsList = goodsService.getNewestGoodsList();
        String content = JSONArray.toJSONString(newestGoodsList);
        try {
            return URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 发布闲置
    @RequestMapping("/release_goods")
    public @ResponseBody String releaseGoods(HttpServletRequest request) {
        String content; // 返回给客户端的内容

        try {
            request.setCharacterEncoding("utf-8");  //设置编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //创建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory(10 * 1024 * 1024, new File("E:/temp"));//设置缓存大小和临时目录
        //得到解析器
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //设置单个文件最大值为10*1024*1024
        sfu.setFileSizeMax(10 * 1024 * 1024);

        // 设置保存路径
        String str = request.getServletContext().getRealPath("/");
        int loc = str.indexOf("target");
        String savepath = str.substring(0, loc) + "goods_pic";

        //使用sfu去解析request对象，得到List<FileItem>
        try {
            List<FileItem> fileItemList = sfu.parseRequest(new ServletRequestContext(request));
            List<Integer> picIndexList = new ArrayList<>();
            Map<String, String> params = new HashMap<>();
            for (int i = 0; i < fileItemList.size(); i++) {
                if (fileItemList.get(i).isFormField()) { // 参数类型
                    params.put(fileItemList.get(i).getFieldName(), fileItemList.get(i).getString("utf-8"));
                } else { // 图片类型
                    picIndexList.add(i);
                }
            }

            // 得到发布内容信息
            String g_name = params.get("g_name");
            String g_desc = params.get("g_desc");
            double g_price = Double.valueOf(params.get("g_price"));
            double g_originalPrice = Double.valueOf(params.get("g_originalPrice"));
            String g_t_id = params.get("g_t_id");
            String g_u_id = params.get("g_u_id");

            String g_id = params.get("g_id");
            boolean isNew = true;
            if (g_id != null) {
                isNew = false;
            } else {
                g_id = CommonUtils.uuid(16);
            }

            if (!isNew) {
                for (int i = 0; i < 3; i++) {
                    // 删除服务器上原有商品图片
                    File photoFile = new File(savepath, g_id + i + ".jpg");
                    if (photoFile.exists()) {
                        photoFile.delete();
                    }
                }
            }

            //设置图片名称：g_id + 图片索引 + 图片后缀(.jpg)
            List<String> filenames = new ArrayList<>();
            for (int i = 0; i < picIndexList.size(); i++) {
                filenames.add(g_id + i + ".jpg");
            }

            for (int i = 0; i < filenames.size(); i++) {
                //使用目录和文件名称创建目标文件
                File destFile = new File(savepath, filenames.get(i));
                //保存图片文件到目标文件位置
                fileItemList.get(picIndexList.get(i)).write(destFile);
            }

            // 保存到服务器
            Date g_updateTime = goodsService.release(isNew, g_id, g_name, g_desc, g_price, g_originalPrice, filenames, g_t_id, g_u_id);

            // 返回成功信息和图片名给客户端
            content = "{'code':'1', 'msg':'发布成功', 'g_id':'" + g_id + "', 'g_updateTime':'" + g_updateTime.getTime() + "'}";
            try {
                return URLEncoder.encode(content, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return content;
        } catch (Exception e) {
            if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
                // 图片尺寸过大
                content = "{'code':'0', 'msg':'图片尺寸过大，发布失败'}";
                try {
                    return URLEncoder.encode(content, "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                return content;
            }
        }

        return null;
    }

}
