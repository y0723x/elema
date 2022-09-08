/*
 Navicat Premium Data Transfer

 Source Server         : MySQL8
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3307
 Source Schema         : elema

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 22/05/2022 18:22:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号码',
  `telephone` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住宅电话',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系地址',
  `enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '密码',
  `userface` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'zhangsan', '10086', '110', '浙江杭州', 1, 'niuwa', '$2a$10$phWPFzEYhX/XF4GUvmy22uwpMTIJH3ucItoiLXbx.HFkBkNBvX7aq', NULL, NULL);

-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `adminid` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `rid` int(0) NULL DEFAULT NULL COMMENT ' 权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin_role
-- ----------------------------
INSERT INTO `t_admin_role` VALUES (1, 1, 3);

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `goods_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `goods_intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品介绍',
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品名称',
  `goods_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '商品图片',
  `goods_sale` int(0) NULL DEFAULT NULL COMMENT '商品月售',
  `shop_cate_id` int(0) NULL DEFAULT NULL COMMENT '所属店铺类别',
  `is_enabled` int(0) NULL DEFAULT NULL COMMENT '是否可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES (1, 22.50, '主要原料：牛肉饼、面包、番茄片、芝士片、酸黄瓜、酱；产品及包装以实物为准。', '汁汁双层嫩牛堡', 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/ironman/489845.jpg', 38, 1, 1);
INSERT INTO `t_goods` VALUES (2, 51.50, '新奥尔良烤鸡腿堡1个+醇香土豆泥1份+香辣鸡翅2块+百事可乐（中）一杯，详情以实物为准。', '饿小宝新奥尔良烤堡单人餐', 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/ironman/674308.jpg', 2, 1, 1);
INSERT INTO `t_goods` VALUES (3, 63.00, '吮指原味鸡*6块装，详情以实物为准', '吮指原味鸡六块装', 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/ironman/689264.jpg', 15, 2, 1);
INSERT INTO `t_goods` VALUES (4, 49.00, '吮指原味鸡2块+醇香土豆泥1份+黄金鸡块（5块）+百事可乐（中）1杯；详情以实物为准', '吮指原味鸡豆泥餐', 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/ironman/877075.jpg', 7, 2, 1);
INSERT INTO `t_goods` VALUES (5, 13.00, '主要原料：茶、奶茶伴侣，产品以实物为准', '醇香奶茶热中', 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/ironman/944959.jpg', 4, 3, 1);
INSERT INTO `t_goods` VALUES (6, 7.50, '主要原料：红豆；产品以实物为准', '红豆派（1只）', 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/canzhong/545909.jpg', 20, 3, 1);
INSERT INTO `t_goods` VALUES (7, 99.00, '香辣鸡翅20块', '20块香辣鸡翅', 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/canzhong/545918.jpg', 7, 4, 1);
INSERT INTO `t_goods` VALUES (8, 56.00, '10块新奥尔良烤翅', '10块装新奥尔良烤翅', 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/canzhong/587597.jpg', 13, 4, 1);

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父菜单的id',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '（id1,id2,id3)，通过,分隔开上一级菜单',
  `is_parent` int(0) NULL DEFAULT NULL COMMENT '是否是父菜单',
  `is_enabled` int(0) NULL DEFAULT NULL COMMENT '菜单能否用',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '标题',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标',
  `level` int(0) NULL DEFAULT NULL COMMENT '一级菜单，二级菜单，如（level1，level2）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, -1, '1', 1, 1, '美食外卖', '&#xe751;', 1);
INSERT INTO `t_menu` VALUES (2, -1, '2', 0, 1, '超市便利', '&#xe770;', 1);
INSERT INTO `t_menu` VALUES (3, -1, '3', 0, 1, '美食团购', '&#xe618;', 1);
INSERT INTO `t_menu` VALUES (4, -1, '4', 0, 1, '买药', '&#xe62d;', 1);
INSERT INTO `t_menu` VALUES (5, -1, '5', 0, 1, '休闲玩乐', '&#xfb65;', 1);
INSERT INTO `t_menu` VALUES (6, -1, '6', 0, 1, '午餐', '&#xe6e8;', 1);
INSERT INTO `t_menu` VALUES (7, -1, '7', 0, 1, '水果', '&#xe810;', 1);
INSERT INTO `t_menu` VALUES (8, -1, '8', 0, 1, '红包膨胀', '&#xe6da;', 1);
INSERT INTO `t_menu` VALUES (9, -1, '9', 0, 1, '甜品饮品', '&#xe61c;', 1);
INSERT INTO `t_menu` VALUES (10, -1, '10', 0, 1, '数字藏品', '&#xe60b;', 1);
INSERT INTO `t_menu` VALUES (11, -1, '11', 0, 1, '生日纪念日', '&#xe80f;', 1);
INSERT INTO `t_menu` VALUES (12, -1, '12', 0, 1, '每日抢好券', '&#xe748;', 1);
INSERT INTO `t_menu` VALUES (13, -1, '13', 0, 1, '0元领水果', '&#xe8b3;', 1);
INSERT INTO `t_menu` VALUES (14, -1, '14', 0, 1, '阳光厨房', '&#xe884;', 1);
INSERT INTO `t_menu` VALUES (15, -1, '15', 0, 1, '鲜花绿植', '&#xe625;', 1);
INSERT INTO `t_menu` VALUES (16, 1, '1,16', 0, 1, '美味饭菜', NULL, 2);
INSERT INTO `t_menu` VALUES (17, 1, '1,17', 0, 1, '汉堡披萨', NULL, 2);
INSERT INTO `t_menu` VALUES (18, 1, '1,18', 0, 1, '奶茶果汁', NULL, 2);
INSERT INTO `t_menu` VALUES (19, 1, '1,19', 0, 1, '米粉烫捞', NULL, 2);
INSERT INTO `t_menu` VALUES (20, 1, '1,20', 0, 1, '龙虾烧烤', NULL, 2);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `shop_id` int(0) NULL DEFAULT NULL COMMENT '店铺id',
  `goods_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '产品id（1,2,3）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '订单生成时间',
  `status` int(0) NULL DEFAULT NULL COMMENT '订单状态(0：未付款，1：已付款，2：已取消，3：已退款)',
  `is_enabled` int(0) NULL DEFAULT NULL COMMENT '是否可用',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价格',
  `total_count` int(0) NULL DEFAULT NULL COMMENT '产品总数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (1, 1, 1, '1,2', '2022-05-20 15:59:41', 0, 1, 100.00, 3);
INSERT INTO `t_order` VALUES (2, 1, 1, '1,2,3,4', '2022-05-19 16:00:15', 1, 1, 150.00, 4);
INSERT INTO `t_order` VALUES (3, 1, 1, '1,3,4', '2022-05-18 16:00:46', 2, 1, 120.00, 3);
INSERT INTO `t_order` VALUES (4, 1, 1, '1,2,3,4,5,6', '2022-05-17 16:01:13', 3, 1, 200.00, 6);
INSERT INTO `t_order` VALUES (6, 1, 1, '1,2,3,4,5,6,7,8', '2022-05-21 17:01:11', 0, 1, 361.50, 8);
INSERT INTO `t_order` VALUES (7, 1, 1, '5,6', '2022-05-21 17:09:43', 0, 1, 20.50, 2);
INSERT INTO `t_order` VALUES (8, 1, 1, '5,6,7,8', '2022-05-21 17:14:36', 2, 1, 175.50, 4);
INSERT INTO `t_order` VALUES (9, 1, 1, '1,2', '2022-05-21 17:21:39', 1, 1, 74.00, 2);
INSERT INTO `t_order` VALUES (10, 1, 1, '1', '2022-05-21 18:40:06', 2, 1, 22.50, 1);
INSERT INTO `t_order` VALUES (11, 1, 1, '3,7', '2022-05-21 18:55:20', 1, 1, 162.00, 2);

-- ----------------------------
-- Table structure for t_order_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_order_goods`;
CREATE TABLE `t_order_goods`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` int(0) NULL DEFAULT NULL COMMENT '订单id',
  `goods_id` int(0) NULL DEFAULT NULL COMMENT '产品id',
  `is_enabled` int(0) NULL DEFAULT NULL COMMENT '是否可用',
  `goods_count` int(0) NULL DEFAULT NULL COMMENT '产品下单数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_goods
-- ----------------------------
INSERT INTO `t_order_goods` VALUES (1, 1, 1, 1, 2);
INSERT INTO `t_order_goods` VALUES (2, 1, 2, 1, 1);
INSERT INTO `t_order_goods` VALUES (3, 2, 1, 1, 1);
INSERT INTO `t_order_goods` VALUES (4, 2, 2, 1, 1);
INSERT INTO `t_order_goods` VALUES (5, 2, 3, 1, 1);
INSERT INTO `t_order_goods` VALUES (6, 2, 4, 1, 1);
INSERT INTO `t_order_goods` VALUES (7, 3, 1, 1, 1);
INSERT INTO `t_order_goods` VALUES (8, 3, 3, 1, 1);
INSERT INTO `t_order_goods` VALUES (9, 3, 4, 1, 1);
INSERT INTO `t_order_goods` VALUES (10, 4, 1, 1, 1);
INSERT INTO `t_order_goods` VALUES (11, 4, 2, 1, 1);
INSERT INTO `t_order_goods` VALUES (12, 4, 3, 1, 1);
INSERT INTO `t_order_goods` VALUES (13, 4, 4, 1, 1);
INSERT INTO `t_order_goods` VALUES (14, 4, 5, 1, 1);
INSERT INTO `t_order_goods` VALUES (15, 4, 6, 1, 1);
INSERT INTO `t_order_goods` VALUES (16, 6, 1, 1, 1);
INSERT INTO `t_order_goods` VALUES (17, 6, 2, 1, 1);
INSERT INTO `t_order_goods` VALUES (18, 6, 3, 1, 1);
INSERT INTO `t_order_goods` VALUES (19, 6, 4, 1, 1);
INSERT INTO `t_order_goods` VALUES (20, 6, 5, 1, 1);
INSERT INTO `t_order_goods` VALUES (21, 6, 6, 1, 1);
INSERT INTO `t_order_goods` VALUES (22, 6, 7, 1, 1);
INSERT INTO `t_order_goods` VALUES (23, 6, 8, 1, 1);
INSERT INTO `t_order_goods` VALUES (24, 7, 5, 1, 1);
INSERT INTO `t_order_goods` VALUES (25, 7, 6, 1, 1);
INSERT INTO `t_order_goods` VALUES (26, 8, 5, 1, 1);
INSERT INTO `t_order_goods` VALUES (27, 8, 6, 1, 1);
INSERT INTO `t_order_goods` VALUES (28, 8, 7, 1, 1);
INSERT INTO `t_order_goods` VALUES (29, 8, 8, 1, 1);
INSERT INTO `t_order_goods` VALUES (30, 9, 1, 1, 1);
INSERT INTO `t_order_goods` VALUES (31, 9, 2, 1, 1);
INSERT INTO `t_order_goods` VALUES (32, 10, 1, 1, 1);
INSERT INTO `t_order_goods` VALUES (33, 11, 3, 1, 1);
INSERT INTO `t_order_goods` VALUES (34, 11, 7, 1, 1);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '名称',
  `nameZh` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'ROLE_superadmin', '超级管理员');
INSERT INTO `t_role` VALUES (2, 'ROLE_admin', '管理员');
INSERT INTO `t_role` VALUES (3, 'ROLE_user', '普通用户');

-- ----------------------------
-- Table structure for t_shop
-- ----------------------------
DROP TABLE IF EXISTS `t_shop`;
CREATE TABLE `t_shop`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '店铺名称',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '类属',
  `is_enabled` int(0) NULL DEFAULT NULL COMMENT '是否可用',
  `cates_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '店铺包含产品类别的ids，如（id1，id2，id3）',
  `shop_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '店铺地址',
  `shop_score` double NULL DEFAULT NULL COMMENT '评分',
  `shop_sale` int(0) NULL DEFAULT NULL COMMENT '月售',
  `shop_distribution` double NULL DEFAULT NULL COMMENT '配送',
  `shop_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `distribution_min_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '最低总价',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_shop
-- ----------------------------
INSERT INTO `t_shop` VALUES (1, 'kfc', 17, 1, '1,2,3,4', '青山湖宝龙', 4.8, 640, 5, 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/spideman/247850.png', 50.00);
INSERT INTO `t_shop` VALUES (2, '麦当劳', 17, 1, NULL, '青山湖宝龙', 4.9, 1000, 6, 'https://yxrepository.oss-cn-hangzhou.aliyuncs.com/img/spideman/374464.jpg', 50.00);

-- ----------------------------
-- Table structure for t_shop_cate
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_cate`;
CREATE TABLE `t_shop_cate`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `shop_cate_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '店铺分类名称',
  `goods_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '店铺包含产品的ids，如（id1，id2，id3）',
  `shop_id` int(0) NULL DEFAULT NULL COMMENT '所属店铺',
  `is_enabled` int(0) NULL DEFAULT NULL COMMENT '是否可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_shop_cate
-- ----------------------------
INSERT INTO `t_shop_cate` VALUES (1, '美味汉堡/卷', '1,2', 1, 1);
INSERT INTO `t_shop_cate` VALUES (2, '原味鸡/脆皮鸡', '3,4', 1, 1);
INSERT INTO `t_shop_cate` VALUES (3, '饮品/甜品', '5,6', 1, 1);
INSERT INTO `t_shop_cate` VALUES (4, '炸鸡/小食', '7,8', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
