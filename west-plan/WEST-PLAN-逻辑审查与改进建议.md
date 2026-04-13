# WESTPLAN 逻辑审查与改进建议

## 1. 审查范围与方法

- 审查范围：`westplan` 聚合工程（`ruoyi-admin`、`ruoyi-framework`、`ruoyi-system`、`ruoyi-quartz`、`ruoyi-generator`、配置与 Mapper）。
- 审查方式：
	- 核心链路静态审查（登录鉴权、评论/留言、微信登录、定时任务、代码生成）。
	- 配置与 SQL 风险点审查（敏感信息、动态 SQL、权限边界）。
	- 编译级验证：在 `westplan` 目录执行 `mvn -DskipTests -pl ruoyi-admin -am compile -q`，返回码 `LASTEXITCODE=0`。
- 审查结论：当前代码可编译，但存在多处“可运行但高风险/易出错”的逻辑与安全缺口，建议分阶段整改。

## 1.1 本次整改执行结果（2026-04-12）

- 基线提交：`747678e`（先提交后修复）。
- 第一阶段（止血）已完成：
	- 已修复 `WxLoginController` 中字符串 `==` 比较问题，改为 `equals`。
	- 已修复 `UserMessageMapper.xml` 中 `send_user_id` 列名错误为 `user_id`。
	- 已为 `MessageController` / `UserMessageController` 删除逻辑增加空值保护。
	- 已为 `MessageController` 的 `admin/list` 增加权限注解。
	- 已将数据库、微信、Token、Druid 控制台口令改为环境变量注入形式。
- 第一阶段复审：
	- 后端编译通过：`mvn -DskipTests compile`。
	- 关键风险点复查通过（字符串比较、列名错误、空指针路径）。

- 第二阶段（一致性）已完成：
	- 已修复评论父子关联 SQL：父评论用户改为“先关联父评论再关联父评论作者”。
	- 已补齐评论查询映射字段：`user_name`、`parent_user_name`、`avatar`。
	- 已补齐 `deleteCommentByParentIds` 的 Mapper SQL。
	- 已实现 `ForumCommentServiceImpl` 中原空实现方法。
	- 已修复评论删除计数漂移：按 `deleteCount` 次数扣减 `comment_count`。
- 第二阶段复审：
	- 后端编译通过；ForumComment 相关文件无编译/语义错误。

- 第三阶段（质量基线）已完成：
	- 新增测试：
		- `ruoyi-admin/src/test/java/com/ruoyi/web/controller/system/WxLoginControllerTest.java`
		- `ruoyi-admin/src/test/java/com/ruoyi/web/controller/system/MessageControllersTest.java`
		- `ruoyi-system/src/test/java/com/ruoyi/system/service/impl/ForumCommentServiceImplTest.java`
	- 新增测试依赖：`junit`、`mockito`、`byte-buddy`（test scope）。
	- 已完成动态列名风险加固：`ForumPostServiceImpl` 增加列名白名单校验。
- 第三阶段复审：
	- `mvn -pl ruoyi-system -am test` 通过。
	- `mvn -pl ruoyi-admin -am test` 通过。

## 1.2 当前剩余风险（已降级）

- 配置安全依赖部署规范：虽然代码已改为环境变量注入，但若运行环境未覆盖 `TOKEN_SECRET`、`DB_PASSWORD`、`WX_MP_APP_SECRET`、`DRUID_LOGIN_PASSWORD`，仍存在弱配置风险。

## 1.3 新增后端功能补齐（发帖/审核链路）

- 已补齐并修复发帖与审核后端关键能力：
	- 发帖前置校验：板块存在且启用、标题与内容非空。
	- 审核校验：仅允许状态 `1/2`，驳回必须填写备注。
	- 批量审核：由 Service 事务批处理，替换 Controller 层循环覆盖返回值的错误实现。
	- 帖子详情互动状态：改为从 `common_interact` 正确查询 `is_liked/is_collected`。
	- 帖子列表数据权限条件：修复 `where ... and ... or ...` 的条件歧义，避免查询结果越权/失真。
	- 互动计数字段安全：补充列名白名单校验，防止动态列误用。
	- 板块删除单条接口：补全 `deleteForumBoardById` 未实现逻辑并增加“有帖子不可删”校验。

- 本次补齐涉及文件：
	- `ruoyi-system/src/main/java/com/ruoyi/system/service/IForumPostService.java`
	- `ruoyi-system/src/main/java/com/ruoyi/system/service/impl/ForumPostServiceImpl.java`
	- `ruoyi-system/src/main/java/com/ruoyi/system/service/impl/ForumBoardServiceImpl.java`
	- `ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/ForumPostController.java`
	- `ruoyi-system/src/main/resources/mapper/system/ForumPostMapper.xml`

- 回归验证结果：
	- `mvn -DskipTests compile` 通过。
	- `mvn -pl ruoyi-system -am test` 通过。
	- `mvn -pl ruoyi-admin -am test` 通过。

## 1.4 持续迭代补齐（论坛后端完善）

- 审核状态机增强：
	- 仅允许待审核(0)/已驳回(2)进入审核流程，已通过帖子不允许重复审核。
	- 审核通过时清空 `auditRemark`，避免历史驳回意见污染通过结果。

- 审核接口与参数约束增强：
	- 单条审核接口入参统一为 `ForumAudit`，与批量审核一致。
	- `ForumAudit.auditStatus` 增加 `@NotNull` 校验并在 Controller 层启用 `@Validated`。

- 板块查询可用性修复：
	- 修复 `ForumBoardMapper.xml` 中 `selectByPrimaryKey` 的重复 `from forum_board` 问题，避免运行时 SQL 语法错误。

- 本轮影响文件：
	- `ruoyi-system/src/main/java/com/ruoyi/system/service/impl/ForumPostServiceImpl.java`
	- `ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/ForumPostController.java`
	- `ruoyi-system/src/main/java/com/ruoyi/system/domain/dto/ForumAudit.java`
	- `ruoyi-system/src/main/resources/mapper/system/ForumBoardMapper.xml`

- 本轮回归：
	- `mvn -DskipTests compile` 通过。
	- `mvn -pl ruoyi-system -am test` 通过。
	- `mvn -pl ruoyi-admin -am test` 通过。

## 1.5 下一轮迭代（幂等与并发安全）

- 接口幂等保护：
	- 已为发帖、编辑、单条审核、批量审核接口增加 `@RepeatSubmit` 防重提交拦截。

- 审核并发保护：
	- 新增 Mapper 并发安全更新方法 `updateAuditStatus`，仅允许 `audit_status in (0,2)` 的记录更新，避免并发覆盖。
	- Service 层在并发冲突时返回明确错误：`帖子状态已变更，请刷新后重试`。

- 审核日志细化：
	- 审核时记录操作人、帖子ID、审核前状态、审核后状态、审核备注。
	- 批量审核记录开始与结束日志（操作者、条数、目标状态、成功数）。

- 本轮影响文件：
	- `ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/ForumPostController.java`
	- `ruoyi-system/src/main/java/com/ruoyi/system/service/impl/ForumPostServiceImpl.java`
	- `ruoyi-system/src/main/java/com/ruoyi/system/mapper/ForumPostMapper.java`
	- `ruoyi-system/src/main/resources/mapper/system/ForumPostMapper.xml`

- 本轮回归：
	- `mvn -DskipTests compile` 通过。
	- `mvn -pl ruoyi-system -am test` 通过。
	- `mvn -pl ruoyi-admin -am test` 通过。

## 1.6 本轮彻底完善（权限收口与上传链路修复）

- 权限标识收口：
	- `MessageController` 管理接口权限统一改为留言域优先，并兼容历史权限：`@ss.hasAnyPermi('system:message:reply,system:alumni_honor:reply')`。
	- 价值：避免继续复用“校友荣誉”权限语义，同时保障历史角色未迁移期间不发生功能中断。

- 审核接口契约增强：
	- 单条审核接口新增 `id` 判空前置校验，空参数直接返回明确业务错误。
	- 批量审核接口新增 `ids` 非空校验，避免无效请求进入服务层。

- 论坛图片上传链路修复：
	- 三套前端（`ruoyi-ui`、`forumui`、`server-ui`）的论坛发帖/编辑页面上传地址，从 `/system/alumni_honor/uploadImage` 统一修正为 `/system/forum_post/uploadImage`。
	- 价值：避免发帖图片上传错误落到无关业务控制器，消除前后端接口错位。

- 本轮影响文件：
	- `westplan/ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/MessageController.java`
	- `westplan/ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/ForumPostController.java`
	- `westplan/ruoyi-ui/src/views/system/forum_post/add.vue`
	- `westplan/ruoyi-ui/src/views/system/forum_post/postEdit.vue`
	- `forumui/src/views/system/forum_post/add.vue`
	- `forumui/src/views/system/forum_post/postEdit.vue`
	- `server-ui/src/views/system/forum_post/add.vue`
	- `server-ui/src/views/system/forum_post/postEdit.vue`

## 1.7 权限迁移落地包（菜单/角色配置收口）

- 迁移脚本：
	- `westplan/sql/permission_migration_20260413.sql`

- 脚本内容能力：
	- 迁移前自动备份受影响 `sys_menu` 记录到 `sys_menu_perm_mig_20260413_bak`（幂等）。
	- 将历史权限 `system:alumni_honor:reply` 统一更新为 `system:message:reply`。
	- 提供迁移后校验查询（旧权限残留/新权限生效）。
	- 提供可选回滚 SQL（按 menu_id 从备份恢复）。

- 执行顺序建议：
	1. 先发布当前后端代码（已兼容新旧权限）。
	2. 在数据库执行 `permission_migration_20260413.sql`。
	3. 清理缓存并重新登录验证权限按钮与管理接口。
	4. 观察稳定后，再移除后端兼容中的旧权限常量。

## 1.8 最终收口完成（权限兼容移除）

- 前置条件：已完成 `permission_migration_20260413.sql` 的数据库执行与校验。
- 收口动作：
	- `MessageController` 从 `hasAnyPermi` 兼容模式切换为仅使用 `system:message:reply`。
	- 代码中已不再依赖 `system:alumni_honor:reply` 的运行时权限判断。
- 当前结论：留言管理权限已完成“数据迁移 + 代码收口”闭环。

---

## 2. 关键问题清单（按严重级别）

## P0（高优先级，建议立即处理）

1) 敏感信息与默认口令明文落库到配置
- 证据：
	- `ruoyi-admin/src/main/resources/application.yml:24`（`wx.mp.appSecret`）
	- `ruoyi-admin/src/main/resources/application.yml:107`（`token.secret`）
	- `ruoyi-admin/src/main/resources/application-druid.yml:11`（数据库密码 `123456`）
	- `ruoyi-admin/src/main/resources/application-druid.yml:50-51`（Druid 控制台默认口令）
- 风险：配置泄露后可直接导致账号接管、接口伪造、数据库未授权访问。
- 建议：改为环境变量/密钥中心注入；不同环境使用不同密钥；立即轮换已暴露凭据。

2) 微信登录禁用校验使用 `==` 比较字符串
- 证据：`ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/WxLoginController.java:97`
	- `sysUser.getStatus() == "0"`
- 风险：Java 字符串引用比较可能导致禁用用户绕过校验。
- 建议：改为 `"0".equals(sysUser.getStatus())`。

3) 用户留言查询 SQL 字段疑似写错（可能直接报 SQL 异常）
- 证据：`ruoyi-system/src/main/resources/mapper/system/UserMessageMapper.xml:89`
	- `WHERE send_user_id=#{userId}`
	- 同文件插入和其他查询均使用 `user_id`。
- 风险：查询“我发出的留言”时可能报 Unknown column 或返回错误数据。
- 建议：核对表结构后统一为正确列名（大概率应为 `user_id`）。

## P1（中优先级，建议本迭代修复）

4) 删除留言接口存在空指针风险
- 证据：
	- `ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/MessageController.java:71-72`
	- `ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/UserMessageController.java:88-89`
- 问题：查询结果可能为 `null`，随后直接 `getUserId()`。
- 风险：请求不存在 ID 时出现 500，影响稳定性与接口一致性。
- 建议：先判空并返回 404/业务错误码。

5) 评论树删除后，帖子评论数只减 1，可能与实际删除数量不一致
- 证据：`ruoyi-system/src/main/java/com/ruoyi/system/service/impl/ForumCommentServiceImpl.java:168-171`
	- 先递归收集并批量删除多条评论，再只执行一次 `decrementCount(..., "comment_count")`。
- 风险：帖子 `comment_count` 长期漂移，前端展示和统计失真。
- 建议：按 `deleteCount` 扣减，或重算回写。

6) 评论关联与展示字段映射不一致，可能导致“父评论人/评论人”显示异常
- 证据：
	- `ruoyi-system/src/main/resources/mapper/system/ForumCommentMapper.xml:101,109`
		- `pu.nick_name as parent_nick`，但服务层使用 `comment.getParentUserName()`。
	- `ruoyi-system/src/main/java/com/ruoyi/system/service/impl/ForumCommentServiceImpl.java:244-246`
- 额外风险：SQL 中 `c.parent_id = pu.user_id` 将评论主键与用户主键关联，语义上不合理。
- 建议：
	- 先通过父评论 ID 关联父评论，再关联父评论作者。
	- 别名改为与实体一致（如 `user_name`、`parent_user_name`）或完善 `resultMap` 映射。

7) 论坛评论 Service 中多个接口为“空实现”
- 证据：`ruoyi-system/src/main/java/com/ruoyi/system/service/impl/ForumCommentServiceImpl.java:128,133,138,143,148`
	- 多个方法直接 `return null` 或 `return 0`。
- 风险：后续若复用通用 CRUD 接口，会出现静默失败或空返回。
- 建议：要么完整实现，要么删除接口暴露并显式抛 `UnsupportedOperationException`。

8) “管理员列表”接口缺少权限注解
- 证据：
	- `ruoyi-admin/src/main/java/com/ruoyi/web/controller/system/MessageController.java:27`（`/admin/list`）
	- 同文件 `admin/reply` 有 `@PreAuthorize`，权限策略不一致。
- 风险：已登录普通用户可能访问管理视图（取决于前端路由与后端网关约束）。
- 建议：为 `admin/list` 增加明确权限控制。

## P2（优化项）

9) 动态列名 `${column}` 直拼，存在注入面
- 证据：`ruoyi-system/src/main/resources/mapper/system/ForumPostMapper.xml:252,256`
- 说明：当前若只从服务层常量传参，短期可控；但接口扩展后容易被误用。
- 建议：改为白名单枚举映射（后端仅允许 `like_count`/`favorite_count`/`comment_count` 等固定值）。

10) 缺少自动化测试基线
- 证据：`westplan/**/src/test/**` 未发现测试文件。
- 风险：上述逻辑问题在重构和回归中难以及早发现。
- 建议：至少补齐登录、留言删除、评论树删除计数、微信登录绑定流程的集成测试。

---

## 3. 分阶段改进方案

## 第一阶段（1-2天，止血）

- 修复 `WxLoginController` 字符串比较。
- 修复 `UserMessageMapper.xml` 列名问题并联调列表接口。
- 给留言删除接口加空值校验，统一返回业务错误码。
- 为 `MessageController` 的 `admin/list` 补权限注解。
- 下线明文密钥：改环境变量并轮换旧密钥。

## 第二阶段（3-5天，消除数据一致性风险）

- 修复评论父子关联 SQL 与展示字段映射。
- 修复评论删除计数逻辑（按删除条数扣减或重算）。
- 清理 `ForumCommentServiceImpl` 空实现（实现或显式禁用）。

## 第三阶段（持续，工程质量）

- 建立最低限度测试集：
	- 登录/鉴权
	- 微信绑定与登录
	- 留言与删除权限
	- 评论树删除与计数一致性
- 建立配置安全基线（凭据扫描、配置模板分层、启动自检）。

---

## 4. 建议的验收清单

- 功能验收：
	- 禁用用户无法通过微信登录。
	- “我发出的留言”接口查询稳定且数据正确。
	- 删除不存在留言返回明确业务错误，不再 500。
	- 删除有子评论的根评论后，`comment_count` 与数据库实际条数一致。
- 安全验收：
	- 仓库和部署配置中无明文密钥/默认口令。
	- 管理接口具备明确权限控制。
- 质量验收：
	- 新增用例通过并纳入 CI。

---

## 5. 说明

本报告基于当前代码与配置静态审查及编译验证结果，不替代生产环境压测、渗透测试和全链路联调。建议在修复后进行一次回归测试与安全复核。
