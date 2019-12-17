package com.hanhan.blog.service.impl;


import com.hanhan.blog.dao.LogMapper;
import com.hanhan.blog.entity.Log;
import com.hanhan.blog.service.LogService;
import com.hanhan.blog.util.LogCreator;
import com.hanhan.blog.util.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Resource
    private LogMapper logMapper;


    @Override
    public PageResult getLogPage(Integer page, Integer limit) {
        List<Log> logList = logMapper.getLogByStartAndLimit((page - 1) * limit, limit);
        int count = logMapper.getLogCount(); // 获取总数
        PageResult pageResult = new PageResult(logList, count, limit, page);
        return pageResult;
    }

    @Override
    public void addLog(String type, String detail) {
        Log log = LogCreator.createLog(type, detail);
        logMapper.insertSelective(log);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return logMapper.deleteLogByIds(ids) > 0;
    }
}
