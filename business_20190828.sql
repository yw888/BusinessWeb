-- MySQL dump 10.13  Distrib 5.5.20, for Win64 (x86)
--
-- Host: localhost    Database: business
-- ------------------------------------------------------
-- Server version	5.5.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE SCHEMA IF NOT EXISTS `business` DEFAULT CHARACTER SET utf8mb4 ;
USE `business` ;

--
-- Table structure for table `neuedu_cart`
--

DROP TABLE IF EXISTS `neuedu_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neuedu_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `checked` int(11) DEFAULT NULL COMMENT '是否选择，1=已勾选，0=未勾选',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neuedu_cart`
--

LOCK TABLES `neuedu_cart` WRITE;
/*!40000 ALTER TABLE `neuedu_cart` DISABLE KEYS */;
INSERT INTO `neuedu_cart` VALUES (3,22,1,1,1,'2019-08-22 22:19:31','2019-08-22 22:19:31');
/*!40000 ALTER TABLE `neuedu_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neuedu_category`
--

DROP TABLE IF EXISTS `neuedu_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neuedu_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别Id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类别id，当id=0时说明是根节点，一级类别',
  `name` varchar(50) DEFAULT NULL COMMENT '类别名称',
  `status` tinyint(1) DEFAULT '1' COMMENT '类别状态1-正常，2-已废弃',
  `sort_order` int(4) DEFAULT NULL COMMENT '排序编号，同类展示顺序，数值相等则自然排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neuedu_category`
--

LOCK TABLES `neuedu_category` WRITE;
/*!40000 ALTER TABLE `neuedu_category` DISABLE KEYS */;
INSERT INTO `neuedu_category` VALUES (1,0,'手机',0,NULL,'2019-05-11 22:26:33','2019-05-11 23:34:20'),(2,1,'iPhone',0,NULL,'2019-05-11 22:46:08','2019-05-11 23:35:19'),(3,2,'iPhone 8',1,NULL,'2019-05-12 15:27:57','2019-05-12 15:27:57');
/*!40000 ALTER TABLE `neuedu_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neuedu_order`
--

DROP TABLE IF EXISTS `neuedu_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neuedu_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `shipping_id` int(11) DEFAULT NULL,
  `payment` decimal(20,2) DEFAULT NULL COMMENT '实际付款金额，单位是元，保留两位小数',
  `payment_type` int(4) DEFAULT NULL COMMENT '支付类型，1-在线支付',
  `postage` int(10) DEFAULT NULL COMMENT '运费，单位是元',
  `status` int(10) DEFAULT NULL COMMENT '订单状态：0-已取消，10-未付款，20-已付款，40-已发货，50-交易成功，60交易关闭',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `send_time` datetime DEFAULT NULL COMMENT '发货时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neuedu_order`
--

LOCK TABLES `neuedu_order` WRITE;
/*!40000 ALTER TABLE `neuedu_order` DISABLE KEYS */;
INSERT INTO `neuedu_order` VALUES (1,1566398986195,22,4,13600.00,1,0,40,NULL,'2019-08-23 22:59:11',NULL,NULL,'2019-08-21 22:49:46','2019-08-23 22:59:11');
/*!40000 ALTER TABLE `neuedu_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neuedu_order_item`
--

DROP TABLE IF EXISTS `neuedu_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neuedu_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单子表id',
  `user_id` int(11) DEFAULT NULL,
  `order_no` bigint(20) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `product_image` varchar(500) DEFAULT NULL COMMENT '商品图片地址',
  `current_unit_price` decimal(20,2) DEFAULT NULL COMMENT '生成订单时的商品单价，单位是元，保留两位小数',
  `quantity` int(10) DEFAULT NULL COMMENT '商品数量',
  `total_price` decimal(20,2) DEFAULT NULL COMMENT '商品总价，单位是元，保留两位小数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `order_no_index` (`order_no`) USING BTREE,
  KEY `order_no_user_id_index` (`user_id`,`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neuedu_order_item`
--

LOCK TABLES `neuedu_order_item` WRITE;
/*!40000 ALTER TABLE `neuedu_order_item` DISABLE KEYS */;
INSERT INTO `neuedu_order_item` VALUES (1,22,1566398986195,1,'iphone x','1.jpg',6800.00,2,13600.00,'2019-08-21 22:49:46','2019-08-21 22:49:46');
/*!40000 ALTER TABLE `neuedu_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neuedu_pay_info`
--

DROP TABLE IF EXISTS `neuedu_pay_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neuedu_pay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `order_no` bigint(20) DEFAULT NULL COMMENT '订单号',
  `pay_paltform` int(10) DEFAULT NULL COMMENT '支付平台：1-支付宝，2-微信',
  `platform_number` varchar(200) DEFAULT NULL COMMENT '支付宝支付流水号',
  `platform_status` varchar(20) DEFAULT NULL COMMENT '支付宝支付状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neuedu_pay_info`
--

LOCK TABLES `neuedu_pay_info` WRITE;
/*!40000 ALTER TABLE `neuedu_pay_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `neuedu_pay_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neuedu_product`
--

DROP TABLE IF EXISTS `neuedu_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neuedu_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `category_id` int(11) NOT NULL COMMENT '分类id，对应neuedu_category表的主键',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `subtitle` varchar(200) DEFAULT NULL COMMENT '商品副标题',
  `main_image` varchar(500) DEFAULT NULL COMMENT '产品主图，url相对地址',
  `sub_image` text COMMENT '图片地址，json格式，扩展用',
  `detail` text COMMENT '商品详情',
  `price` decimal(20,2) NOT NULL COMMENT '价格，单位-元，保留两位小数',
  `stock` int(11) NOT NULL COMMENT '库存数量',
  `status` int(6) DEFAULT '1' COMMENT '商店状态，1-在售 2-下架 3-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neuedu_product`
--

LOCK TABLES `neuedu_product` WRITE;
/*!40000 ALTER TABLE `neuedu_product` DISABLE KEYS */;
INSERT INTO `neuedu_product` VALUES (1,1,'iphone x','iphone 618大促销','1.jpg','1.jpg,2.jpg,3.jpg','刘海屏设计',6800.00,98,1,'2019-07-16 21:56:48','2019-08-21 22:49:46');
/*!40000 ALTER TABLE `neuedu_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neuedu_shipping`
--

DROP TABLE IF EXISTS `neuedu_shipping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neuedu_shipping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `receiver_name` varchar(20) DEFAULT NULL COMMENT '收货姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收货固定电话',
  `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收货移动电话',
  `receiver_province` varchar(20) DEFAULT NULL COMMENT '省份',
  `receiver_city` varchar(20) DEFAULT NULL COMMENT '城市',
  `receiver_district` varchar(20) DEFAULT NULL COMMENT '区/县',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `receiver_zip` varchar(6) DEFAULT NULL COMMENT '邮编',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neuedu_shipping`
--

LOCK TABLES `neuedu_shipping` WRITE;
/*!40000 ALTER TABLE `neuedu_shipping` DISABLE KEYS */;
INSERT INTO `neuedu_shipping` VALUES (4,22,'zhangsan','666666','111111','天津','天津','塘沽区北塘经济区','嘉庭公寓','文件','2019-08-17 23:40:41','2019-08-17 23:40:41');
/*!40000 ALTER TABLE `neuedu_shipping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `neuedu_user`
--

DROP TABLE IF EXISTS `neuedu_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `neuedu_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员，1-普通用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `neuedu_user`
--

LOCK TABLES `neuedu_user` WRITE;
/*!40000 ALTER TABLE `neuedu_user` DISABLE KEYS */;
INSERT INTO `neuedu_user` VALUES (21,'admin','admin','1291734112@qq.com','125688','你是谁？','me',0,'2018-10-15 22:26:35','2018-10-15 22:26:42'),(22,'zhangsan','e10adc3949ba59abbe56e057f20f883e','111111@qq.com','111111','xxx','xxx',0,'2019-03-11 22:02:58','2019-03-11 22:02:58'),(23,'lisi','e10adc3949ba59abbe56e057f20f883e','654321@qq.com','777777','xxx','xxx',1,'2019-03-12 20:48:22','2019-03-12 23:14:43');
/*!40000 ALTER TABLE `neuedu_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-28 21:50:43
