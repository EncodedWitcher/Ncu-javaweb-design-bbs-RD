# BBS论坛系统

基于SpringBoot3 + MySQL + Vue3的BBS论坛系统后端项目。

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

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 数据库配置

1. 确保MySQL服务正在运行
2. 数据库连接信息已在 `application.properties` 中配置：
   ```properties
   spring.datasource.url=jdbc:mysql://117.72.215.67:13306/bbs_forum
   spring.datasource.username=root
   spring.datasource.password=S2pAR8m3MmX54iFL
   ```

### 启动项目

1. 克隆项目到本地
2. 进入项目根目录
3. 运行以下命令：

```bash
# 安装依赖
mvn clean install

# 启动项目
mvn spring-boot:run
```

或者在IDE中直接运行 `BbsRdApplication.java`

### 访问项目

- 项目启动后访问: http://localhost:8080
- API文档地址: http://localhost:8080/swagger-ui.html

## API接口

### 用户模块 (/api/users)

- `POST /register` - 用户注册
- `POST /login` - 用户登录
- `GET /profile` - 获取个人信息
- `PUT /profile` - 更新个人信息
- `POST /checkin` - 每日签到

### 帖子模块 (/api/posts)

- `GET /` - 获取帖子列表
- `GET /{id}` - 获取帖子详情
- `POST /` - 发布帖子
- `PUT /{id}` - 更新帖子
- `DELETE /{id}` - 删除帖子
- `POST /{id}/like` - 点赞帖子

### 评论模块 (/api/comments)

- `GET /post/{postId}` - 获取帖子评论
- `POST /` - 发布评论
- `PUT /{id}` - 更新评论
- `DELETE /{id}` - 删除评论
- `POST /{id}/like` - 点赞评论

### 板块模块 (/api/sections)

- `GET /` - 获取板块列表
- `GET /{id}` - 获取板块详情
- `POST /` - 创建板块 (管理员)
- `PUT /{id}` - 更新板块 (管理员)
- `DELETE /{id}` - 删除板块 (管理员)

### 管理员模块 (/api/admin)

- `PUT /posts/{id}/pin` - 置顶帖子
- `PUT /posts/{id}/feature` - 加精帖子
- `PUT /users/{id}/points` - 调整用户积分

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

## 测试

运行测试：

```bash
mvn test
```

## 部署

### 打包

```bash
mvn clean package
```

### 运行jar包

```bash
java -jar target/bbs-RD-0.0.1-SNAPSHOT.jar
```

## 贡献

欢迎提交Issue和Pull Request来改进项目。

## 许可证

本项目采用MIT许可证。
