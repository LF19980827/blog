package com.hanhan.blog.controller.admin;

import com.hanhan.blog.service.CommentService;
import com.hanhan.blog.service.LogService;
import com.hanhan.blog.util.PageResult;
import com.hanhan.blog.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CommentController {

    @Resource
    CommentService commentService;

    @Resource
    LogService logService;

    // 评论管理页面
    @GetMapping("/comments")
    public String list(HttpServletRequest request) {
        request.setAttribute("path", "comments");
        return "admin/comment";
    }

    @GetMapping("/comments/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {

        // 前端发送 page 当前页码， limit 每页个数
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());

        PageResult pageResult = commentService.getCommentPage(page, limit);

        // 后台返回数据
        return new Result<>(200, "SUCCESS", pageResult);
    }

    // 批量审核
    @PostMapping("/comments/checkDone")
    @ResponseBody
    public Result checkDone(@RequestBody Integer[] ids) {

        if (ids.length < 1) {
            return new Result<>(500, "参数异常！", null);
        }

        List<String> logDetails = commentService.getBatchContent(ids);

        if (commentService.check(ids)) {
            logService.addLog("审核评论", String.join(", ", logDetails));
            return new Result<>(200, "SUCCESS", null);
        } else {
            return new Result<>(500, "删除失败！", null);
        }
    }

    // 添加回复
    @PostMapping("/comments/reply")
    @ResponseBody
    public Result reply(@RequestParam("commentId") Long commentId,
                        @RequestParam("replyBody") String replyBody) {
        if (commentId == null || commentId < 1 || StringUtils.isEmpty(replyBody)) {
            return new Result<>(500, "参数异常！", null);
        }
        if (commentService.reply(commentId, replyBody)) {
            logService.addLog("添加回复", replyBody);
            return new Result<>(200, "SUCCESS", null);
        } else {
            return new Result<>(500, "添加回复失败！", null);
        }
    }

    @PostMapping("/comments/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return new Result<>(500, "参数异常！", null);
        }
        List<String> logDetails = commentService.getBatchContent(ids);
        if (commentService.deleteBatch(ids)) {
            logService.addLog("删除评论或回复", String.join("， ", logDetails));
            return new Result<>(200, "SUCCESS", null);
        } else {
            return new Result<>(500, "删除失败！", null);
        }
    }

    // 回复管理页面
    @GetMapping("/replies")
    public String replyList(HttpServletRequest request) {
        request.setAttribute("path", "replies");
        return "admin/reply";
    }

    // 回复列表
    @GetMapping("/replies/list")
    @ResponseBody
    public Result replyList(@RequestParam Map<String, Object> params) {

        // 前端发送 page 当前页码， limit 每页个数
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());

        PageResult pageResult = commentService.getReplyPage(page, limit);

        // 后台返回数据
        return new Result<>(200, "SUCCESS", pageResult);
    }


    // 回复修改
    @RequestMapping(value = "/replies/update", method = RequestMethod.POST)
    @ResponseBody
    public Result replyUpdate(@RequestParam("commentId") Long commentId,
                              @RequestParam("replyBody") String replyBody) {
        if (commentId == null || commentId < 1 || StringUtils.isEmpty(replyBody)) {
            return new Result<>(500, "参数异常！", null);
        }
        if (commentService.updateReply(commentId, replyBody)) {
            logService.addLog("修改回复", replyBody);
            return new Result<>(200, "SUCCESS", null);
        } else {
            return new Result<>(500, "回复修改失败！", null);
        }
    }
}
