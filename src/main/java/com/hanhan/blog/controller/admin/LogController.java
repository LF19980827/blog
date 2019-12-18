package com.hanhan.blog.controller.admin;

import com.hanhan.blog.service.LogService;
import com.hanhan.blog.util.PageResult;
import com.hanhan.blog.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class LogController {

    @Resource
    private LogService logService;

    @GetMapping("/log")
    public String logPage(HttpServletRequest request) {
        request.setAttribute("path", "log");
        return "admin/log";
    }


    /**
     * 日志列表
     */
    @GetMapping(value = "/log/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {

        // 前端发送 page 当前页码， limit 每页个数
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());

        PageResult pageResult = logService.getLogPage(page, limit);

        // 后台返回数据
        return new Result<>(200, "SUCCESS", pageResult);
    }

    @PostMapping(value = "/log/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return new Result<>(500, "参数异常！", null);
        }

        if (logService.deleteBatch(ids)) {
            return new Result<>(200, "SUCCESS", null);

        } else {
            return new Result<>(500, "删除失败", null);
        }
    }
}
