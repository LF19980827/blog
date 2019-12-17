package com.hanhan.blog.util;

import com.hanhan.blog.controller.admin.AdminController;
import com.hanhan.blog.entity.Log;
import java.util.Date;

public class LogCreator {
    public static Log createLog(String type, String detail) {
        Log log = new Log();
        log.setDetail(detail);
        log.setIp(AdminController.LoginIp);
        log.setTime(new Date());
        log.setType(type);
        return log;
    }
}
