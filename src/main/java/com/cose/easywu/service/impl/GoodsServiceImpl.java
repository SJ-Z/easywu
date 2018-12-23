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
    public int addReplyToComment(String u_id, String reply, int comment_id, Date createTime) {
        ReplyDetailPo replyDetailPo = new ReplyDetailPo(u_id, reply, comment_id, createTime);
        if (goodsMapper.insertReplyPo(replyDetailPo) == 1) {
            return replyDetailPo.getId();
        } else {
            return 0;
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
    public CommentBean getGoodsComment(String g_id) {
        return goodsMapper.selectGoodsCommentWithReply(g_id);
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

        System.out.println("GoodsServiceImpl:" + goods.toString());

        if (isNew) {
            goodsMapper.insertGoods(goods);
        } else {
            goodsMapper.updateGoods(goods);
        }

        return g_updateTime;
    }

    @Override
    public List<GoodsQueryPo> getNewestGoodsList() {
        return goodsMapper.selectNewestGoods();
    }
}
