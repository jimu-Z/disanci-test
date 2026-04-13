# WEST-PLAN 运行说明

本项目基于 RuoYi-Vue（Spring Boot + Vue2），已结合西部计划业务做定制开发。

## 1. 项目结构

- westplan：后端 Maven 多模块工程
- westplan/ruoyi-admin：后端启动模块
- westplan/ruoyi-ui：后台前端（Vue2）
- forumui：论坛与业务前端（Vue2，当前主要维护）
- westplan/sql：数据库脚本目录

## 2. 环境要求

- JDK 1.8
- Maven 3.6+
- MySQL 8.x（或兼容版本）
- Redis 6.x（本地默认 6379，无密码）
- Node.js 16+（建议 LTS）
- npm 8+

## 3. 数据库初始化

1. 创建数据库：westplan（utf8mb4）
2. 按需导入脚本（位于 westplan/sql）：
   - ry_20250522.sql
   - quartz.sql
   - friend_system.sql
   - permission_migration_20260413.sql
3. 修改数据库连接：
   - westplan/ruoyi-admin/src/main/resources/application-druid.yml

当前默认示例为本地：
- url: jdbc:mysql://localhost:3306/westplan ...
- username: root
- password: 123456

建议改为你本机实际账号，生产环境禁止使用明文默认密码。

## 4. 后端启动

后端默认端口为 8081，配置文件：
- westplan/ruoyi-admin/src/main/resources/application.yml

### 方式 A：命令行启动（推荐开发期）

在 westplan 目录执行：

```bash
mvn clean package -DskipTests
cd ruoyi-admin/target
java -jar ruoyi-admin.jar
```

### 方式 B：Windows 脚本启动

在 westplan/bin 目录执行：

```bat
run.bat
```

打包脚本：

```bat
package.bat
```

## 5. 前端启动

本仓库有两套前端，按需选择其一。

### 5.1 forumui（推荐）

开发端口默认 80，后端代理目标为 http://127.0.0.1:8081/。

```bash
cd forumui
npm install
npm run dev
```

生产构建：

```bash
npm run build:prod
```

### 5.2 ruoyi-ui

开发端口默认 8080，后端代理目标为 http://localhost:8081。

```bash
cd westplan/ruoyi-ui
npm install
npm run dev
```

## 6. 接口冒烟检查（forumui）

forumui 已提供最小化接口冒烟脚本：
- forumui/scripts/smoke-api-check.js

执行示例：

```bash
cd forumui
npm run smoke:api -- http://127.0.0.1:8080
```

带登录 token 的示例：

```bash
npm run smoke:api -- http://127.0.0.1:8080 <token>
```

说明：
- 未传 token 时，鉴权接口会自动跳过
- 若 captcha 返回 ERR，通常是后端未启动、地址不通或跨环境端口不一致

## 7. 常见问题

1. 前端能启动但接口报错
- 检查后端是否运行在 8081
- 检查前端 vue.config.js 中 baseUrl 是否和后端一致

2. 登录失败或 401
- 检查 token 配置（application.yml 中 token.secret）
- 检查系统时间是否异常

3. 上传失败
- 检查 application.yml 中 ruoyi.profile 目录是否存在且有写权限

4. Redis 连接失败
- 检查本地 Redis 是否启动
- 检查 application.yml 中 spring.redis 配置

## 8. 维护建议

- 优先维护 forumui 作为业务主前端
- 每次合并前至少执行一轮 smoke:api
- 数据库与密钥配置统一使用环境变量，不要在仓库中保留明文敏感信息
