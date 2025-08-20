# 共享链接功能 API 文档

## 概述
本文档描述了标签共享功能的API接口，采用复制模式实现，用户可以将个人标签共享到公共区域，其他用户可以复制到自己的分类中。

## 新增字段说明

### Link 实体新增字段
- `isShared`: 是否共享 (1: 已共享, 0: 未共享)
- `sharedTime`: 共享时间
- `originalLinkId`: 原始链接ID（用于复制模式）

## API 接口

### 1. 共享链接
**接口地址**: `POST /api/link/share`

**请求参数**:
```json
{
  "linkId": "123"
}
```

**响应示例**:
```json
{
  "code": 0,
  "msg": "共享成功"
}
```

### 2. 取消共享链接
**接口地址**: `POST /api/link/unshare`

**请求参数**:
```json
{
  "linkId": "123"
}
```

**响应示例**:
```json
{
  "code": 0,
  "msg": "取消共享成功"
}
```

### 3. 获取所有共享链接
**接口地址**: `GET /api/link/shared`

**响应示例**:
```json
{
  "code": 0,
  "data": [
    {
      "linkId": "123",
      "title": "示例链接",
      "url": "https://example.com",
      "description": "这是一个示例链接",
      "icon": "icon.png",
      "categoryId": "456",
      "isShared": "1",
      "sharedTime": "2024-01-01T10:00:00",
      "originalLinkId": "123",
      "createBy": "user123",
      "iconUrl": "/api/file/icon.png"
    }
  ]
}
```

### 4. 复制共享链接到个人分类
**接口地址**: `POST /api/link/copy`

**请求参数**:
```json
{
  "sharedLinkId": "123",
  "targetCategoryId": "789"
}
```

**响应示例**:
```json
{
  "code": 0,
  "msg": "复制成功"
}
```

## 前端功能说明

### 1. 共享分类
- 在导航栏中显示"共享链接"分类（写死在前端）
- 点击后显示所有用户共享的链接
- 共享分类不显示编辑/删除按钮，只显示复制按钮

### 2. 个人链接共享
- 在每个个人链接卡片上添加共享/取消共享按钮
- 已共享的链接显示绿色"已共享"标识
- 共享状态实时更新

### 3. 复制功能
- 点击复制按钮弹出分类选择对话框
- 选择目标分类后复制链接到个人分类
- 复制的链接默认不共享

## 数据库变更

执行以下SQL脚本添加新字段：
```sql
-- 添加是否共享字段
ALTER TABLE link ADD COLUMN is_shared VARCHAR(1) DEFAULT '0' COMMENT '是否共享 1：已共享 0：未共享';

-- 添加共享时间字段
ALTER TABLE link ADD COLUMN shared_time DATETIME NULL COMMENT '共享时间';

-- 添加原始链接ID字段
ALTER TABLE link ADD COLUMN original_link_id BIGINT NULL COMMENT '原始链接ID（用于复制模式）';

-- 创建索引
CREATE INDEX idx_link_is_shared ON link(is_shared);
CREATE INDEX idx_link_shared_time ON link(shared_time);
CREATE INDEX idx_link_original_link_id ON link(original_link_id);
```

## 注意事项

1. 共享功能需要用户登录
2. 共享的链接会显示创建者信息
3. 复制的链接与原链接独立，修改不会相互影响
4. 共享分类只有一个，没有子分类
5. 只有标签有分享功能，分类没有分享功能 