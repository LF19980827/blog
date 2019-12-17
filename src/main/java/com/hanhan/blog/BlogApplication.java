package com.hanhan.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.hanhan.blog.dao")
@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
        System.out.println("***************************************************************************");
        System.out.println("***************************************************************************");
        System.out.println("**********************************启动成功**********************************");
        System.out.println("***************************************************************************");
        System.out.println("***************************************************************************");

    }

}
