/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : portal

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 17/09/2019 11:55:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL,
  `nick_name` varchar(64) NOT NULL,
  `password` varchar(50) NOT NULL,
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 正常 1 删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `disabled` tinyint(1) DEFAULT NULL COMMENT '0 正常 1 禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'shimh', 'shimh', 'c237910910ffa1f4827bf7fe1831ce43', 0, '2019-08-30 14:18:43', NULL, '123', '919431514@qq.com', 'e4153a582cbc45c3a199998b506dab28', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
