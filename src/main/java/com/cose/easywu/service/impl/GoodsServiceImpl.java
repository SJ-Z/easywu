package com.cose.easywu.service.impl;

import com.cose.easywu.mapper.GoodsMapper;
import com.cose.easywu.po.*;
import com.cose.easywu.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public int addReplyToComment(String u_id, String reply, String origin_uid, int comment_id, Date createTime) {
        ReplyDetailPo replyDetailPo = new ReplyDetailPo(u_id, reply, origin_uid, comment_id, createTime);
        if (goodsMapper.insertReplyPo(replyDetailPo) == 1) {
            return replyDetailPo.getId();
        } else {
            return 0;
        }
    }

    @Override
    public int addReplyToFindComment(String u_id, String reply, String origin_uid, int comment_id, Date createTime, boolean isFindGoods) {
        ReplyDetailPo replyDetailPo = new ReplyDetailPo(u_id, reply, origin_uid, comment_id, createTime);
        if (isFindGoods) {
            if (goodsMapper.insertFindGoodsReplyPo(replyDetailPo) == 1) {
                return replyDetailPo.getId();
            } else {
                return 0;
            }
        } else {
            if (goodsMapper.insertFindPeopleReplyPo(replyDetailPo) == 1) {
                return replyDetailPo.getId();
            } else {
                return 0;
            }
        }
    }

    @Override
    public int addComment(String comment, int gc_id, String g_id, String u_id, Date date) {
        if (gc_id == -1) {
            CommentBean commentBean = new CommentBean(g_id);
            if (goodsMapper.insertCommentBean(commentBean) == 1) {
                gc_id = commentBean.getId();
            } else {
                return 0;
            }
        }
        CommentDetailPo commentDetailPo = new CommentDetailPo(comment, gc_id, g_id, u_id, date);
        if (goodsMapper.insertCommentDetailPo(commentDetailPo) == 1) {
            return commentDetailPo.getId();
        } else {
            return 0;
        }
    }

    @Override
    public int addCommentToFindGoods(String comment, int gc_id, String g_id, String u_id, Date date) {
        if (gc_id == -1) {
            CommentBean commentBean = new CommentBean(g_id);
            if (goodsMapper.insertCommentBeanFindGoods(commentBean) == 1) {
                gc_id = commentBean.getId();
            } else {
                return 0;
            }
        }
        CommentDetailPo commentDetailPo = new CommentDetailPo(comment, gc_id, g_id, u_id, date);
        if (goodsMapper.insertCommentDetailPoFindGoods(commentDetailPo) == 1) {
            return commentDetailPo.getId();
        } else {
            return 0;
        }
    }

    @Override
    public int addCommentToFindPeople(String comment, int gc_id, String g_id, String u_id, Date date) {
        if (gc_id == -1) {
            CommentBean commentBean = new CommentBean(g_id);
            if (goodsMapper.insertCommentBeanFindPeople(commentBean) == 1) {
                gc_id = commentBean.getId();
            } else {
                return 0;
            }
        }
        CommentDetailPo commentDetailPo = new CommentDetailPo(comment, gc_id, g_id, u_id, date);
        if (goodsMapper.insertCommentDetailPoFindPeople(commentDetailPo) == 1) {
            return commentDetailPo.getId();
        } else {
            return 0;
        }
    }

    @Override
    public GoodsQueryPo getGoodsInfo(String g_id) {
        return goodsMapper.selectGoodsById(g_id);
    }

    @Override
    public List<GoodsQueryPo> getGoodsOfType(String type_id, Page page) {
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("type_id", type_id);
        pageMap.put("pageSize", page.getPageSize());
        pageMap.put("startPos", page.getPageSize() * page.getPageCode());
        return goodsMapper.selectGoodsByTypeId(pageMap);
    }

    @Override
    public List<GoodsQueryPo> searchGoods(String key, Page page) {
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("key", key);
        pageMap.put("pageSize", page.getPageSize());
        pageMap.put("startPos", page.getPageSize() * page.getPageCode());
        return goodsMapper.selectGoodsByKey(pageMap);
    }

    @Override
    public CommentBean getGoodsComment(String g_id) {
        return goodsMapper.selectGoodsCommentWithReply(g_id);
    }

    @Override
    public CommentBean getFindGoodsComment(String g_id, boolean isFindGoods) {
        if (isFindGoods) {
            return goodsMapper.selectFindGoodsCommentWithReply(g_id);
        } else {
            return goodsMapper.selectFindPeopleCommentWithReply(g_id);
        }
    }

    @Override
    public boolean confirmNewGoodsOrder(String g_id, String u_id) {
        UpdateGoodsPo updateGoodsPo = new UpdateGoodsPo(g_id, u_id);
        updateGoodsPo.setState(1); // 1表示订单完成
        int result = goodsMapper.updateGoodsState(updateGoodsPo);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean refuseNewGoodsOrder(String g_id, String u_id) {
        UpdateGoodsPo updateGoodsPo = new UpdateGoodsPo(g_id, u_id);
        updateGoodsPo.setState(0); // 0表示商品重新上架
        int result = goodsMapper.updateGoodsStateAndSetBuyerIDNull(updateGoodsPo);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addNewGoodsOrder(String g_id, String u_id, Date date) {
        UpdateGoodsPo updateGoodsPo = new UpdateGoodsPo(g_id, u_id, date);
        updateGoodsPo.setState(5); // 5表示被购买者下订单
        int result = goodsMapper.updateGoodsStateAndBuyerID(updateGoodsPo);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean userRemoveGoods(String g_id, String u_id) {
        UpdateGoodsPo updateGoodsPo = new UpdateGoodsPo(g_id, u_id);
        updateGoodsPo.setState(4); // 4表示用户界面不显示
        int result = goodsMapper.updateGoodsState(updateGoodsPo);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean userDeleteGoods(String g_id, String u_id) {
        UpdateGoodsPo updateGoodsPo = new UpdateGoodsPo(g_id, u_id);
        updateGoodsPo.setState(2); // 2表示被用户下架
        int result = goodsMapper.updateGoodsState(updateGoodsPo);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean userDeleteFindGoods(String g_id, String u_id, boolean isFindGoods) {
        UpdateGoodsPo updateGoodsPo = new UpdateGoodsPo(g_id, u_id);
        updateGoodsPo.setState(2); // 2表示被用户下架
        int result;
        if (isFindGoods) {
            result = goodsMapper.updateFindGoodsState(updateGoodsPo);
        } else {
            result = goodsMapper.updateFindPeopleState(updateGoodsPo);
        }
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean polishGoods(String g_id, String u_id, Date updateTime) {
        UpdateGoodsPo updateGoodsPo = new UpdateGoodsPo(g_id, u_id, updateTime);
        int result = goodsMapper.updateGoodsUpdateTime(updateGoodsPo);
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void setLikeGoods(String g_id, String u_id, boolean like) {
        UpdateGoodsPo updateGoodsPo;
        if (like) {
            updateGoodsPo = new UpdateGoodsPo(g_id, u_id, 1);
        } else {
            updateGoodsPo = new UpdateGoodsPo(g_id, u_id, -1);
        }
        if (like) {
            goodsMapper.insertLikeGoods(updateGoodsPo);
        } else {
            goodsMapper.deleteLikeGoods(updateGoodsPo);
        }
        goodsMapper.updateGoodsLike(updateGoodsPo);
    }

    @Override
    public void setLikeFindGoods(String g_id, String u_id, boolean like, boolean isFindGoods) {
        UpdateGoodsPo updateGoodsPo;
        if (like) {
            updateGoodsPo = new UpdateGoodsPo(g_id, u_id, 1);
        } else {
            updateGoodsPo = new UpdateGoodsPo(g_id, u_id, -1);
        }
        if (like) {
            if (isFindGoods) {
                goodsMapper.insertLikeFindGoods(updateGoodsPo);
            } else {
                goodsMapper.insertLikeFindPeople(updateGoodsPo);
            }
        } else {
            if (isFindGoods) {
                goodsMapper.deleteLikeFindGoods(updateGoodsPo);
            } else {
                goodsMapper.deleteLikeFindPeople(updateGoodsPo);
            }
        }
        if (isFindGoods) {
            goodsMapper.updateFindGoodsLike(updateGoodsPo);
        } else {
            goodsMapper.updateFindPeopleLike(updateGoodsPo);
        }
    }

    @Override
    public Date releaseFindGoods(int type, boolean isNew, String fg_id, String fg_name, String fg_desc, List<String> filenames, String fg_ft_id, String fg_u_id) {
        Date g_updateTime = new Date();
        FindGoodsQueryPo goods = new FindGoodsQueryPo(fg_id, fg_name, fg_desc, 0, g_updateTime, fg_ft_id, fg_u_id);
        int picLen = filenames.size();
        if (picLen == 1) {
            goods.setFg_pic1(filenames.get(0));
            goods.setFg_pic2(null);
            goods.setFg_pic3(null);
        } else if (picLen == 2) {
            goods.setFg_pic1(filenames.get(0));
            goods.setFg_pic2(filenames.get(1));
            goods.setFg_pic3(null);
        } else if (picLen == 3) {
            goods.setFg_pic1(filenames.get(0));
            goods.setFg_pic2(filenames.get(1));
            goods.setFg_pic3(filenames.get(2));
        }

        if (type == 0) {
            if (isNew) {
                goodsMapper.insertFindPeople(goods);
            } else {
                goodsMapper.updateFindPeople(goods);
            }
        } else {
            if (isNew) {
                goodsMapper.insertFindGoods(goods);
            } else {
                goodsMapper.updateFindGoods(goods);
            }
        }

        return g_updateTime;
    }

    @Override
    public Date release(boolean isNew, String g_id, String g_name, String g_desc, double g_price, double g_originalPrice,
                        List<String> filenames, String g_t_id, String g_u_id) {
        Goods goods = new Goods(g_id, g_name, g_desc, g_price, g_originalPrice);
        int picLen = filenames.size();
        if (picLen == 1) {
            goods.setG_pic1(filenames.get(0));
            goods.setG_pic2(null);
            goods.setG_pic3(null);
        } else if (picLen == 2) {
            goods.setG_pic1(filenames.get(0));
            goods.setG_pic2(filenames.get(1));
            goods.setG_pic3(null);
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
        Date g_updateTime = new Date();
        goods.setG_updateTime(g_updateTime);

        if (isNew) {
            goodsMapper.insertGoods(goods);
        } else {
            goodsMapper.updateGoods(goods);
        }

        return g_updateTime;
    }

    @Override
    public List<GoodsQueryPo> getNewestGoodsList(Page page) {
        Map<String, Integer> pageMap = new HashMap<>();
        pageMap.put("pageSize", page.getPageSize());
        pageMap.put("startPos", page.getPageSize() * page.getPageCode());
        return goodsMapper.selectNewestGoods(pageMap);
    }

    @Override
    public List<FindGoodsQueryPo> getNewestFindGoodsList(Page page) {
        Map<String, Integer> pageMap = new HashMap<>();
        pageMap.put("pageSize", page.getPageSize());
        pageMap.put("startPos", page.getPageSize() * page.getPageCode());
        return goodsMapper.selectNewestFindGoods(pageMap);
    }

    @Override
    public List<FindGoodsQueryPo> getNewestFindPeopleList(Page page) {
        Map<String, Integer> pageMap = new HashMap<>();
        pageMap.put("pageSize", page.getPageSize());
        pageMap.put("startPos", page.getPageSize() * page.getPageCode());
        return goodsMapper.selectNewestFindPeople(pageMap);
    }
}
