[TOC]

## 1、user(用户登陆信息表)

| 列名           | 含义     | 数据类型     | 备注 |
| -------------- | -------- | ------------ | ---- |
| user_id        | 用户ID   | INT(11)      | 主键 |
| user_name      | 用户名   | VARCHAR(255) |      |
| user_pass      | 密码     | VARCHAR(255) |      |
| user_nickname  | 昵称     | VARCHAR(255) |      |
| user_signature | 签名     | VARCHAR(500) | 保留 |
| user_email     | 邮箱     | VARCHAR(100) | 保留 |
| user_avatar    | --       | VARCHAR(255) | 保留 |
| user_profile   | 个人简介 | TEXT         | 保留 |

## 2、article（文章表）

| 列名                   | 含义         | 数据类型     | 备注              |
| ---------------------- | ------------ | ------------ | ----------------- |
| article_id             | 文章ID       | INT(11)      | 主键              |
| article_user_id        | 作者名称     | INT(11)      |                   |
| article_category_id    | 分类ID       | INT(11)      |                   |
| article_title          | 文章标题     | VARCHAR(255) |                   |
| article_content        | 文章内容     | MEDIUMTEXT   |                   |
| article_view_count     | 访问量       | BIGINT(20)   |                   |
| article_comment_count  | 评论数       | INT(11)      |                   |
| article_like_count     | 点赞数       | INT(11)      |                   |
| article_status         | 文章状态     | TINYINT(4)   | 0 - 草稿 1 - 发布 |
| article_enable_comment | 是否允许评论 | TINYINT(4)   |                   |
| article_update_time    | 最新更新时间 | DATETIME     |                   |
| article_create_time    | 发布时间     | DATETIME     |                   |

## 3、article_tag_ref（关系表）

| 列名       | 含义   | 数据类型 | 备注 |
| ---------- | ------ | -------- | ---- |
| article_id | 文章ID | INT(11)  | 主键 |
| tag_id     | 标签ID | INT(11)  | 主键 |

## 4、category（分类表）

| 列名          | 含义     | 数据类型    | 备注 |
| ------------- | -------- | ----------- | ---- |
| category_id   | 分类ID   | INT(11)     | 主键 |
| category_name | 分类名称 | VARCHAR(50) |      |

## 5、comment（评论回复表）

| 列名                 | 含义       | 数据类型     | 备注                     |
| -------------------- | ---------- | ------------ | ------------------------ |
| comment_id           | 评论ID     | BIGINT(20)   | 主键                     |
| comment_pid          | 分类ID     | BIGINT(20)   |                          |
| comment_article_id   | 文章ID     | BIGINT(20)   |                          |
| comment_author_name  | 发布者名   | VARCHAR(50)  |                          |
| comment_author_email | 发布者邮箱 | VARCHAR(100) |                          |
| comment_author_ip    | 发布者ip   | VARCHAR(20)  | 保留                     |
| comment_content      | 发布内容   | VARCHAR(200) |                          |
| comment_create_time  | 发布时间   | DATETIME     |                          |
| comment_status       | 状态       | TINYINT(4)   | 0-未审核 1-已审核 2-回复 |
| comment_like_count   | 点赞数     | INT(11)      |                          |



## 6、log（日志表）

| 列名   | 含义     | 数据类型     | 备注 |
| ------ | -------- | ------------ | ---- |
| id     | 日志ID   | INT(11)      | 主键 |
| time   | 日志时间 | DATETIME     |      |
| type   | 日志类型 | VARCHAR(255) |      |
| detail | 日志内容 | VARCHAR(255) |      |
| ip     | 设备IP   | VARCHAR(255) |      |
|        |          |              |      |



## 7、tag（标签表）

| 列名     | 含义   | 数据类型    | 备注 |
| -------- | ------ | ----------- | ---- |
| tag_id   | 标签ID | INT(11)     | 主键 |
| tag_name | 标签名 | VARCHAR(50) |      |

