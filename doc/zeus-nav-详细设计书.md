## 1. 概述

### 1.1 项目背景

LinkHub（`zeus-nav`）是一个面向个人与小团队的「高效导航集成平台」，提供个人书签管理与共享导航能力，主要目标包括：

- **个人书签管理**：支持按分类管理常用链接，便于快速访问。
- **共享链接浏览**：支持用户将链接共享出来，其他用户可以浏览和复制到自己的分类。
- **统一体验**：提供暗色/浅色主题、良好的 PC / 移动端体验。
- **账号与安全**：支持注册登录、短信验证码找回密码、多租户支持（通过租户上下文与缓存）。

### 1.2 范围与读者

- **本详细设计书范围**：覆盖当前仓库中的后端模块 `zeus-admin`、通用模块 `zeus-common` 以及前端模板 `front/index.html` 的设计说明。
- **目标读者**：
  - 项目开发人员（后端、前端）
  - 测试人员（编写测试用例与测试计划）
  - 运维人员（了解服务结构与关键配置）

### 1.3 名词术语

- **链接（Link）**：用户保存的书签，包含标题、URL、图标、描述等。
- **分类（Category）**：对链接进行分组的逻辑集合。
- **共享链接（Shared Link）**：被标记为共享的链接，对所有用户可见。
- **租户（Tenant）**：多租户场景下，用于区分不同组织/空间的数据隔离单位。

---

## 2. 总体架构设计

### 2.1 工程结构

- 顶级工程：`zeus-nav`（`pom.xml`，打包类型 `pom`）
- 子模块：
  - `zeus-admin`：Web 管理端 / 前端接口层
  - `zeus-common`：通用组件聚合模块
    - `zeus-common-bom`：版本管理 BOM
    - `zeus-common-core`：通用返回体、常量、工具类
    - `zeus-common-data`：数据访问、MyBatis 支撑、多租户
    - `zeus-common-oss`：文件存储抽象（文件引擎、OSS 支持）
    - `zeus-common-security`：安全相关自动配置（Sa-Token）
    - `zeus-common-sms`：短信发送、模板与日志

### 2.2 技术选型

- **后端**
  - Java 17
  - Spring Boot 3.4.x
  - Sa-Token（认证与会话）
  - MyBatis-Plus（ORM）
  - Hutool 工具库
- **前端**
  - 模板引擎：Thymeleaf
  - UI：Tailwind CSS + Font Awesome
  - JS：jQuery + 原生 JS
  - 消息提示：toastr

### 2.3 分层架构

- **展示层（View）**
  - `templates/front/index.html`：主页面，包含前端逻辑与 UI。
- **接口层（Controller）**
  - `IndexController`：根路径路由，返回主页面。
  - `UserController`：用户登录注册、退出、找回密码、登录状态。
  - `CategoryController`：分类 CRUD。
  - `LinkController`：链接 CRUD、共享/取消共享、共享链接查询、复制共享链接。
  - `SmsController`：注册/找回密码短信发送。
  - `FileController`：文件访问（图标、附件等）（如有需要可补充说明）。
- **业务层（Service）**
  - `CategoryService`、`LinkService`、`SysUserService` 及其实现类。
- **数据访问层（Mapper）**
  - `CategoryMapper`、`LinkMapper`、`SysUserMapper`。
- **通用基础层（Common / Framework）**
  - `zeus-common-*` 模块：核心工具、数据、多租户、OSS、短信、安全等。
  - `framework` 包下工具与配置：`DomainUtil`、`ProxyUrlToMultipartFile`、`VerifyCodeCacheUtil`、`CacheConfig` 等。

### 2.4 模块依赖关系（简述）

- `zeus-admin` 依赖 `zeus-common-core/data/oss/security/sms` 等模块。
- 各 `zeus-common-*` 模块之间低耦合，主要通过 `zeus-common-bom` 统一依赖版本。
- 控制器只依赖服务接口与公共组件，避免直接操作 Mapper。

---

## 3. 功能设计

### 3.1 首页与导航展示

- **页面**：`templates/front/index.html`
- **主要功能**：
  - 展示站点标题、搜索框、主题切换按钮。
  - 展示「共享链接」和用户自定义分类的导航菜单（PC 顶部导航 + 移动端侧滑菜单）。
  - 展示链接卡片（标题、图标、描述、共享状态等）。
  - 登录后显示悬浮按钮，用于添加书签和管理分类。
- **前端逻辑概要**：
  - 主题切换：操作 `html` 标签 class（`dark` / `light`），结果持久化在 `localStorage`。
  - 搜索：监听输入框回车与搜索图标点击，调用 `loadLinks(categoryId, keyword)`。
  - 分类加载：`loadCategories()` 拉取用户分类，固定拼接「共享链接」分类。
  - 链接加载：`loadLinks` / `loadSharedLinks` 调用后端接口，填充卡片区域。

### 3.2 用户与认证

#### 3.2.1 登录

- **接口**：`POST /api/user/login`
- **输入**：
  - `tel`：手机号或登录名。
  - `password`：密码（明文传输，依赖 HTTPS 保障安全）。
  - `remember`：记住登录状态（布尔）。
- **处理逻辑**：
  1. 根据 `username == tel` 或 `tel == tel` 查询用户。
  2. 使用 BCrypt 校验密码。
  3. 登录成功后：
     - 调用 `StpUtil.login(userId, remember)`。
     - 在 Session 中写入：`USER_ID`、`USER_NAME`、`TENANT_ID`、`USER_DETAIL`。
     - 拷贝生成 `SysUserVo`，设置 `token` 为当前登录 token。
  4. 返回 `R.ok(SysUserVo)` 或 `R.failed("用户名或密码错误")`。

#### 3.2.2 注册

- **接口**：`POST /api/user/register`
- **输入**：
  - `SysUser user`：包含手机号 `tel`、密码等。
  - `code`：短信验证码。
- **处理逻辑**：
  1. 校验手机号、密码、验证码非空。
  2. 调用 `VerifyCodeCacheUtil.isValidCode(tel, code)` 校验验证码。
  3. 调用 `sysUserService.saveSysUser(user)` 完成用户创建（含密码加密等）。

#### 3.2.3 退出登录

- **接口**：`POST /api/user/logout`
- **输入**：`userId`。
- **处理逻辑**：
  1. 校验 `userId` 非空。
  2. 调用 `StpUtil.logout(userId)`。
  3. 返回 `R.ok()`。

#### 3.2.4 登录状态检测

- **接口**：`GET /api/user/isLogin`
- **处理逻辑**：
  - 返回 `R.ok(StpUtil.isLogin())`。
  - 前端初始化时使用，用于控制 UI（登录按钮/用户信息/悬浮按钮）。

#### 3.2.5 找回密码

- **接口**：`POST /api/user/findPassword`
- **输入**：`phone`、`code`、`newPassword`。
- **处理逻辑**：
  1. 校验参数完整性。
  2. 调用 `VerifyCodeCacheUtil.isValidCode(phone, code)` 验证短信验证码。
  3. 根据手机号查找用户，使用 BCrypt 生成新密码并更新。
  4. 返回 `R.ok()` 或错误信息。

### 3.3 短信验证码

#### 3.3.1 注册验证码

- **接口**：`/api/sms/register`
- **输入**：`phone`。
- **处理逻辑**：
  1. 使用 `RandomUtil.randomInt(1000, 9999)` 生成 4 位验证码。
  2. 构造模板参数 `{"code": 验证码}`。
  3. 调用 `smsTemplate.send(phone, template_ForgotPassword, paramMap)` 发送短信。
  4. 发送成功后，通过 `VerifyCodeCacheUtil.put(phone, code)` 缓存验证码。
  5. 返回 `R.ok()` 或 `R.failed(错误信息)`。

#### 3.3.2 找回密码验证码

- **接口**：`/api/sms/forgotPassword`
- **逻辑**：与注册验证码流程一致，主要用于找回密码场景。

### 3.4 分类管理

#### 3.4.1 分类查询

- **接口**：`GET /api/category/list`
- **权限控制**：类级 `@SaCheckLogin`，必须登录。
- **逻辑**：
  - 调用 `categoryService.list()` 获取当前用户/租户下的分类列表。
  - 前端用于：
    - 顶部/侧边导航。
    - 书签编辑/新增时的分类下拉选择。
    - 分类管理弹框列表展示。

#### 3.4.2 新增分类

- **接口**：`POST /api/category`
- **输入**：`Category category`。
- **逻辑**：
  - （预留）可从 Session 中取得租户 ID 并设置到分类上。
  - 调用 `categoryService.save(category)`。

#### 3.4.3 编辑分类

- **接口**：`PUT /api/category`
- **输入**：`Category category`（包含 `categoryId` 与新名称）。
- **逻辑**：
  1. 根据 `categoryId` 查询 `dbCategory`。
  2. 若不存在，返回 `R.failed("数据不存在")`。
  3. 更新名称并 `updateById`。

#### 3.4.4 删除分类

- **接口**：`DELETE /api/category`
- **输入**：`Long categoryId`。
- **逻辑**：
  - 调用 `categoryService.removeById(categoryId)` 删除分类。

### 3.5 链接管理

#### 3.5.1 链接列表查询

- **接口**：`GET /api/link/list`
- **输入**：
  - `categoryId`：分类 ID，可选。
  - `linkTitle`：标题关键字，可选。
- **逻辑**：
  1. 判断 `StpUtil.isLogin()`。
  2. 使用 `Wrappers.lambdaQuery()` 构建查询：
     - 条件一：`categoryId` 匹配（若非空）。
     - 条件二：`title LIKE linkTitle`（若非空）。
  3. 未登录时追加条件：`isShared = "1"`。
  4. 查询结果遍历：
     - 若 `icon` 为空：设置 `iconUrl` 为默认图标地址。
     - 若 `icon` 非空：设置 `iconUrl` 为 `/api/file/{icon}`。
  5. 返回 `R.ok(links)`。

#### 3.5.2 按 ID 查询链接

- **接口**：`GET /api/link/getById`
- **输入**：`linkId`。
- **逻辑**：
  - 查询 `Link`，若 `icon` 非空，则设置 `iconUrl` 为 `/api/file/{icon}`。
  - 返回 `R.ok(link)`。

#### 3.5.3 新增链接

- **接口**：`POST /api/link`
- **权限**：`@SaCheckLogin`。
- **输入**：`Link link`。
- **逻辑**：
  1. 校验 `title` 与 `url` 非空。
  2. 若 `iconUrl` 以 `http` / `https` 开头：
     - 通过 `ProxyUrlToMultipartFile.convert(iconUrl)` 把远程图片转为 `MultipartFile`。
     - 使用 `fileTemplate.uploadFile(file)` 上传，得到存储 `filename`。
     - 设置 `link.setIcon(filename)`。
  3. 调用 `DomainUtil.ensureProtocol(link.getUrl())` 保证 URL 带协议。
  4. `linkService.save(link)`。

#### 3.5.4 编辑链接

- **接口**：`PUT /api/link`
- **权限**：`@SaCheckLogin`。
- **输入**：`Link link`。
- **逻辑**：
  1. 校验必填字段。
  2. 查询 `dbLink = linkService.getById(linkId)`，若不存在则失败。
  3. 根据新旧 URL 是否相同域名决定图标处理：
     - **相同域名**：
       - 若原来 `dbLink.icon` 为空，并且前端传入了新的 http/https 图标 URL，则抓取并上传。
     - **不同域名**：
       - 只要前端传入新的 http/https 图标 URL，则抓取并上传。
  4. 保证 URL 带协议。
  5. `linkService.updateById(link)`。

#### 3.5.5 删除链接

- **接口**：`DELETE /api/link`
- **权限**：`@SaCheckLogin`。
- **输入**：`Long linkId`。
- **逻辑**：
  - 查询 `Link`，若不存在则失败。
  - 调用 `linkService.removeById(link)`。

### 3.6 链接共享与复制

#### 3.6.1 共享 / 取消共享

- **接口**：
  - 共享：`POST /api/link/share`
  - 取消共享：`POST /api/link/unshare`
- **权限**：`@SaCheckLogin`。
- **输入**：`Long linkId`。
- **逻辑**：
  - 校验 linkId 非空。
  - 调用对应服务方法 `shareLink` / `unshareLink`。
  - 成功返回 `R.ok("共享成功" / "取消共享成功")`，失败返回错误信息。

#### 3.6.2 获取所有共享链接

- **接口**：`GET /api/link/shared`
- **逻辑**：
  1. 调用 `linkService.getSharedLinks()`。
  2. 与普通列表查询一样设置 `iconUrl`（默认图标或 `/api/file/{icon}`）。
  3. 返回 `R.ok(sharedLinks)`。

#### 3.6.3 复制共享链接到个人分类

- **接口**：`POST /api/link/copy`
- **权限**：`@SaCheckLogin`。
- **输入**：
  - `sharedLinkId`：共享链接 ID。
  - `targetCategoryId`：目标分类 ID。
- **逻辑**：
  1. 校验两个参数非空。
  2. 从 Sa-Token Session 中获取当前用户名 `currentUsername`。
  3. 调用 `linkService.copySharedLink(sharedLinkId, targetCategoryId, currentUsername)`：
     - 实现中需判断：若该链接已属于当前用户则返回失败。
  4. 根据结果返回成功或提示「该链接已经属于您，无需重复复制」。

---

## 4. 数据模型设计（概要）

> 具体字段类型、索引、约束可在数据库设计文档（如 `zeus_nav_dev.sql`）中进一步细化，这里列出主要逻辑字段。

### 4.1 用户表 `sys_user`

- **核心字段**：
  - `user_id`：主键。
  - `username`：用户名。
  - `tel`：手机号。
  - `password`：BCrypt 加密密码。
  - `tenant_id`：租户 ID。
  - 状态字段：启用/禁用。
  - 审计：创建时间、更新时间、创建人等。

### 4.2 分类表 `category`

- **核心字段**：
  - `category_id`：主键。
  - `name`：分类名称。
  - `user_id` 或 `tenant_id`：所属用户/租户。
  - 排序字段。
  - 审计字段。

### 4.3 链接表 `link`

- **核心字段**：
  - `link_id`：主键。
  - `category_id`：所属分类。
  - `title`：链接标题。
  - `url`：完整 URL（含协议）。
  - `description`：描述。
  - `icon`：存储在文件系统中的图标 key。
  - `is_shared`：是否共享标记。
  - `create_by`：创建人。
  - `shared_time`：共享时间。
  - 审计字段：创建时间、更新时间等。

### 4.4 短信与文件相关表（来自 `zeus-common-sms` / `zeus-common-oss`）

- **短信配置表**（示意）：
  - 渠道标识、accessKey、secret、签名、模板 ID 等。
- **短信日志表**（示意）：
  - 手机号、模板、参数、发送时间、返回码、返回消息等。
- **文件元数据表**（如适用）：
  - 文件 key、原始文件名、存储路径、大小、类型、上传人、上传时间等。

---

## 5. 非功能设计

### 5.1 安全性

- 使用 Sa-Token 管理登录状态，支持 token + session。
- 密码统一使用 BCrypt 存储，避免明文或可逆加密。
- 通过注解控制接口访问权限（`@SaCheckLogin` / `@SaIgnore`）。
- 短信验证码存储在缓存中，验证后可根据策略清理或过期。

### 5.2 可扩展性

- 通用能力提取为 `zeus-common-*` 模块，便于多个业务模块复用。
- Controller 只负责参数与响应封装，业务逻辑下沉到 Service。
- 数据访问统一走 MyBatis-Plus，便于增加字段和索引。
- 前端采用统一的 REST 接口风格，便于后续替换前端技术栈（如 React/Vue）。

### 5.3 性能与体验

- 链接与分类数据均通过 Ajax 加载，避免整页刷新。
- 默认图标减少无图场景，提高页面观感。
- 使用本地存储缓存登录用户信息和主题设置，提升用户体验。

---

## 6. 典型业务流程（文字说明）

### 6.1 登录流程

1. 用户在登录弹框输入手机号/用户名 + 密码，点击「登录」。
2. 前端调用 `POST /api/user/login`。
3. 后端校验用户与密码，成功后通过 Sa-Token 建立会话并返回 `SysUserVo`。
4. 前端将返回信息写入 `localStorage`，更新页面为登录状态（显示用户名与悬浮按钮），并重新加载分类与链接。

### 6.2 注册与验证码流程

1. 用户在注册弹框输入手机号，点击「发送验证码」。
2. 前端调用 `/api/sms/register`，后端发送短信并缓存验证码，同时前端按钮进入倒计时状态。
3. 用户收到短信后输入验证码与密码，点击「注册」。
4. 前端调用 `POST /api/user/register`，后端校验验证码并创建用户。
5. 注册成功后关闭注册弹框并打开登录弹框。

### 6.3 找回密码流程

1. 用户在登录弹框点击「忘记密码」，打开找回密码弹框。
2. 输入手机号并点击发送验证码，调用 `/api/sms/forgotPassword`。
3. 输入验证码与新密码后，调用 `POST /api/user/findPassword`。
4. 后端校验验证码并更新密码，返回成功。
5. 前端关闭找回密码弹框并打开登录弹框。

### 6.4 链接共享与复制流程

1. 用户在自己的链接卡片上点击「共享」按钮，前端调用 `POST /api/link/share`。
2. 服务端更新 `isShared` 标记及共享时间。
3. 所有用户在「共享链接」分类下通过 `GET /api/link/shared` 能看到该链接。
4. 其他登录用户在共享链接卡片点击「复制到我的分类」：
   - 前端先调用 `/api/category/list` 拉取当前用户分类，弹出选择分类弹框。
   - 用户选择目标分类后，前端调用 `POST /api/link/copy`。
   - 后端校验是否已存在属于当前用户的相同链接，若不存在则复制，成功后提示「复制成功」。

---

## 7. 后续扩展点（预留）

### 7.1 角色与权限

- 在现有用户系统基础上增加角色（如普通用户、管理员），配合 Sa-Token 或 Spring Security 做细粒度权限控制。

### 7.2 多租户完善

- 基于 `TenantContextHolder` 与 `CacheConstant.TENANT_ID`，在数据访问层增加租户过滤（如 MyBatis 拦截器）。

### 7.3 审计与操作日志

- 对关键操作（登录、注册、共享、复制链接等）增加操作日志记录，便于审计与问题排查。


