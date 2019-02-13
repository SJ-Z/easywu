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
    List<FindGoodsQueryPo> selectNewestFindPeople(Map<String, Integer> pageMap);
    List<FindGoodsQueryPo> selectReleaseFindGoodsList(String u_id);
    List<FindGoodsQueryPo> selectReleaseFindPeopleList(String u_id);
    List<FindGoodsQueryPo> selectLikeFindGoodsList(String u_id);
    List<FindGoodsQueryPo> selectLikeFindPeopleList(String u_id);
    void insertFindGoods(FindGoodsQueryPo goods);
    void updateFindGoods(FindGoodsQueryPo goods);
    void insertFindPeople(FindGoodsQueryPo goods);
    void updateFindPeople(FindGoodsQueryPo goods);
    void insertLikeFindGoods(UpdateGoodsPo updateGoodsPo);
    void insertLikeFindPeople(UpdateGoodsPo updateGoodsPo);
    void deleteLikeFindGoods(UpdateGoodsPo updateGoodsPo);
    void deleteLikeFindPeople(UpdateGoodsPo updateGoodsPo);
    void updateFindGoodsLike(UpdateGoodsPo updateGoodsPo);
    void updateFindPeopleLike(UpdateGoodsPo updateGoodsPo);
    int insertCommentBeanFindGoods(CommentBean commentBean);
    int insertCommentDetailPoFindGoods(CommentDetailPo commentDetailPo);
    int insertCommentBeanFindPeople(CommentBean commentBean);
    int insertCommentDetailPoFindPeople(CommentDetailPo commentDetailPo);
    CommentBean selectFindGoodsCommentWithReply(String g_id);
    CommentBean selectFindPeopleCommentWithReply(String g_id);
    int insertFindGoodsReplyPo(ReplyDetailPo replyDetailPo);
    int insertFindPeopleReplyPo(ReplyDetailPo replyDetailPo);
    int updateFindGoodsState(UpdateGoodsPo updateGoodsPo);
    int updateFindPeopleState(UpdateGoodsPo updateGoodsPo);
    FindGoodsQueryPo selectFindGoodsById(String fg_id);
    FindGoodsQueryPo selectFindPeopleById(String fg_id);
    int updateFindGoodsUpdateTime(UpdateGoodsPo updateGoodsPo);
    int updateFindPeopleUpdateTime(UpdateGoodsPo updateGoodsPo);
    List<FindGoodsQueryPo> selectFindGoodsByTypeId(Map<String, Object> pageMap);
    List<FindGoodsQueryPo> selectFindPeopleByTypeId(Map<String, Object> pageMap);
    List<FindGoodsQueryPo> selectFindGoodsByKey(Map<String, Object> pageMap);
    List<FindGoodsQueryPo> selectFindPeopleByKey(Map<String, Object> pageMap);
}
