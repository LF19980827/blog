package com.hanhan.blog.controller.admin;

import com.hanhan.blog.entity.Article;
import com.hanhan.blog.entity.Tag;
import com.hanhan.blog.service.ArticleService;
import com.hanhan.blog.service.CategoryService;
import com.hanhan.blog.service.LogService;
import com.hanhan.blog.util.PageResult;
import com.hanhan.blog.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private LogService logService;

    @GetMapping("/articles")
    public String articlePage(HttpServletRequest request) {
        request.setAttribute("path", "articles");
        return "admin/article";
    }

    @GetMapping("/articles/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        // 前端发送 page 当前页码， limit 每页个数
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());

        PageResult pageResult = articleService.getArticlePage(page, limit);

        // 后台返回数据
        return new Result<>(200, "SUCCESS", pageResult);
    }

    // 发布文章的编辑界面
    @GetMapping("/articles/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        request.setAttribute("categories", categoryService.getAllCategories());
        return "admin/edit";
    }

    // 进入修改文章页面
    @GetMapping("/articles/edit/{articleId}")
    public String edit(HttpServletRequest request, @PathVariable("articleId") Integer articleId) {
        request.setAttribute("path", "edit");
        Article article = articleService.getArticleById(articleId);

        if (article == null) {
            return "error/error_400";
        }
        //将标签List转换为String传给前端
        List<Tag> tagList = article.getTagList();
        StringBuilder tags = new StringBuilder();
        for (int i = 0; i < tagList.size(); i++) {
            tags.append(tagList.get(i).getTagName());
            if (i < tagList.size() - 1) {
                tags.append(",");
            }
        }

        request.setAttribute("article", article);
        request.setAttribute("categories", categoryService.getAllCategories());
        request.setAttribute("tags", tags);
        return "admin/edit";
    }

    // 保存新文章
    @PostMapping("/articles/save")
    @ResponseBody
    public Result save(Article article, @RequestParam("articleTags") String articleTags) {
        logService.addLog("添加文章", article.getArticleTitle());
        String saveBlogResult = articleService.saveArticle(article, articleTags);

        if ("SUCCESS".equals(saveBlogResult)) {
            return new Result<>(200, saveBlogResult, null);
        } else {
            return new Result<>(500, saveBlogResult, null);
        }
    }

    // 修改旧文章
    @PostMapping("/articles/update")
    @ResponseBody
    public Result update(Article article, @RequestParam("articleTags") String articleTags) {

        logService.addLog("更新文章", article.getArticleTitle());
        String saveBlogResult = articleService.saveArticle(article, articleTags);

        if ("SUCCESS".equals(saveBlogResult)) {
            return new Result<>(200, saveBlogResult, null);
        } else {
            return new Result<>(500, saveBlogResult, null);
        }
    }


    // 批量删除
    @PostMapping("/articles/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return new Result<>(500, "参数异常！", null);
        }
        //查询要批量删除的文章的名字
        List<String> logDetails = articleService.getBatchNames(ids);

        if (articleService.deleteBatch(ids)) {
            logService.addLog("删除文章", String.join("， ", logDetails));
            return new Result<>(200, "SUCCESS", null);
        } else {
            return new Result<>(500, "删除失败！", null);
        }
    }
}
