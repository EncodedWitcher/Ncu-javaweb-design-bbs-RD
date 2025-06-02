# BBS论坛系统

基于SpringBoot3 + MySQL的BBS论坛系统后端项目。

## 项目概述

这是一个功能完整的BBS论坛系统，支持用户注册登录、发帖回复、板块管理、积分系统等功能。

### 技术栈

- **后端框架**: Spring Boot 3.5.0
- **数据库**: MySQL 8.0+
- **ORM框架**: MyBatis Plus 3.5.12
- **身份认证**: JWT
- **API文档**: SpringDoc OpenAPI 3
- **构建工具**: Maven
- **Java版本**: JDK 17

### 主要功能

- 用户注册、登录、个人资料管理
- 帖子发布、编辑、删除
- 评论回复（支持楼中楼）
- 板块管理
- 积分系统（签到、加精奖励等）
- 管理员功能（置顶、加精、积分调整）
- JWT身份认证
- 跨域支持

## 快速开始

### 容器部署

1. 使用docker file构建镜像bbs-rd:1.0.0
2. 创建docker网络bbs-app,并将MySQL容器加入bbs-app
   ```bash
   docker network create bbs-app
   ``` 
3. 使用以下命令创建容器
   ```bash
   docker=run -d --network bbs-app
   -p 8081:8080 \
   -e "SPRING_DATASOURCE_URL={数据库URL}" \ 
   -e "SPRING_DATASOURCE_USERNAME={数据库用户名}" \
   -e "SPRING_DATASOURCE_PASSWORD={数据库密码}" \
   --name bbs-rd \
   bbs-rd:1.0.0
   ```

## API接口

### 用户模块 (/api/users)

- `POST /register` - 用户注册
  - 请求体：`{ "username": "用户名", "password": "密码", "name": "姓名", "contact": "联系方式", "jobType": "职业类型", "jobLocation": "工作地点" }`
- `POST /login` - 用户登录
  - 请求体：`{ "username": "用户名", "password": "密码" }`
- `GET /profile` - 获取个人信息 (需要登录)
- `PUT /profile` - 更新个人信息 (需要登录)
  - 请求体：`{ "name": "姓名", "contact": "联系方式", "jobType": "职业类型", "jobLocation": "工作地点" }`
- `POST /checkin` - 每日签到 (需要登录)

### 帖子模块 (/api/posts)

- `GET /` - 获取帖子列表
  - 查询参数：`page`(页码，默认1), `size`(每页大小，默认10), `sectionId`(板块ID，可选)
- `GET /{id}` - 获取帖子详情
- `GET /user/{userId}` - 获取用户发布的帖子
  - 查询参数：`page`(页码，默认1), `size`(每页大小，默认10)
- `POST /` - 发布帖子 (需要登录)
  - 请求体：`{ "title": "标题", "content": "内容", "sectionId": 板块ID }`
- `PUT /{id}` - 更新帖子 (仅作者可操作)
  - 请求体：`{ "title": "标题", "content": "内容", "sectionId": 板块ID }`
- `DELETE /{id}` - 删除帖子 (仅作者可操作)
- `POST /{id}/like` - 点赞帖子 (需要登录)

### 评论模块 (/api/comments)

- `GET /post/{postId}` - 获取帖子评论
- `GET /parent/{parentCommentId}` - 获取子评论列表
- `POST /` - 发布评论 (需要登录)
  - 请求体：`{ "postId": 帖子ID, "content": "评论内容", "parentCommentId": 父评论ID(可选) }`
- `DELETE /{id}` - 删除评论 (仅作者和管理员可操作)
- `POST /{id}/like` - 点赞评论 (需要登录)

### 板块模块 (/api/sections)

- `GET /` - 获取板块列表
- `GET /{id}` - 获取板块详情
- `POST /` - 创建板块 (管理员权限)
  - 请求体：`{ "name": "板块名称", "description": "板块描述" }`
- `PUT /{id}` - 更新板块 (管理员权限)
  - 请求体：`{ "name": "板块名称", "description": "板块描述" }`
- `DELETE /{id}` - 删除板块 (管理员权限)

### 管理员模块 (/api/admin)

- `PUT /posts/{id}/pin` - 置顶帖子 (管理员权限)
  - 请求体：`{ "isPinned": 1或0 }`
- `PUT /posts/{id}/feature` - 加精帖子 (管理员权限)
  - 请求体：`{ "isFeatured": 1或0 }`
- `PUT /users/{id}/points` - 调整用户积分 (管理员权限)
  - 请求体：`{ "points": 积分变化量(正数为增加，负数为减少) }`

### API文档

项目集成了SpringDoc OpenAPI 3，启动项目后可以通过以下地址访问完整的API文档：

- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

API文档包含了所有接口的详细说明、参数格式、响应示例等信息。

## 项目结构

```
src/main/java/cn/byssted/bbs/bbsrd/
├── config/          # 配置类
├── controller/      # 控制器
├── service/         # 服务层
├── mapper/          # 数据访问层
├── entity/          # 实体类
├── dto/             # 数据传输对象
├── common/          # 通用类
├── exception/       # 异常处理
└── util/            # 工具类
```
## 系统架构设计

### 整体架构
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   前端 (Vue3)    │    │   后端 (Spring)  │    │   数据库 (MySQL) │
│                 │    │                 │    │                 │
│ - 用户界面       │◄──►│ - REST API      │◄──►│ - 用户数据       │
│ - 路由管理       │    │ - 业务逻辑       │    │ - 帖子数据       │
│ - 状态管理       │    │ - 数据访问       │    │ - 板块数据       │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

###  后端分层架构
```
┌─────────────────────────────────────────────────────────────┐
│                    Controller Layer                         │
│              (REST API 接口层)                              │
├─────────────────────────────────────────────────────────────┤
│                    Service Layer                            │
│                  (业务逻辑层)                                │
├─────────────────────────────────────────────────────────────┤
│                    Mapper Layer                             │
│                 (数据访问层)                                 │
├─────────────────────────────────────────────────────────────┤
│                    Entity Layer                             │
│                  (实体类层)                                  │
└─────────────────────────────────────────────────────────────┘
```
## 开发说明

### 认证机制

项目使用JWT进行身份认证，请求需要在Header中携带Token：

```
Authorization: Bearer <your-jwt-token>
```

### 权限控制

- 游客：只能浏览帖子和评论
- 普通用户：可以发帖、回复、点赞
- 管理员：拥有所有权限，可以管理板块、置顶加精帖子

### 积分系统

- 注册赠送50积分
- 每日签到+10积分
- 帖子加精+100积分
- 发布求助帖需要消耗积分
