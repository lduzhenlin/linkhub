/*
 Navicat Premium Data Transfer

 Source Server         : 酷图云-自己
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : 154.201.71.223:3406
 Source Schema         : zeus_nav_dev

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 20/08/2025 18:18:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` bigint(0) NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录名',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '修改人',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记，0未删除，1已删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '所属租户ID',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1914935854955081729, '常用工具', '2025-04-23 14:54:20', NULL, '2025-06-11 13:25:57', '13345092258', '0', 1);
INSERT INTO `category` VALUES (1914936227547619330, '技术文档', '2025-04-23 14:55:49', NULL, '2025-06-11 13:26:00', ' ', '0', 1);
INSERT INTO `category` VALUES (1914939863753351170, '常用技术', '2025-04-23 15:10:16', NULL, '2025-06-11 13:25:54', ' ', '0', 2);
INSERT INTO `category` VALUES (1914964265773621250, '网络工具', '2025-04-23 16:47:14', '13345092258', '2025-08-20 15:50:04', '1', '0', 2);
INSERT INTO `category` VALUES (1931736404591718401, '111', '2025-06-08 23:33:43', '2', '2025-06-08 23:33:43', '2', '0', 4);

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link`  (
  `link_id` bigint(0) NOT NULL COMMENT 'ID',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `category_id` bigint(0) NULL DEFAULT NULL COMMENT '所属分类',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '修改人',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记，0未删除，1已删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '所属租户ID',
  `is_shared` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否共享 0：未共享  1：已共享  ',
  `shared_time` datetime(0) NULL DEFAULT NULL COMMENT '共享时间',
  `original_link_id` bigint(0) NULL DEFAULT NULL COMMENT '原始链接ID（用于复制模式）',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of link
-- ----------------------------
INSERT INTO `link` VALUES (1958091629219360769, '百度', 'http://www.baidu.com', '', NULL, 1914939863753351170, '2025-08-20 16:59:59', '1', '2025-08-20 16:59:59', '1', '0', 2, '0', NULL, NULL);
INSERT INTO `link` VALUES (1958091760018731010, 'bing', 'http://www.bing.com', '', NULL, 1914939863753351170, '2025-08-20 17:00:30', '1', '2025-08-20 17:00:30', '1', '0', 2, '0', NULL, NULL);
INSERT INTO `link` VALUES (1958096021385494530, '豆包', 'http://www.doubao.com', '222', NULL, 1914939863753351170, '2025-08-20 17:17:26', '1', '2025-08-20 17:28:54', '1', '0', 2, '1', '2025-08-20 17:28:36', 1958096021385494530);
INSERT INTO `link` VALUES (1958096102377504770, 'Gitee', 'http://www.gitee.com', '', NULL, 1914939863753351170, '2025-08-20 17:17:45', '1', '2025-08-20 17:28:41', '1', '0', 2, '0', '2025-08-20 17:18:47', 1958096102377504770);
INSERT INTO `link` VALUES (1958096216911364098, 'DeepSeek', 'http://www.deepseek.com', '', NULL, 1914939863753351170, '2025-08-20 17:18:12', '1', '2025-08-20 17:18:49', '1', '0', 2, '1', '2025-08-20 17:18:49', 1958096216911364098);

-- ----------------------------
-- Table structure for sys_file_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_config`;
CREATE TABLE `sys_file_config`  (
  `config_id` bigint unsigned NOT NULL COMMENT '主键ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `storage_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储类型 1:oss 2：local',
  `enable` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否开启',
  `endpoint` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象存储服务的URL',
  `access_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问key',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密钥key',
  `bucket_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认的存储桶名称',
  `dir` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录',
  `base_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '本地存储默认路径',
  `path_style_access` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT 'true path-style nginx 反向代理和S3默认支持 pathStyle {http://endpoint/bucketname} false\r\nsupports virtual-hosted-style 阿里云等需要配置为 virtual-hosted-style',
  `custom_domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义域名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file_config
-- ----------------------------
INSERT INTO `sys_file_config` VALUES (2, 'local', 'local', '0', NULL, NULL, NULL, 'images', '', '/upload', '0', NULL, NULL, NULL, '0', NULL, NULL, NULL);
INSERT INTO `sys_file_config` VALUES (1808498539561365505, 'ali', 'oss', '0', 'oss-cn-beijing.aliyuncs.com', 'LTAI5tHcvSEDz1x7cUzdPxP1', 'KVDWyoTwRdZWWJIDnPjXeSaCtnpmxT', 'zhenlin-test', 'nav', NULL, '0', NULL, NULL, NULL, '0', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_sms_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_config`;
CREATE TABLE `sys_sms_config`  (
  `config_id` bigint unsigned NOT NULL COMMENT '主键ID',
  `supplier` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '供应商名称',
  `region_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域',
  `sign_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信签名',
  `access_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问key',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密钥key',
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_sms_config
-- ----------------------------
INSERT INTO `sys_sms_config` VALUES (1808498539561365506, 'ali', 'cn-qingdao', '启善智能', 'LTAI5tH7BYEoWVzPJPMc8n8u', 'b82uuqf8vraqxSB7j0Iz8w4l2bydC3', 1, '2024-07-03 21:50:27', '2024-07-03 21:50:27', '0', 'admin', 'admin', 1);

-- ----------------------------
-- Table structure for sys_sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_log`;
CREATE TABLE `sys_sms_log`  (
  `log_id` bigint unsigned NOT NULL COMMENT '主键ID',
  `supplier` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储类型 1:oss 2：local',
  `region_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象存储服务的URL',
  `sign_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信签名',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `template_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信模板',
  `template_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板参数',
  `success` tinyint(1) NULL DEFAULT NULL COMMENT '是否成功',
  `reponse_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '响应码',
  `reponse_message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '响应信息',
  `biz_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务ID',
  `request_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `tenant_id` bigint(0) NULL DEFAULT NULL COMMENT '租户',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_sms_log
-- ----------------------------
INSERT INTO `sys_sms_log` VALUES (1931311058020929536, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_278275295', '{\"code\":\"1234\"}', 1, 'OK', '发送成功', '620103549295412573^0', 'C5386644-AB9E-5AC9-8542-F34469008F42', NULL, NULL, '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1931314631500832768, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_278275295', '{\"code\":\"1234\"}', 1, 'OK', '发送成功', '614305749296263641^0', '5D329B23-A382-5770-96F9-08A9E76F4BD4', '2025-06-07 19:37:45', '2025-06-07 19:37:46', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935591543651381248, 'ali', 'cn-qingdao', 'oss-cn-beijing.aliyuncs.com', '13345092258', 'SMS_278275295', '{\"code\":9142}', 0, 'isv.SMS_TEMPLATE_ILLEGAL', '该账号下找不到对应模板', NULL, 'D408808B-3A33-5EE4-8C21-A43C40358FEC', '2025-06-19 14:52:40', '2025-06-19 14:52:40', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935592089657458688, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_278275295', '{\"code\":5945}', 0, 'isv.SMS_TEMPLATE_ILLEGAL', '该账号下找不到对应模板', NULL, 'DE58C334-DCCA-546F-B6B7-F81A86B16356', '2025-06-19 14:54:50', '2025-06-19 14:54:50', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935596599628976128, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_489465138', '{\"code\":7637}', 0, 'isv.SMS_TEMPLATE_ILLEGAL', '该账号下找不到对应模板', NULL, 'EAD79842-852D-5B7C-B316-8ED58AE9CA51', '2025-06-19 15:12:46', '2025-06-19 15:12:46', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935598311739666432, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_278275295', '{\"code\":\"1234\"}', 0, 'isv.SMS_TEMPLATE_ILLEGAL', '该账号下找不到对应模板', NULL, 'C24085E2-64F8-56C8-8270-65BB302ED186', '2025-06-19 15:19:34', '2025-06-19 15:19:34', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935599682140438528, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_278275295', '{\"code\":\"1234\"}', 1, 'OK', '发送成功', '147920650317901112^0', '6B585252-3B4F-5C80-BA6D-3F03B67CEFD5', '2025-06-19 15:25:01', '2025-06-19 15:25:01', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935600020650160128, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_489465138', '{\"code\":9545}', 1, 'OK', '发送成功', '157306950317981861^0', 'ACD7F64A-D1DE-5C14-954C-65178A6B2E3B', '2025-06-19 15:26:21', '2025-06-19 15:26:21', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935620390501408768, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_489465138', '{\"code\":2999}', 1, 'OK', '发送成功', '564711650322838361^0', 'F6469EE7-7ED1-59C9-814F-77D477DAE5BD', '2025-06-19 16:47:18', '2025-06-19 16:47:18', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935620865401438208, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_489465138', '{\"code\":7932}', 1, 'OK', '发送成功', '225118150322951665^0', '21550D4C-16FC-5EC5-83C0-A8DD3008C350', '2025-06-19 16:49:11', '2025-06-19 16:49:11', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935636409848623104, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_489465138', '{\"code\":8397}', 1, 'OK', '发送成功', '924810950326657689^0', 'DB0D51A9-3497-5D1C-BD01-7C9EB3E03B0F', '2025-06-19 17:50:57', '2025-06-19 17:50:57', '0', NULL, NULL, NULL);
INSERT INTO `sys_sms_log` VALUES (1935677846602600448, 'ali', 'cn-qingdao', '启善智能', '13345092258', 'SMS_489465138', '{\"code\":1138}', 1, 'OK', '发送成功', '307814750336537027^0', 'ADAEE773-4827-570F-8BA7-4AD81749A638', '2025-06-19 20:35:36', '2025-06-19 20:35:36', '0', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '修改人',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记，0未删除，1已删除',
  `tenant_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '所属租户ID',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `user_idx1_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1920368384279457793, '13345092258', '$2a$10$f6PbGFhyPrInTfUCXz0.leXKifIC/e6CrFc69mxlNlVZg5DrUVnxq', '13345092258', NULL, NULL, '2025-05-08 06:41:27', '13345092251', '2025-06-19 12:38:04', ' ', '0', 1);
INSERT INTO `sys_user` VALUES (1931368019366273026, '1', '$2a$10$hRCi3zbiLXbAYp1glKpA7ewDucbUI6c/BiAqR1g0gR9.62s7gOl7.', '1', NULL, NULL, '2025-06-07 23:09:54', NULL, '2025-06-07 23:09:54', NULL, '0', 2);
INSERT INTO `sys_user` VALUES (1931736302825320450, '2', '$2a$10$zImk4PJLs7Sxo1/sYpjXN.ucskfsNHuzhZpiPHWMviXfMfsjXdPS6', '2', NULL, NULL, '2025-06-08 23:33:19', NULL, '2025-06-08 23:33:19', NULL, '0', 4);
INSERT INTO `sys_user` VALUES (1931737127022833666, '19563238538', '$2a$10$d1aJ1BBKWhM.6fU.oCFsDu0TPHfKJvXKKQwcQdPxm6I8XMtz0lbEi', '19563238538', NULL, NULL, '2025-06-08 23:36:36', '2', '2025-06-08 23:36:36', '2', '0', 5);

SET FOREIGN_KEY_CHECKS = 1;
