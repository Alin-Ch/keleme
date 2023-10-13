/*
 Navicat Premium Data Transfer

 Source Server         : MySQLDB
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : water

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 11/10/2023 20:27:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `addressid` bigint NOT NULL AUTO_INCREMENT COMMENT '地址id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人名称',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人手机号',
  `detailaddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收货人详细地址',
  `weidu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址纬度',
  `jingdu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址经度',
  `isdefault` int NULL DEFAULT 0 COMMENT '是否默认0不默认  1默认',
  `address_userid` bigint NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`addressid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '猪小明111', '18888888888', '桂林理工大学', '20.66666', '110.11111', 0, 5);
INSERT INTO `address` VALUES (2, '余大嘴', '18888888888', '遥遥领先', '25.28045396749676', '110.31751155504026', 1, 5);
INSERT INTO `address` VALUES (4, '象鼻山公园', '15966666666', '广西壮族自治区桂林市雁山区雁山街389号', '25.267332870414783', '110.2944736811066', 1, 2);
INSERT INTO `address` VALUES (6, '小明嘻嘻嘻', '17453689541', '广西师范大学', '25.096662635587492', '110.2876567770727', 1, 3);
INSERT INTO `address` VALUES (7, '小红', '12334578963', '桂林电子科技大学', '25.0948748851001', '110.30284880893305', 0, 3);
INSERT INTO `address` VALUES (8, '小绿', '11111111111', '广西大学', '25.06399976894627', '110.28798579820432', 0, 3);
INSERT INTO `address` VALUES (9, '小紫', '22222222222', '广西民族大学', NULL, NULL, 0, 3);
INSERT INTO `address` VALUES (10, '张三', '33333333333', '广西医科大学', NULL, NULL, 1, 4);
INSERT INTO `address` VALUES (11, '小绿啦啦啦', '132', '桂林理工大学', NULL, NULL, 0, 4);
INSERT INTO `address` VALUES (32, '可乐', '18989898989', '广西壮族自治区桂林市雁山区雁山镇雁山街319号桂林理工大学(雁山校区)桂林理工大学', '25.06316', '110.300418', 0, 2);
INSERT INTO `address` VALUES (33, '鸡翅', '19999999999', '广西壮族自治区桂林市雁山区大雁路398号桂林融创', '25.052178', '110.313947', 0, 2);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `empid` int NOT NULL AUTO_INCREMENT COMMENT '员工id',
  `empavatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工头像',
  `empname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工名称',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '人员手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工登录密码',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工性别',
  `age` int NULL DEFAULT NULL COMMENT '员工年龄',
  `station_id` int NULL DEFAULT NULL COMMENT '水站id，员工所属',
  PRIMARY KEY (`empid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '1695741575063', '员工1', '18888888888', '123456', '男', 18, 1);
INSERT INTO `employee` VALUES (2, '1695741580915', '员工2', '18888888866', '123456', '男', 19, 1);
INSERT INTO `employee` VALUES (3, '1695741603015', '员工3', '18888888877', '123456', '男', 18, 1);
INSERT INTO `employee` VALUES (4, '1695741609591', '员工明明', '18888888885', '123456', '女', 18, 2);
INSERT INTO `employee` VALUES (6, '1695741618161', '员工6', '18888888887', '123456', '男', 25, 3);
INSERT INTO `employee` VALUES (7, '1695741626189', '员工7', '18888888886', '123456', '女', 21, 4);
INSERT INTO `employee` VALUES (11, '1695741636073', '员工8', '18888888888', '123456', '女', 18, 4);
INSERT INTO `employee` VALUES (12, '1695741655425', '员工9', '18888888884', '123456', '男', 20, 5);
INSERT INTO `employee` VALUES (13, '1695741661942', '员工10', '19745683215', '123456', '女', 22, 5);

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good`  (
  `goodid` int NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goodname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品规格',
  `newprice` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品新价格',
  `oldprice` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品旧价格',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品描述',
  `quantity` int NULL DEFAULT NULL COMMENT '商品库存',
  `sold` int NULL DEFAULT NULL COMMENT '商品已售数量',
  `station_id` int NULL DEFAULT NULL COMMENT '水站ID',
  `type_id` int NULL DEFAULT NULL COMMENT '商品类型id',
  PRIMARY KEY (`goodid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good
-- ----------------------------
INSERT INTO `good` VALUES (1, '哇哈哈矿泉水', '330ml', 2.00, 1.80, '1695376673270', '哇哈哈矿泉水', 1183, 446, 1, 1);
INSERT INTO `good` VALUES (2, '哇哈哈纯净水', '500ml', 5.00, 4.50, '1695376680954', '哇哈哈哈哈哈哈哈纯净水', 1184, 172, 1, 2);
INSERT INTO `good` VALUES (3, '哇哈哈矿物质水', '500ml', 5.50, 4.30, '1695741689265', '矿物质水哇哈哈哈哈', 2222, 230, 4, 3);
INSERT INTO `good` VALUES (4, '哇哈哈大桶纯净水', '16.7L', 7.00, 6.80, '1695741695291', '大桶水', 2222, 506, 4, 4);
INSERT INTO `good` VALUES (5, '农夫山泉大桶水', '16.7L', 7.00, 7.00, '1695741701249', '农夫山泉的桶装水', 2222, 600, 2, 1);
INSERT INTO `good` VALUES (6, '农夫山泉矿泉水', '330ml', 2.00, 2.00, '1695741731990', '矿泉水', 2222, 311, 2, 2);
INSERT INTO `good` VALUES (7, '百岁山天然矿泉水', '570ml', 3.00, 3.00, '1695741739407', '百岁山天然矿泉水', 2222, 453, 5, 3);
INSERT INTO `good` VALUES (8, '景田纯净水', '560ml', 3.50, 3.50, '1695376716699', '景田纯净水', 2166, 410, 3, 4);
INSERT INTO `good` VALUES (9, '景田纯净水', '15L', 7.00, 7.00, '1695376705089', '景田纯净水大桶装', 2161, 713, 3, 1);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `someoneName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品详情id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `order_id` bigint NULL DEFAULT NULL COMMENT '订单的id',
  `good_id` int NULL DEFAULT NULL COMMENT '商品的id',
  `number` int NULL DEFAULT NULL COMMENT '商品选购的数量',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品的价格',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_orders_orderdetail`(`order_id` ASC) USING BTREE,
  CONSTRAINT `fk_orders_orderdetail` FOREIGN KEY (`order_id`) REFERENCES `orders` (`orderid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 160 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (151, '哇哈哈矿泉水', 1706570793225424896, 1, 1, 2.00);
INSERT INTO `order_detail` VALUES (152, '哇哈哈纯净水', 1706570869645643776, 2, 1, 5.00);
INSERT INTO `order_detail` VALUES (153, '景田纯净水', 1706570869645643776, 8, 1, 3.50);
INSERT INTO `order_detail` VALUES (154, '哇哈哈纯净水', 1706573321379909632, 2, 1, 5.00);
INSERT INTO `order_detail` VALUES (155, '景田纯净水', 1706573321379909632, 8, 1, 3.50);
INSERT INTO `order_detail` VALUES (156, '哇哈哈纯净水', 1706599334629478400, 2, 1, 5.00);
INSERT INTO `order_detail` VALUES (157, '景田纯净水', 1706599334629478400, 8, 1, 3.50);
INSERT INTO `order_detail` VALUES (158, '哇哈哈纯净水', 1706604586187493376, 2, 1, 5.00);
INSERT INTO `order_detail` VALUES (159, '景田纯净水', 1706604586187493376, 8, 1, 3.50);
INSERT INTO `order_detail` VALUES (160, '景田纯净水', 1706604586187493376, 9, 1, 7.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orderid` bigint NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `ordernum` bigint NULL DEFAULT NULL COMMENT '订单号',
  `createtime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单创建时间',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单的价格',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单的备注',
  `statu` int NULL DEFAULT 1 COMMENT '订单的状态 1 待付款 0取消 2已付款 3配送中 4已完成',
  `order_userid` int NULL DEFAULT NULL COMMENT '用户id',
  `address_id` int NULL DEFAULT NULL COMMENT '收货地址id',
  PRIMARY KEY (`orderid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1706604586187493377 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1706570793225424896, 1706570793225424896, '2023-09-26 15:25:58', 2.00, '', 3, 2, 32);
INSERT INTO `orders` VALUES (1706570869645643776, 1706570869645643776, '2023-09-26 15:26:16', 8.50, '', 3, 2, 32);
INSERT INTO `orders` VALUES (1706573321379909632, 1706573321379909632, '2023-09-26 15:36:01', 8.50, '', 2, 2, 32);
INSERT INTO `orders` VALUES (1706599334629478400, 1706599334629478400, '2023-09-26 17:19:23', 8.50, '', 2, 2, 33);
INSERT INTO `orders` VALUES (1706604586187493376, 1706604586187493376, '2023-09-26 17:40:15', 15.50, '', 2, 2, 33);

-- ----------------------------
-- Table structure for stations
-- ----------------------------
DROP TABLE IF EXISTS `stations`;
CREATE TABLE `stations`  (
  `sid` int NOT NULL AUTO_INCREMENT COMMENT '水站id',
  `sname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '水站名称',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '水站地址',
  `manager` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '水站负责人',
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人电话',
  `weidu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '纬度',
  `jingdu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '经度',
  `distribution` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配送距离',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stations
-- ----------------------------
INSERT INTO `stations` VALUES (1, '哇哈哈水站', '桂林理工大学', '屏风', '18645213489', '25.062384501139015', '110.31425134399416', '5000');
INSERT INTO `stations` VALUES (2, '农夫山泉水站', '广西师范大学', NULL, NULL, '25.097289', '110.28789996751584', '3000');
INSERT INTO `stations` VALUES (3, '广西桂林农业学校水站', '广西桂林农业学校', NULL, '', '25.07472', '110.31418', '5000');
INSERT INTO `stations` VALUES (4, '桂林旅游学院水站', '桂林旅游学院', NULL, '', '25.12090', '110.30076', '5000');
INSERT INTO `stations` VALUES (5, '屏风校区水站', '桂林理工大学屏风校区', NULL, NULL, '25.28002', '110.31650', '5000');

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `ticketid` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ticketname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '水票名称',
  `usecount` int NULL DEFAULT 2 COMMENT '水票剩余使用次数',
  `t_stationid` int NULL DEFAULT NULL COMMENT '水站id',
  `t_userid` int NULL DEFAULT NULL COMMENT '用户id',
  `money` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '水票价格',
  PRIMARY KEY (`ticketid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '水票表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ticket
-- ----------------------------
INSERT INTO `ticket` VALUES (1, '哇哈哈15Lx12', 12, 1, 3, '80.5');
INSERT INTO `ticket` VALUES (2, '哇哈哈330mlx24', 24, 2, 5, '24.5');
INSERT INTO `ticket` VALUES (3, '哇哈哈550mlx3', 6, 1, 8, '6');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `typeid` int NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `typeName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型名称',
  `typeDescription` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品类型描述',
  PRIMARY KEY (`typeid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '矿泉水', '矿泉水矿泉水都是矿');
INSERT INTO `type` VALUES (2, '纯净水', '纯净水，纯净物');
INSERT INTO `type` VALUES (3, '矿物质水', '里面有矿物质的水');
INSERT INTO `type` VALUES (4, '大桶水', '不是小瓶喝不起，是大瓶更有性价比');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userid` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'ROLE_CONSUMER' COMMENT '角色',
  `jifen` int NULL DEFAULT 0 COMMENT '积分',
  `station_id` int NOT NULL DEFAULT 0 COMMENT '水站id，如果有id，就是那个水站的员工',
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1695740292454', 'admin', '123456', '18888888888', '男', 'ROLE_ADMIN', 80, 0);
INSERT INTO `user` VALUES (2, 'http://tmp/UWinIyn5ZhyU3f5b35ecb511e61514b05e2e17e223b2.jpeg', '胡歌', '123456', '18888888887', '男', 'ROLE_BOSS', 540, 0);
INSERT INTO `user` VALUES (3, '1695785398867', '李白', '123456', '18888888886', '男', 'ROLE_CONSUMER', 310, 0);
INSERT INTO `user` VALUES (4, '1695785371342', '亚瑟', '111111', '18888888885', '男', 'ROLE_CONSUMER', 50, 0);
INSERT INTO `user` VALUES (5, '1695785363806', '小钱', '234567', '13855657890', '女', 'ROLE_CONSUMER', 100, 0);
INSERT INTO `user` VALUES (6, '1695740816271', '王昭君', '999999', '18899001234', '女', 'ROLE_CONSUMER', 120, 0);
INSERT INTO `user` VALUES (7, '1695740827687', '乔峰', '686868', '13670708813', '男', 'ROLE_CONSUMER', 80, 0);
INSERT INTO `user` VALUES (8, '1695740835713', '兰陵王', '888888', '18924909800', '男', 'ROLE_CONSUMER', 60, 0);

SET FOREIGN_KEY_CHECKS = 1;
