/*
 Navicat Premium Dump SQL

 Source Server         : 酷兔云-2025
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : 154.201.71.223:3406
 Source Schema         : zeus_nav_dev

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 01/06/2025 21:54:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` bigint NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目录名',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '修改人',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记，0未删除，1已删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '所属租户ID',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1914935854955081729, '常用工具', '2025-04-23 14:54:20', NULL, '2025-05-27 13:08:37', '13345092258', '0', 1);
INSERT INTO `category` VALUES (1914936227547619330, '技术文档', '2025-04-23 14:55:49', NULL, '2025-05-27 13:08:39', ' ', '1', 1);
INSERT INTO `category` VALUES (1914939863753351170, '常用技术', '2025-04-23 15:10:16', NULL, '2025-05-27 13:08:40', ' ', '0', 1);
INSERT INTO `category` VALUES (1914964265773621250, '网路工具', '2025-04-23 16:47:14', '13345092258', '2025-05-27 13:08:42', '13345092258', '0', 1);

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link`  (
  `link_id` bigint NOT NULL COMMENT 'ID',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `category_id` bigint NULL DEFAULT NULL COMMENT '所属分类',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '修改人',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记，0未删除，1已删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '所属租户ID',
  PRIMARY KEY (`link_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of link
-- ----------------------------
INSERT INTO `link` VALUES (1915223059841781761, '百度', 'http://www.baidu.com', '百度一下', 'https://ico.ihuan.me/www.baidu.com', 1914935854955081729, '2025-04-24 09:55:35', '13345092258', '2025-06-01 09:49:57', '13345092258', '0', 1);
INSERT INTO `link` VALUES (1915296983069802497, 'Bing', 'www.bing.com', 'bing', 'https://ico.ihuan.me/www.bing.com', 1914936227547619330, '2025-04-24 14:49:20', '13345092258', '2025-06-01 09:50:07', '13345092258', '0', 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `tel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT ' ' COMMENT '修改人',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记，0未删除，1已删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '所属租户ID',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `user_idx1_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1920368384279457793, '1', '$2a$10$QkunEawOffoc3xt.jf5uGe0DLG9HgEK.mghr1W/6Uwc0.C18ohL2e', '13345092258', NULL, NULL, '2025-05-08 06:41:27', '13345092251', '2025-06-01 13:54:05', ' ', '0', 1);

SET FOREIGN_KEY_CHECKS = 1;
