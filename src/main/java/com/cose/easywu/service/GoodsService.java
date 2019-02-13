package com.cose.easywu.service;

import com.cose.easywu.po.CommentBean;
import com.cose.easywu.po.FindGoodsQueryPo;
import com.cose.easywu.po.GoodsQueryPo;
import com.cose.easywu.po.Page;

import java.util.Date;
import java.util.List;

public interface GoodsService {

    Date release(boolean isNew, String g_id, String g_name, String g_desc, double g_price, double g_originalPrice,
                 List<String> filenames, String g_t_id, String g_u_id);
    List<GoodsQueryPo> getNewestGoodsList(Page page);
    void setLikeGoods(String g_id, String u_id, boolean like);
    boolean polishGoods(String g_id, String u_id, Date updateTime);
    boolean userDeleteGoods(String g_id, String u_id);
    boolean userRemoveGoods(String g_id, String u_id);
    boolean addNewGoodsOrder(String g_id, String u_id, Date date);
    CommentBean getGoodsComment(String g_id);
    int addReplyToComment(String u_id, String reply, String origin_uid, int comment_id, Date createTime);
    int addComment(String comment, int gc_id, String g_id, String u_id, Date date);
    GoodsQueryPo getGoodsInfo(String g_id);
    List<GoodsQueryPo> getGoodsOfType(String type_id, Page page);
    List<GoodsQueryPo> searchGoods(String key, Page page);
    boolean confirmNewGoodsOrder(String g_id, String u_id);
    boolean refuseNewGoodsOrder(String g_id, String u_id);
    Date releaseFindGoods(int type, boolean isNew, String fg_id, String fg_name, String fg_desc, List<String> filenames, String fg_ft_id, String fg_u_id);
    List<FindGoodsQueryPo> getNewestFindGoodsList(Page page);
    List<FindGoodsQueryPo> getNewestFindPeopleList(Page page);
    void setLikeFindGoods(String fg_id, String u_id, boolean like, boolean isFindGoods);
    int addCommentToFindGoods(String comment, int gc_id, String fg_id, String u_id, Date createTime);
    int addCommentToFindPeople(String comment, int gc_id, String fg_id, String u_id, Date createTime);
    CommentBean getFindGoodsComment(String fg_id, boolean isFindGoods);
    int addReplyToFindComment(String u_id, String reply, String origin_uid, int comment_id, Date createTime, boolean isFindGoods);
    boolean userDeleteFindGoods(String fg_id, String u_id, boolean isFindGoods);
    FindGoodsQueryPo getFindGoodsInfo(String fg_id, boolean isFindGoods);
    boolean polishFindGoods(String fg_id, String u_id, Date updateTime, boolean isFindGoods);
    List<FindGoodsQueryPo> getFindGoodsOfType(String type_id, Page page, boolean isFindGoods);
    List<FindGoodsQueryPo> searchFindGoods(String key, Page page, boolean isFindGoods);
    boolean userRemoveFindGoods(String fg_id, String u_id, boolean isFindGoods);
}
