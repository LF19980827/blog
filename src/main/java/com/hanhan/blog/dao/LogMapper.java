package com.hanhan.blog.dao;

import com.hanhan.blog.entity.Log;

import java.util.List;

public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
    
    ///////

    int getLogCount();

    List<Log> getLogByStartAndLimit(Integer start, Integer limit);

    int deleteLogByIds(Integer[] tagIds);

}