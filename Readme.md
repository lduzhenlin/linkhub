## LinkHub 导航聚合平台

### 📖 项目简介

**LinkHub** 是一个基于 Spring Boot 3.x 构建的现代化导航聚合平台，专为技术人员、团队和企业设计。  
平台采用多租户架构，支持分类管理、链接管理、智能搜索、链接共享、排序管理等核心功能，为用户提供高效、美观、个性化的网址导航服务。

#### 🎯 项目由来
作为一名技术人员，平常收藏的技术资源较多，之前一直保存在浏览器标签中。  
浏览器的缺陷是无法跨设备同步，且现有的标签管理工具在协作、共享、个性化方面存在不足。  
因此决定开发一套功能简洁、体验优秀的导航平台，既能满足个人链接管理，又能支持团队共享和沉淀优质资源。

![](./doc/intro-images/1.png)

## ✨ 核心功能特性

### 🔗 智能链接管理
- **分类体系**: 支持多级分类结构，灵活组织收藏链接
- **标签系统**: 为链接添加标签，实现多维度检索和筛选
- **链接预览**: 自动获取网站图标和描述信息
- **排序管理**: 支持拖拽/排序保存接口，保持个性化展示顺序

### 🌐 链接共享生态
- **一键共享**: 将个人链接共享到公共区域，形成共享链接池
- **共享分类**: 专门的共享分类视图，集中展示所有公共链接
- **复制收藏**: 支持将共享链接一键复制到个人分类
- **来源追踪**: 记录链接的分享人、分享时间等基础信息
- **权限控制**: 基于登录状态控制共享与复制能力

### 🎨 个性化体验
- **主题切换**: 支持明暗主题，适配不同使用场景
- **布局定制**: 多种卡片布局、列表布局（前端模板中可扩展）
- **响应式设计**: 适配桌面端、平板和移动端

### 📦 文件与图标管理
- **多存储支持**: 支持本地存储及兼容 S3 协议的对象存储（如阿里云 OSS、腾讯云 COS、七牛云、MinIO 等）
- **统一文件接口**: 通过 `FileTemplate` 抽象封装文件上传、下载、删除等操作
- **图标自动获取**: 支持根据链接 URL 自动拉取站点图标并存储
- **图片类型校验**:  
  - 仅允许 **常用图片格式** 上传（如 png/jpg/jpeg/gif/bmp/webp/svg/ico）  
  - 对远程图标抓取和手动文件上传统一做格式校验，非图片文件将被拒绝保存

### 🔐 安全与权限
- **多租户架构**: 支持不同租户之间的数据隔离和权限控制
- **用户认证**: 基于 Sa-Token 的登录认证和会话管理
- **角色权限**: 预留细粒度权限控制扩展能力
- **敏感数据保护**: 支持对敏感数据进行加密存储（可结合业务场景扩展）

## 🏗️ 技术架构

### 核心技术栈
- **后端框架**: Spring Boot 3.4.3
- **Java 版本**: JDK 17 (LTS)
- **构建工具**: Maven 3.11.0
- **数据库**: MySQL 8.0+（集成 MyBatis-Plus）
- **缓存系统**: Redis 6.0+
- **认证与权限**: Sa-Token 1.37+
- **前端模板**: FreeMarker 3.0+（结合原生 HTML 模板）
- **CSS 框架**: Tailwind CSS 4.1.10
- **图标库**: Font Awesome 6.0.0

### 架构特点
- **模块化设计**: Maven 多模块结构，业务与基础能力解耦
- **多租户支持**: 数据访问层内置多租户能力，可按租户隔离数据
- **插件化扩展**: 文件存储、短信服务、安全认证等均以独立模块方式提供
- **云原生友好**: 基于 Spring Boot，可方便迁移到容器、K8s 等环境

## 📁 项目结构（当前仓库）

```text
linkhub/
├── zeus-admin/                    # 主应用模块（后台 + 前台模板）
│   ├── src/main/java/
│   │   └── com/qishanor/
│   │       ├── AdminApplication.java    # 主启动类
│   │       ├── controller/              # 控制器层
│   │       │   ├── CategoryController.java    # 分类管理
│   │       │   ├── FileController.java        # 文件管理（上传/下载）
│   │       │   ├── IndexController.java       # 首页控制器
│   │       │   ├── LinkController.java        # 链接与共享管理
│   │       │   ├── SmsController.java         # 短信服务
│   │       │   └── UserController.java        # 用户管理
│   │       ├── entity/                # 实体类
│   │       │   ├── Category.java            # 分类实体
│   │       │   ├── Link.java                # 链接实体
│   │       │   ├── SysUser.java             # 用户实体
│   │       │   └── vo/                      # 视图对象
│   │       ├── framework/             # 框架与通用配置
│   │       │   ├── ApplicationDataLoader.java # 应用数据加载
│   │       │   ├── CacheConfig.java          # 缓存配置
│   │       │   └── util/                     # 工具类（如图标下载、图片校验）
│   │       ├── mapper/                # 数据访问层
│   │       │   ├── CategoryMapper.java       # 分类数据访问
│   │       │   ├── LinkMapper.java           # 链接数据访问
│   │       │   └── SysUserMapper.java        # 用户数据访问
│   │       └── Service/               # 业务逻辑层
│   │           ├── CategoryService.java      # 分类服务
│   │           ├── LinkService.java          # 链接服务
│   │           ├── SysUserService.java       # 用户服务
│   │           └── impl/                     # 服务实现
│   └── src/main/resources/
│       ├── application.yml           # 应用配置
│       ├── templates/front/          # 前端页面模板
│       │   └── index.html           # 首页模板
│       └── static/                   # 静态资源
│           ├── css/                  # 样式文件
│           ├── js/                   # JavaScript 文件
│           └── image/                # 图片资源
├── zeus-common/                     # 公共基础模块
│   ├── zeus-common-bom/             # 依赖版本管理（BOM）
│   ├── zeus-common-core/            # 核心工具包
│   │   └── src/main/java/
│   │       └── com/qishanor/common/core/
│   │           ├── constant/         # 常量定义
│   │           ├── exception/        # 异常处理
│   │           └── util/             # 通用工具类
│   ├── zeus-common-data/            # 数据访问扩展模块
│   │   └── src/main/java/
│   │       └── com/qishanor/common/data/
│   │           ├── mybatis/          # MyBatis 配置
│   │           └── tenant/           # 多租户支持
│   ├── zeus-common-oss/             # 文件存储模块
│   │   └── src/main/java/
│   │       └── com/qishanor/common/file/
│   │           ├── engine/           # 存储引擎实现（本地 / OSS / S3 等）
│   │           └── repository/       # 存储配置
│   ├── zeus-common-security/        # 安全认证模块
│   │   └── src/main/java/
│   │       └── com/qishanor/common/security/
│   │           ├── SaTokenConfiguration.java  # Sa-Token 配置
│   │           └── StpInterfaceImpl.java      # 权限接口实现
│   └── zeus-common-sms/             # 短信服务模块
│       └── src/main/java/
│           └── com/qishanor/common/sms/
│               ├── engine/           # 短信引擎
│               ├── log/              # 短信日志
│               └── repository/       # 配置仓库
└── pom.xml                          # 父级 POM 文件
```

## 🚀 快速开始

### 环境要求
- **JDK**: 17+（推荐使用 LTS 版本）
- **Maven**: 3.11.0+
- **MySQL**: 8.0+（推荐 8.0.28+）
- **Redis**: 6.0+

### 安装步骤

#### 1. 克隆项目
```bash
git clone https://gitee.com/qishanor/zeus-nav.git
cd linkhub   # 或仓库实际目录名称
```

#### 2. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE zeus_nav_dev CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 导入初始数据
-- 使用 Navicat 或其他客户端导入 doc/zeus_nav_dev.sql 文件
```

#### 3. 应用配置
修改 `zeus-admin/src/main/resources/application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/zeus_nav_dev?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password

# 文件存储配置示例（本地存储）
file:
  storage:
    enable: true
    type: local           # local / oss / s3 等
    bucketName: images
    basePath: /upload
    # aliyun:
    #   endpoint: your_endpoint
    #   accessKey: your_access_key
    #   secretKey: your_secret_key
    #   bucketName: your_bucket
```

#### 4. 启动应用

- **默认端口**: 8008（可在配置文件中修改）
- **访问地址**: `http://localhost:8008`

登录后即可管理分类、链接和共享链接；未登录状态下可以访问公共共享链接列表。

## 🤝 贡献指南

### 贡献流程
- **Fork 项目**: Fork 本仓库到你的 Git 账号
- **创建分支**: 创建特性分支（`git checkout -b feature/xxx`）
- **提交更改**: 提交你的更改（`git commit -m 'feat: xxx'`）
- **推送分支**: 推送到远程仓库（`git push origin feature/xxx`）
- **创建 PR**: 在代码托管平台上创建 Pull Request 并描述你的更改

## 📄 开源协议

本项目采用 **GPL** 协议开源，详情请查看仓库中的 `LICENSE` 文件。

**LinkHub** —— 让网址导航更简单、更高效、更智能！ 🚀  
如果你觉得这个项目对你有帮助，欢迎点一个 ⭐ Star 支持一下。
