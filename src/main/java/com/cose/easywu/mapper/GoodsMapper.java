package com.cose.easywu.mapper;

import com.cose.easywu.po.*;

import java.util.List;

public interface GoodsMapper {

    void insertGoods(Goods goods);
    void updateGoods(Goods goods);
    List<GoodsQueryPo> selectNewestGoods();
    List<GoodsQueryPo> selectGoodsLike(String u_id);
    List<GoodsQueryPo> selectReleaseGoodsList(String u_id);
    void insertLikeGoods(UpdateGoodsPo updateGoodsPo);
    void deleteLikeGoods(UpdateGoodsPo updateGoodsPo);
    void updateGoodsLike(UpdateGoodsPo updateGoodsPo);
    int updateGoodsUpdateTime(UpdateGoodsPo updateGoodsPo);
    int updateGoodsState(UpdateGoodsPo updateGoodsPo);
    CommentBean selectGoodsCommentWithReply(String g_id);
    int insertCommentDetailPo(CommentDetailPo commentDetailPo);
    int insertCommentBean(CommentBean commentBean);
    int insertReplyPo(ReplyDetailPo replyDetailPo);
    GoodsQueryPo selectGoodsById(String g_id);
}
