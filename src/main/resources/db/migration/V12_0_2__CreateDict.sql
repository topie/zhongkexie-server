/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : 127.0.0.1:3306
Source Database       : zhongkexie_server

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-01-18 16:38:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for d_dict
-- ----------------------------
DROP TABLE IF EXISTS `d_dict`;
CREATE TABLE `d_dict` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_desc` varchar(100) DEFAULT NULL,
  `dict_name` varchar(50) DEFAULT NULL,
  `dict_seq` int(11) DEFAULT NULL,
  `dict_status` int(1) DEFAULT NULL,
  `dict_type` varchar(10) DEFAULT NULL,
  `dict_code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_dict
-- ----------------------------
INSERT INTO `d_dict` VALUES ('1', '试题所属职责部门', '职责部门', '1', null, null, 'ZZBM');
INSERT INTO `d_dict` VALUES ('2', '专业领域', '专业领域', '2', null, null, 'ZYLY');

-- ----------------------------
-- Table structure for d_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `d_dict_item`;
CREATE TABLE `d_dict_item` (
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_code` varchar(50) DEFAULT NULL,
  `item_name` varchar(50) DEFAULT NULL,
  `item_seq` int(11) DEFAULT NULL,
  `dict_id` int(11) DEFAULT NULL,
  `item_pid` int(11) DEFAULT NULL,
  `item_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `d_dict_item_pid_index` (`item_pid`),
  KEY `d_dict_item_dictid_index` (`dict_id`),
  CONSTRAINT `fk_dict` FOREIGN KEY (`dict_id`) REFERENCES `d_dict` (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_dict_item
-- ----------------------------
INSERT INTO `d_dict_item` VALUES ('1', '学会学术部学会管理处', '学会学术部学会管理处', '1', '1', null, '学会学术部学会管理处');
INSERT INTO `d_dict_item` VALUES ('2', '科技社团党委', '科技社团党委', '2', '1', null, '科技社团党委');
INSERT INTO `d_dict_item` VALUES ('3', '学会学术部改革发展处', '学会学术部改革发展处', '3', '1', null, '学会学术部改革发展处');
INSERT INTO `d_dict_item` VALUES ('4', '学会学术部学术交流处', '学会学术部学术交流处', '4', '1', null, '学会学术部学术交流处');
INSERT INTO `d_dict_item` VALUES ('5', '国际联络部', '国际联络部', '5', '1', null, '国际联络部');
INSERT INTO `d_dict_item` VALUES ('6', '学会学术部期刊出版处', '学会学术部期刊出版处', '6', '1', null, '学会学术部期刊出版处');
INSERT INTO `d_dict_item` VALUES ('7', '组织人事部', '组织人事部', '7', '1', null, '组织人事部');
INSERT INTO `d_dict_item` VALUES ('8', '学会学术部企业工作处', '学会学术部企业工作处', '8', '1', null, '学会学术部企业工作处');
INSERT INTO `d_dict_item` VALUES ('9', '计划财务部', '计划财务部', '9', '1', null, '计划财务部');
INSERT INTO `d_dict_item` VALUES ('10', '科学技术普及部', '科学技术普及部', '10', '1', null, '科学技术普及部');
INSERT INTO `d_dict_item` VALUES ('11', '调研宣传部', '调研宣传部', '11', '1', null, '调研宣传部');
INSERT INTO `d_dict_item` VALUES ('12', '金融', '金融', '1', '2', null, '金融');
INSERT INTO `d_dict_item` VALUES ('13', '科技', '科技', '2', '2', null, '科技');
INSERT INTO `d_dict_item` VALUES ('14', '建筑', '建筑', '3', '2', null, '建筑');
