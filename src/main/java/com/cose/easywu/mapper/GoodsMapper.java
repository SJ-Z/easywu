package com.cose.easywu.mapper;

import com.cose.easywu.po.*;

import java.util.List;
import java.util.Map;

public interface GoodsMapper {

    void insertGoods(Goods goods);
    void updateGoods(Goods goods);
    List<GoodsQueryPo> selectNewestGoods(Map<String, Integer> page);
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
    List<GoodsQueryPo> selectGoodsByTypeId(Map<String, Object> map);
    List<GoodsQueryPo> selectGoodsByKey(Map<String, Object> pageMap);
    int updateGoodsStateAndBuyerID(UpdateGoodsPo updateGoodsPo);
    int updateGoodsStateAndSetBuyerIDNull(UpdateGoodsPo updateGoodsPo);
    List<GoodsQueryPo> selectBuyGoodsList(String u_id);
    List<FindGoodsQueryPo> selectNewestFindGoods(Map<String, Integer> pageMap);
    List<FindGoodsQueryPo> selectReleaseFindGoodsList(String u_id);
    List<FindGoodsQueryPo> selectLikeFindGoodsList(String u_id);
    void insertFindGoods(FindGoodsQueryPo goods);
    void updateFindGoods(FindGoodsQueryPo goods);
}
