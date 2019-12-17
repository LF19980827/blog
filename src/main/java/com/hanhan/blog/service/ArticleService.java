package com.hanhan.blog.service;

import com.hanhan.blog.entity.Article;
import com.hanhan.blog.util.PageResult;

import java.util.List;

public interface ArticleService {

    int getArticleCount();

    PageResult getArticlePage(Integer page, Integer limit);

    Article getArticleById(Integer articleId);

    String saveArticle(Article article, String articleTags);

    Boolean deleteBatch(Integer[] ids);

    List<String> getBatchNames(Integer[] ids);
}
