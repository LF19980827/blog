package com.hanhan.blog.service.impl;

import com.hanhan.blog.dao.ArticleTagRefMapper;
import com.hanhan.blog.dao.TagMapper;
import com.hanhan.blog.entity.Tag;
import com.hanhan.blog.service.TagService;
import com.hanhan.blog.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagRefMapper articleTagRefMapper;

    @Override
    public int getTagCount() {
        return tagMapper.getTagCount();
    }

    @Override
    public PageResult getTagPage(Integer page, Integer limit) {
        List<Tag> tagList = tagMapper.getTagByStartAndLimit((page - 1) * limit, limit);
        int count = tagMapper.getTagCount(); // 获取总数
        PageResult pageResult = new PageResult(tagList, count, limit, page);
        return pageResult;
    }

    @Override
    public Boolean saveTag(String tagName) {
        Tag temp = tagMapper.selectByTagName(tagName);
        // 判断该标签名是否已经存在
        if (temp == null) {
            Tag tag = new Tag();
            tag.setTagName(tagName);
            return tagMapper.insertSelective(tag) > 0;
        }
        return false;
    }

    @Override
    public Boolean updateTag(Integer tagId, String tagName) {

        Tag temp = tagMapper.selectByTagName(tagName);
        // 判断该标签名是否已经存在
        if (temp == null) {
            Tag tag = tagMapper.selectByPrimaryKey(tagId);
            tag.setTagName(tagName);
            return tagMapper.updateByPrimaryKeySelective(tag) > 0;
        }
        return false;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        // 删除文章标签关系表中的数据
        articleTagRefMapper.deleteByTagIds(ids);

        //删除标签数据
        return tagMapper.deleteTagByIds(ids) > 0;
    }

    @Override
    public List<String> getBatchNames(Integer[] ids) {
        return tagMapper.getTagNamesByIds(ids);
    }

}

