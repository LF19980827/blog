package com.hanhan.blog.service;

import com.hanhan.blog.util.PageResult;

import java.util.List;

public interface CommentService {

    /**
     * 获取评论数量
     * @return
     */
    int getCommentCount();

    /**
     * 获取评论列表
     * @param page
     * @param limit
     * @return
     */
    PageResult getCommentPage(Integer page, Integer limit);

    /**
     * 审核
     * @param ids
     * @return
     */
    Boolean check(Integer[] ids);

    /**
     * 回复
     * @param commentId
     * @param replyBody
     * @return
     */
    Boolean reply(Long commentId, String replyBody);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Boolean deleteBatch(Integer[] ids);

    /**
     * 获取回复列表
     * @param page
     * @param limit
     * @return
     */
    PageResult getReplyPage(Integer page, Integer limit);

    /**
     * 跟新回复
     * @param commentId
     * @param replyBody
     * @return
     */
    Boolean updateReply(Long commentId, String replyBody);

    /**
     * 根据id，获得评论内容，用于日志记录
     * @param ids
     * @return
     */
    List<String> getBatchContent(Integer[] ids);
}
