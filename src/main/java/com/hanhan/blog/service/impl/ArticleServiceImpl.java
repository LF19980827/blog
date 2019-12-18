package com.hanhan.blog.service.impl;

import com.hanhan.blog.dao.ArticleMapper;
import com.hanhan.blog.dao.ArticleTagRefMapper;
import com.hanhan.blog.dao.TagMapper;
import com.hanhan.blog.entity.Article;
import com.hanhan.blog.entity.ArticleTagRef;
import com.hanhan.blog.entity.Tag;
import com.hanhan.blog.service.ArticleService;
import com.hanhan.blog.service.LogService;
import com.hanhan.blog.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagRefMapper articleTagRefMapper;

    @Resource
    private LogService logService;



    @Override
    public int getArticleCount() {
        return articleMapper.getArticleCount();
    }

    @Override
    public PageResult getArticlePage(Integer page, Integer limit) {
        List<Article> articleList = articleMapper.getArticleByStartAndLimit((page - 1) * limit, limit);
        int count = articleMapper.getArticleCount(); // 获取总数
        PageResult pageResult = new PageResult(articleList, count, limit, page);
        return pageResult;
    }

    @Override
    public Article getArticleById(Integer articleId) {
        return articleMapper.selectByPrimaryKey(articleId);
    }

    @Override
    public String saveArticle(Article article, String articleTags) {
        String[] tags = articleTags.split(",");
        //文章标签集合
        Set<String> newTags = new HashSet<>(Arrays.asList(tags));
        if (tags.length > 6) {
            return "标签数量限制为6";
        }

        // 文章添加userID，暂时不添加，因为只有一个作者

        Set<String> deleteTags = new HashSet<>();  //文章更新后需要删除的标签
        Integer articleId = article.getArticleId();
        // 更新旧文章
        if(articleId != null) {
            // 根据文章id获得原来的全部标签名
            List<Tag> oldTagsList =  tagMapper.getTagByAid(articleId);
            for (Tag tag : oldTagsList) {
                if(newTags.contains(tag.getTagName())) {
                    //如果更改前后都有的标签就从新标签里删除
                    newTags.remove(tag.getTagName());
                }
                else {
                    //如果出现更改前有，更改后没有的标签，则添加至删除标签列表
                    deleteTags.add(tag.getTagName());
                }
            }
            // 更新文章
            Article newArticle = articleMapper.selectByPrimaryKey(articleId);
            newArticle.setArticleUpdateTime(new Date());
            newArticle.setArticleTitle(article.getArticleTitle());
            newArticle.setArticleContent(article.getArticleContent());
            newArticle.setArticleStatus(article.getArticleStatus());
            newArticle.setArticleEnableComment(article.getArticleEnableComment());
            newArticle.setArticleCategoryId(article.getArticleCategoryId());
            articleMapper.updateByPrimaryKeySelective(newArticle);
        }
        else {
            // 添加新文章
            article.setArticleCreateTime(new Date());
            article.setArticleUpdateTime(new Date());
            // useGeneratedKeys="true" 表示开启返回自增ID， keyProperty="articleId" 表示返回主键的名字。
            articleMapper.insertSelective(article);
            // articleId之前为空，此处得到增加后的id
            articleId = article.getArticleId();
        }

        for (String newTag : newTags) {
            // 当新标签不存在Tag表时，把它加入Tag表
            if(tagMapper.selectByTagName(newTag) == null) {
                Tag tag = new Tag();
                tag.setTagName(newTag);
                tagMapper.insertSelective(tag);
                logService.addLog("添加标签", newTag);
            }
            // 添加关系到关系表
            Tag tag = tagMapper.selectByTagName(newTag);
            ArticleTagRef articleTagRef = new ArticleTagRef();
            articleTagRef.setArticleId(articleId);
            articleTagRef.setTagId(tag.getTagId());
            articleTagRefMapper.insertSelective(articleTagRef);
        }

        // 在关系表中删除要删除的标签的关系
        for (String deleteTag : deleteTags) {
            Tag tag = tagMapper.selectByTagName(deleteTag);
            ArticleTagRef articleTagRef = new ArticleTagRef();
            articleTagRef.setTagId(tag.getTagId());
            articleTagRef.setArticleId(articleId);
            articleTagRefMapper.deleteByPrimaryKey(articleTagRef);
        }
        return "SUCCESS";
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        for(Integer articleId : ids) {
            // 删除article表
            articleMapper.deleteByPrimaryKey(articleId);
            // 删除关系表
            articleTagRefMapper.deleteByArticleID(articleId);
        }
        return true;
    }

    @Override
    public List<String> getBatchNames(Integer[] ids) {
        List<String> batchNames = new ArrayList<>();
        for (Integer id : ids) {
            batchNames.add(articleMapper.getArticleTitleByAid(id));
        }
        return batchNames;
    }
}
