[TOC]

## 1、user(用户登陆信息表)

| 列名           | 含义 | 数据类型     | 备注 |
| -------------- | ---- | ------------ | ---- |
| user_id        |      | INT(11)      | 主键 |
| user_name      |      | VARCHAR(255) |      |
| user_pass      |      | VARCHAR(255) |      |
| user_nickname  |      | VARCHAR(255) |      |
| user_signature |      | VARCHAR(500) | 保留 |
| user_email     |      | VARCHAR(100) | 保留 |
| user_avatar    |      | VARCHAR(255) | 保留 |
| user_profile   |      | TEXT         | 保留 |

## 2、article（文章表）

| 列名                   | 含义 | 数据类型     | 备注 |
| ---------------------- | ---- | ------------ | ---- |
| article_id             |      | INT(11)      | 主键 |
| article_user_id        |      | INT(11)      |      |
| article_category_id    |      | INT(11)      |      |
| article_title          |      | VARCHAR(255) |      |
| article_content        |      | MEDIUMTEXT   |      |
| article_view_count     |      | BIGINT(20)   |      |
| article_comment_count  |      | INT(11)      |      |
| article_like_count     |      | INT(11)      |      |
| article_status         |      | TINYINT(4)   |      |
| article_enable_comment |      | TINYINT(4)   |      |
| article_update_time    |      | DATETIME     |      |
| article_create_time    |      | DATETIME     |      |

## 3、article_tag_ref（关系表）

| 列名       | 含义 | 数据类型 | 备注 |
| ---------- | ---- | -------- | ---- |
| article_id |      | INT(11)  | 主键 |
| tag_id     |      | INT(11)  | 主键 |

## 4、category（分类表）

| 列名          | 含义 | 数据类型    | 备注 |
| ------------- | ---- | ----------- | ---- |
| category_id   |      | INT(11)     | 主键 |
| category_name |      | VARCHAR(50) |      |

## 5、comment（评论回复表）

| 列名                 | 含义 | 数据类型 | 备注 |
| -------------------- | ---- | -------- | ---- |
| comment_id           |      |          | 主键 |
| comment_pid          |      |          |      |
| comment_article_id   |      |          |      |
| comment_author_name  |      |          |      |
| comment_author_email |      |          |      |
| comment_author_ip    |      |          |      |
| comment_content      |      |          |      |
| comment_create_time  |      |          |      |
| comment_status       |      |          |      |
| comment_like_count   |      |          |      |



## 6、log（日志表）

| 列名   | 含义 | 数据类型     | 备注 |
| ------ | ---- | ------------ | ---- |
| id     |      | INT(11)      | 主键 |
| time   |      | DATETIME     |      |
| type   |      | VARCHAR(255) |      |
| detail |      | VARCHAR(255) |      |
| ip     |      | VARCHAR(255) |      |
|        |      |              |      |



## 7、tag（标签表）

| 列名     | 含义 | 数据类型    | 备注 |
| -------- | ---- | ----------- | ---- |
| tag_id   |      | INT(11)     | 主键 |
| tag_name |      | VARCHAR(50) |      |

