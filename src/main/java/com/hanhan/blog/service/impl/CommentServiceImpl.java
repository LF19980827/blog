package com.hanhan.blog.service.impl;

import com.hanhan.blog.dao.CommentMapper;
import com.hanhan.blog.entity.Comment;
import com.hanhan.blog.service.CommentService;
import com.hanhan.blog.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentMapper commentMapper;

    @Override
    public int getCommentCount() {
        return commentMapper.getCommentCount();
    }

    @Override
    public PageResult getCommentPage(Integer page, Integer limit) {
        List<Comment> commentList = commentMapper.getCommentByStartAndLimit((page - 1) * limit, limit);
        int count = commentMapper.getCommentCount(); // 获取总数
        PageResult pageResult = new PageResult(commentList, count, limit, page);
        return pageResult;
    }

    @Override
    public Boolean check(Integer[] ids) {
        return commentMapper.checkByIds(ids) > 0;
    }

    @Override
    public Boolean reply(Long commentId, String replyBody) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        Comment newReplyComment = new Comment();
        newReplyComment.setCommentContent(replyBody);
        newReplyComment.setCommentCreateTime(new Date());
        newReplyComment.setCommentPid(commentId);
        newReplyComment.setCommentStatus((byte)2);
        newReplyComment.setCommentArticleId(comment.getCommentArticleId());
        return commentMapper.insertSelective(newReplyComment) > 0;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        for (Integer id : ids) {
            commentMapper.deleteByCommentId(id);
        }
        return true;
    }

    @Override
    public PageResult getReplyPage(Integer page, Integer limit) {
        List<Comment> commentList = commentMapper.getReplyByStartAndLimit((page - 1) * limit, limit);
        int count = commentMapper.getReplyCount(); // 获取总数
        PageResult pageResult = new PageResult(commentList, count, limit, page);
        return pageResult;
    }

    @Override
    public Boolean updateReply(Long commentId, String replyBody) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        comment.setCommentContent(replyBody);
        comment.setCommentCreateTime(new Date());
        return commentMapper.updateByPrimaryKeySelective(comment) > 0;
    }

    @Override
    public List<String> getBatchContent(Integer[] ids) {

        List<String> batchContent = new ArrayList<>();
        for (Integer id : ids) {
            batchContent.add(commentMapper.getCommentContentByCommentId(id));
        }
        return batchContent;
    }
}
