package com.hanhan.blog.service;

import com.hanhan.blog.util.PageResult;

public interface LogService {

    /**
     * 根据页码和每页个数，返回日志查询结果
     * @param page
     * @param limit
     * @return
     */
    PageResult getLogPage(Integer page, Integer limit);

    void addLog(String type, String detail);

    Boolean deleteBatch(Integer[] ids);
}
