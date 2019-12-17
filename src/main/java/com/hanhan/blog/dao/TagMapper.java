package com.hanhan.blog.dao;

import com.hanhan.blog.entity.Tag;

import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer tagId);

    int updateByPrimaryKeySelective(Tag record);
    
    int updateByPrimaryKey(Tag record);

    // 以上是逆向工程生成的
    
    int getTagCount();

    List<Tag> getTagByStartAndLimit(Integer start, Integer limit);

    Tag selectByTagName(String tagName);

    int deleteTagByIds(Integer[] tagIds);

    // 根据文章ID得到标签列表
    List<Tag>getTagByAid(Integer aid);

    List<String> getTagNamesByIds(Integer[] ids);

}