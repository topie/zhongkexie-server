/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : 127.0.0.1:3306
Source Database       : zhongkexie_server

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-01-10 18:43:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for d_dept
-- ----------------------------
DROP TABLE IF EXISTS `d_dept`;
CREATE TABLE `d_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(24) DEFAULT NULL COMMENT '编码',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `duty` varchar(255) DEFAULT NULL COMMENT '职能',
  `field` varchar(255) DEFAULT NULL COMMENT '领域',
  `pid` int(11) DEFAULT NULL COMMENT '父级',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `tel` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `linkman` varchar(255) DEFAULT NULL COMMENT '联系人',
  `type` varchar(255) DEFAULT NULL COMMENT '类型 理科 工科 ...',
  `seq` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_dept
-- ----------------------------
INSERT INTO `d_dept` VALUES ('1', 'ZGKX', '中国科学技术协会', null, null, '-1', null, null, null, null, '1');
INSERT INTO `d_dept` VALUES ('2', 'A-01', '中国数学会', null, null, '1', null, null, null, '理科', '1');
INSERT INTO `d_dept` VALUES ('3', 'A-02', '中国物理学会', null, null, '1', null, null, null, '理科', '2');
INSERT INTO `d_dept` VALUES ('4', 'A-03', '中国力学学会', null, null, '1', null, null, null, '理科', '3');
INSERT INTO `d_dept` VALUES ('5', 'A-04', '中国光学学会', null, null, '1', null, null, null, '理科', '4');
INSERT INTO `d_dept` VALUES ('6', 'A-05', '中国声学学会', null, null, '1', null, null, null, '理科', '5');
INSERT INTO `d_dept` VALUES ('7', 'A-06', '中国化学会', null, null, '1', null, null, null, '理科', '6');
INSERT INTO `d_dept` VALUES ('8', 'A-07', '中国天文学会', null, null, '1', null, null, null, '理科', '7');
INSERT INTO `d_dept` VALUES ('9', 'A-08', '中国气象学会', null, null, '1', null, null, null, '理科', '8');
INSERT INTO `d_dept` VALUES ('10', 'A-09', '中国空间科学学会', null, null, '1', null, null, null, '理科', '9');
INSERT INTO `d_dept` VALUES ('11', 'A-10', '中国地质学会', null, null, '1', null, null, null, '理科', '10');
INSERT INTO `d_dept` VALUES ('12', 'A-11', '中国地理学会', null, null, '1', null, null, null, '理科', '11');
INSERT INTO `d_dept` VALUES ('13', 'A-12', '中国地球物理学会', null, null, '1', null, null, null, '理科', '12');
INSERT INTO `d_dept` VALUES ('14', 'A-13', '中国矿物岩石地球化学学会', null, null, '1', null, null, null, '理科', '13');
INSERT INTO `d_dept` VALUES ('15', 'A-14', '中国古生物学会', null, null, '1', null, null, null, '理科', '14');
INSERT INTO `d_dept` VALUES ('16', 'A-15', '中国海洋湖沼学会', null, null, '1', null, null, null, '理科', '15');
INSERT INTO `d_dept` VALUES ('17', 'A-16', '中国海洋学会', null, null, '1', null, null, null, '理科', '16');
INSERT INTO `d_dept` VALUES ('18', 'A-17', '中国地震学会', null, null, '1', null, null, null, '理科', '17');
INSERT INTO `d_dept` VALUES ('19', 'A-18', '中国动物学会', null, null, '1', null, null, null, '理科', '18');
INSERT INTO `d_dept` VALUES ('20', 'A-19', '中国植物学会', null, null, '1', null, null, null, '理科', '19');
INSERT INTO `d_dept` VALUES ('21', 'A-20', '中国昆虫学会', null, null, '1', null, null, null, '理科', '20');
INSERT INTO `d_dept` VALUES ('22', 'A-21', '中国微生物学会', null, null, '1', null, null, null, '理科', '21');
INSERT INTO `d_dept` VALUES ('23', 'A-22', '中国生物化学与分子生物学会', null, null, '1', null, null, null, '理科', '22');
INSERT INTO `d_dept` VALUES ('24', 'A-23', '中国细胞生物学学会', null, null, '1', null, null, null, '理科', '23');
INSERT INTO `d_dept` VALUES ('25', 'A-24', '中国植物生理与植物分子生物学学会', null, null, '1', null, null, null, '理科', '24');
INSERT INTO `d_dept` VALUES ('26', 'A-25', '中国生物物理学会', null, null, '1', null, null, null, '理科', '25');
INSERT INTO `d_dept` VALUES ('27', 'A-26', '中国遗传学会', null, null, '1', null, null, null, '理科', '26');
INSERT INTO `d_dept` VALUES ('28', 'A-27', '中国心理学会', null, null, '1', null, null, null, '理科', '27');
INSERT INTO `d_dept` VALUES ('29', 'A-28', '中国生态学学会', null, null, '1', null, null, null, '理科', '28');
INSERT INTO `d_dept` VALUES ('30', 'A-29', '中国环境科学学会', null, null, '1', null, null, null, '理科', '29');
INSERT INTO `d_dept` VALUES ('31', 'A-30', '中国自然资源学会', null, null, '1', null, null, null, '理科', '30');
INSERT INTO `d_dept` VALUES ('32', 'A-31', '中国感光学会', null, null, '1', null, null, null, '理科', '31');
INSERT INTO `d_dept` VALUES ('33', 'A-32', '中国优选法统筹法与经济数学研究会', null, null, '1', null, null, null, '理科', '32');
INSERT INTO `d_dept` VALUES ('34', 'A-33', '中国岩石力学与工程学会', null, null, '1', null, null, null, '理科', '33');
INSERT INTO `d_dept` VALUES ('35', 'A-34', '中国野生动物保护协会', null, null, '1', null, null, null, '理科', '34');
INSERT INTO `d_dept` VALUES ('36', 'A-35', '中国系统工程学会', null, null, '1', null, null, null, '理科', '35');
INSERT INTO `d_dept` VALUES ('37', 'A-36', '中国实验动物学会', null, null, '1', null, null, null, '理科', '36');
INSERT INTO `d_dept` VALUES ('38', 'A-37', '中国青藏高原研究会', null, null, '1', null, null, null, '理科', '37');
INSERT INTO `d_dept` VALUES ('39', 'A-38', '中国环境诱变剂学会', null, null, '1', null, null, null, '理科', '38');
INSERT INTO `d_dept` VALUES ('40', 'A-39', '中国运筹学会', null, null, '1', null, null, null, '理科', '39');
INSERT INTO `d_dept` VALUES ('41', 'A-40', '中国菌物学会', null, null, '1', null, null, null, '理科', '40');
INSERT INTO `d_dept` VALUES ('42', 'A-41', '中国晶体学会', null, null, '1', null, null, null, '理科', '41');
INSERT INTO `d_dept` VALUES ('43', 'A-42', '中国神经科学学会', null, null, '1', null, null, null, '理科', '42');
INSERT INTO `d_dept` VALUES ('44', 'A-43W', '中国认知科学学会', null, null, '1', null, null, null, '理科', '43');
INSERT INTO `d_dept` VALUES ('45', 'A-44W', '中国微循环学会', null, null, '1', null, null, null, '理科', '44');
INSERT INTO `d_dept` VALUES ('46', 'A-45G', '国际数字地球学会', null, null, '1', null, null, null, '理科', '45');
INSERT INTO `d_dept` VALUES ('47', 'B-01', '中国机械工程学会', null, null, '1', null, null, null, '工科', '1');
INSERT INTO `d_dept` VALUES ('48', 'B-02', '中国汽车工程学会', null, null, '1', null, null, null, '工科', '2');
INSERT INTO `d_dept` VALUES ('49', 'B-03', '中国农业机械学会', null, null, '1', null, null, null, '工科', '3');
INSERT INTO `d_dept` VALUES ('50', 'B-04', '中国农业工程学会', null, null, '1', null, null, null, '工科', '4');
INSERT INTO `d_dept` VALUES ('51', 'B-05', '中国电机工程学会', null, null, '1', null, null, null, '工科', '5');
INSERT INTO `d_dept` VALUES ('52', 'B-06', '中国电工技术学会', null, null, '1', null, null, null, '工科', '6');
INSERT INTO `d_dept` VALUES ('53', 'B-07', '中国水力发电工程学会', null, null, '1', null, null, null, '工科', '7');
INSERT INTO `d_dept` VALUES ('54', 'B-08', '中国水利学会', null, null, '1', null, null, null, '工科', '8');
INSERT INTO `d_dept` VALUES ('55', 'B-09', '中国内燃机学会', null, null, '1', null, null, null, '工科', '9');
INSERT INTO `d_dept` VALUES ('56', 'B-10', '中国工程热物理学会', null, null, '1', null, null, null, '工科', '10');
INSERT INTO `d_dept` VALUES ('57', 'B-11', '中国空气动力学会', null, null, '1', null, null, null, '工科', '11');
INSERT INTO `d_dept` VALUES ('58', 'B-12', '中国制冷学会', null, null, '1', null, null, null, '工科', '12');
INSERT INTO `d_dept` VALUES ('59', 'B-13', '中国真空学会', null, null, '1', null, null, null, '工科', '13');
INSERT INTO `d_dept` VALUES ('60', 'B-14', '中国自动化学会', null, null, '1', null, null, null, '工科', '14');
INSERT INTO `d_dept` VALUES ('61', 'B-15', '中国仪器仪表学会', null, null, '1', null, null, null, '工科', '15');
INSERT INTO `d_dept` VALUES ('62', 'B-16', '中国计量测试学会', null, null, '1', null, null, null, '工科', '16');
INSERT INTO `d_dept` VALUES ('63', 'B-17', '中国标准化协会', null, null, '1', null, null, null, '工科', '17');
INSERT INTO `d_dept` VALUES ('64', 'B-18', '中国图学学会', null, null, '1', null, null, null, '工科', '18');
INSERT INTO `d_dept` VALUES ('65', 'B-19', '中国电子学会', null, null, '1', null, null, null, '工科', '19');
INSERT INTO `d_dept` VALUES ('66', 'B-20', '中国计算机学会', null, null, '1', null, null, null, '工科', '20');
INSERT INTO `d_dept` VALUES ('67', 'B-21', '中国通信学会', null, null, '1', null, null, null, '工科', '21');
INSERT INTO `d_dept` VALUES ('68', 'B-22', '中国中文信息学会', null, null, '1', null, null, null, '工科', '22');
INSERT INTO `d_dept` VALUES ('69', 'B-23', '中国测绘地理信息学会', null, null, '1', null, null, null, '工科', '23');
INSERT INTO `d_dept` VALUES ('70', 'B-24', '中国造船工程学会', null, null, '1', null, null, null, '工科', '24');
INSERT INTO `d_dept` VALUES ('71', 'B-25', '中国航海学会', null, null, '1', null, null, null, '工科', '25');
INSERT INTO `d_dept` VALUES ('72', 'B-26', '中国铁道学会', null, null, '1', null, null, null, '工科', '26');
INSERT INTO `d_dept` VALUES ('73', 'B-27', '中国公路学会', null, null, '1', null, null, null, '工科', '27');
INSERT INTO `d_dept` VALUES ('74', 'B-28', '中国航空学会', null, null, '1', null, null, null, '工科', '28');
INSERT INTO `d_dept` VALUES ('75', 'B-29', '中国宇航学会', null, null, '1', null, null, null, '工科', '29');
INSERT INTO `d_dept` VALUES ('76', 'B-30', '中国兵工学会', null, null, '1', null, null, null, '工科', '30');
INSERT INTO `d_dept` VALUES ('77', 'B-31', '中国金属学会', null, null, '1', null, null, null, '工科', '31');
INSERT INTO `d_dept` VALUES ('78', 'B-32', '中国有色金属学会', null, null, '1', null, null, null, '工科', '32');
INSERT INTO `d_dept` VALUES ('79', 'B-33', '中国稀土学会', null, null, '1', null, null, null, '工科', '33');
INSERT INTO `d_dept` VALUES ('80', 'B-34', '中国腐蚀与防护学会', null, null, '1', null, null, null, '工科', '34');
INSERT INTO `d_dept` VALUES ('81', 'B-35', '中国化工学会', null, null, '1', null, null, null, '工科', '35');
INSERT INTO `d_dept` VALUES ('82', 'B-36', '中国核学会', null, null, '1', null, null, null, '工科', '36');
INSERT INTO `d_dept` VALUES ('83', 'B-37', '中国石油学会', null, null, '1', null, null, null, '工科', '37');
INSERT INTO `d_dept` VALUES ('84', 'B-38', '中国煤炭学会', null, null, '1', null, null, null, '工科', '38');
INSERT INTO `d_dept` VALUES ('85', 'B-39', '中国可再生能源学会', null, null, '1', null, null, null, '工科', '39');
INSERT INTO `d_dept` VALUES ('86', 'B-40', '中国能源研究会', null, null, '1', null, null, null, '工科', '40');
INSERT INTO `d_dept` VALUES ('87', 'B-41', '中国硅酸盐学会', null, null, '1', null, null, null, '工科', '41');
INSERT INTO `d_dept` VALUES ('88', 'B-42', '中国建筑学会', null, null, '1', null, null, null, '工科', '42');
INSERT INTO `d_dept` VALUES ('89', 'B-43', '中国土木工程学会', null, null, '1', null, null, null, '工科', '43');
INSERT INTO `d_dept` VALUES ('90', 'B-44', '中国生物工程学会', null, null, '1', null, null, null, '工科', '44');
INSERT INTO `d_dept` VALUES ('91', 'B-45', '中国纺织工程学会', null, null, '1', null, null, null, '工科', '45');
INSERT INTO `d_dept` VALUES ('92', 'B-46', '中国造纸学会', null, null, '1', null, null, null, '工科', '46');
INSERT INTO `d_dept` VALUES ('93', 'B-47', '中国文物保护技术协会', null, null, '1', null, null, null, '工科', '47');
INSERT INTO `d_dept` VALUES ('94', 'B-48', '中国印刷技术协会', null, null, '1', null, null, null, '工科', '48');
INSERT INTO `d_dept` VALUES ('95', 'B-49', '中国材料研究学会', null, null, '1', null, null, null, '工科', '49');
INSERT INTO `d_dept` VALUES ('96', 'B-50', '中国食品科学技术学会', null, null, '1', null, null, null, '工科', '50');
INSERT INTO `d_dept` VALUES ('97', 'B-51', '中国粮油学会', null, null, '1', null, null, null, '工科', '51');
INSERT INTO `d_dept` VALUES ('98', 'B-52', '中国职业安全健康协会', null, null, '1', null, null, null, '工科', '52');
INSERT INTO `d_dept` VALUES ('99', 'B-53', '中国烟草学会', null, null, '1', null, null, null, '工科', '53');
INSERT INTO `d_dept` VALUES ('100', 'B-54', '中国仿真学会 ', null, null, '1', null, null, null, '工科', '54');
INSERT INTO `d_dept` VALUES ('101', 'B-55', '中国电影电视技术学会', null, null, '1', null, null, null, '工科', '55');
INSERT INTO `d_dept` VALUES ('102', 'B-56', '中国振动工程学会 ', null, null, '1', null, null, null, '工科', '56');
INSERT INTO `d_dept` VALUES ('103', 'B-57', '中国颗粒学会 ', null, null, '1', null, null, null, '工科', '57');
INSERT INTO `d_dept` VALUES ('104', 'B-58', '中国照明学会', null, null, '1', null, null, null, '工科', '58');
INSERT INTO `d_dept` VALUES ('105', 'B-59', '中国动力工程学会', null, null, '1', null, null, null, '工科', '59');
INSERT INTO `d_dept` VALUES ('106', 'B-60', '中国惯性技术学会', null, null, '1', null, null, null, '工科', '60');
INSERT INTO `d_dept` VALUES ('107', 'B-61', '中国风景园林学会 ', null, null, '1', null, null, null, '工科', '61');
INSERT INTO `d_dept` VALUES ('108', 'B-62', '中国电源学会', null, null, '1', null, null, null, '工科', '62');
INSERT INTO `d_dept` VALUES ('109', 'B-63', '中国复合材料学会 ', null, null, '1', null, null, null, '工科', '63');
INSERT INTO `d_dept` VALUES ('110', 'B-64', '中国消防协会', null, null, '1', null, null, null, '工科', '64');
INSERT INTO `d_dept` VALUES ('111', 'B-65', '中国图象图形学学会', null, null, '1', null, null, null, '工科', '65');
INSERT INTO `d_dept` VALUES ('112', 'B-66', '中国人工智能学会', null, null, '1', null, null, null, '工科', '66');
INSERT INTO `d_dept` VALUES ('113', 'B-67', '中国体视学学会', null, null, '1', null, null, null, '工科', '67');
INSERT INTO `d_dept` VALUES ('114', 'B-68', '中国工程机械学会', null, null, '1', null, null, null, '工科', '68');
INSERT INTO `d_dept` VALUES ('115', 'B-69', '中国海洋工程咨询协会', null, null, '1', null, null, null, '工科', '69');
INSERT INTO `d_dept` VALUES ('116', 'B-70', '中国遥感应用协会', null, null, '1', null, null, null, '工科', '70');
INSERT INTO `d_dept` VALUES ('117', 'B-71', '中国指挥与控制学会 ', null, null, '1', null, null, null, '工科', '71');
INSERT INTO `d_dept` VALUES ('118', 'B-72', '中国光学工程学会', null, null, '1', null, null, null, '工科', '72');
INSERT INTO `d_dept` VALUES ('119', 'B-73', '中国微米纳米技术学会', null, null, '1', null, null, null, '工科', '73');
INSERT INTO `d_dept` VALUES ('120', 'B-74', '中国密码学会', null, null, '1', null, null, null, '工科', '74');
INSERT INTO `d_dept` VALUES ('121', 'B-75', '中国大坝工程学会', null, null, '1', null, null, null, '工科', '75');
INSERT INTO `d_dept` VALUES ('122', 'B-76', '中国卫星导航定位协会', null, null, '1', null, null, null, '工科', '76');
INSERT INTO `d_dept` VALUES ('123', 'B-77W', '中国生物材料学会', null, null, '1', null, null, null, '工科', '77');
INSERT INTO `d_dept` VALUES ('124', 'B-78G', '国际粉体检测与控制联合会', null, null, '1', null, null, null, '工科', '78');
INSERT INTO `d_dept` VALUES ('125', 'C-01', '中国农学会', null, null, '1', null, null, null, '农科', '1');
INSERT INTO `d_dept` VALUES ('126', 'C-02', '中国林学会', null, null, '1', null, null, null, '农科', '2');
INSERT INTO `d_dept` VALUES ('127', 'C-03', '中国土壤学会', null, null, '1', null, null, null, '农科', '3');
INSERT INTO `d_dept` VALUES ('128', 'C-04', '中国水产学会', null, null, '1', null, null, null, '农科', '4');
INSERT INTO `d_dept` VALUES ('129', 'C-05', '中国园艺学会', null, null, '1', null, null, null, '农科', '5');
INSERT INTO `d_dept` VALUES ('130', 'C-06', '中国畜牧兽医学会', null, null, '1', null, null, null, '农科', '6');
INSERT INTO `d_dept` VALUES ('131', 'C-07', '中国植物病理学会', null, null, '1', null, null, null, '农科', '7');
INSERT INTO `d_dept` VALUES ('132', 'C-08', '中国植物保护学会', null, null, '1', null, null, null, '农科', '8');
INSERT INTO `d_dept` VALUES ('133', 'C-09', '中国作物学会', null, null, '1', null, null, null, '农科', '9');
INSERT INTO `d_dept` VALUES ('134', 'C-10', '中国热带作物学会', null, null, '1', null, null, null, '农科', '10');
INSERT INTO `d_dept` VALUES ('135', 'C-11', '中国蚕学会', null, null, '1', null, null, null, '农科', '11');
INSERT INTO `d_dept` VALUES ('136', 'C-12', '中国水土保持学会', null, null, '1', null, null, null, '农科', '12');
INSERT INTO `d_dept` VALUES ('137', 'C-13', '中国茶叶学会', null, null, '1', null, null, null, '农科', '13');
INSERT INTO `d_dept` VALUES ('138', 'C-14', '中国草学会', null, null, '1', null, null, null, '农科', '14');
INSERT INTO `d_dept` VALUES ('139', 'C-15', '中国植物营养与肥料学会', null, null, '1', null, null, null, '农科', '15');
INSERT INTO `d_dept` VALUES ('140', 'C-16W', '中国农业历史学会', null, null, '1', null, null, null, '农科', '16');
INSERT INTO `d_dept` VALUES ('141', 'D-01', '中华医学会', null, null, '1', null, null, null, '医科', '1');
INSERT INTO `d_dept` VALUES ('142', 'D-02', '中华中医药学会', null, null, '1', null, null, null, '医科', '2');
INSERT INTO `d_dept` VALUES ('143', 'D-03', '中国中西医结合学会', null, null, '1', null, null, null, '医科', '3');
INSERT INTO `d_dept` VALUES ('144', 'D-04', '中国药学会', null, null, '1', null, null, null, '医科', '4');
INSERT INTO `d_dept` VALUES ('145', 'D-05', '中华护理学会', null, null, '1', null, null, null, '医科', '5');
INSERT INTO `d_dept` VALUES ('146', 'D-06', '中国生理学会', null, null, '1', null, null, null, '医科', '6');
INSERT INTO `d_dept` VALUES ('147', 'D-07', '中国解剖学会', null, null, '1', null, null, null, '医科', '7');
INSERT INTO `d_dept` VALUES ('148', 'D-08', '中国生物医学工程学会', null, null, '1', null, null, null, '医科', '8');
INSERT INTO `d_dept` VALUES ('149', 'D-09', '中国病理生理学会', null, null, '1', null, null, null, '医科', '9');
INSERT INTO `d_dept` VALUES ('150', 'D-10', '中国营养学会', null, null, '1', null, null, null, '医科', '10');
INSERT INTO `d_dept` VALUES ('151', 'D-11', '中国药理学会', null, null, '1', null, null, null, '医科', '11');
INSERT INTO `d_dept` VALUES ('152', 'D-12', '中国针灸学会', null, null, '1', null, null, null, '医科', '12');
INSERT INTO `d_dept` VALUES ('153', 'D-13', '中国防痨协会', null, null, '1', null, null, null, '医科', '13');
INSERT INTO `d_dept` VALUES ('154', 'D-14', '中国麻风防治协会', null, null, '1', null, null, null, '医科', '14');
INSERT INTO `d_dept` VALUES ('155', 'D-15', '中国心理卫生协会', null, null, '1', null, null, null, '医科', '15');
INSERT INTO `d_dept` VALUES ('156', 'D-16', '中国抗癌协会', null, null, '1', null, null, null, '医科', '16');
INSERT INTO `d_dept` VALUES ('157', 'D-17', '中国体育科学学会', null, null, '1', null, null, null, '医科', '17');
INSERT INTO `d_dept` VALUES ('158', 'D-18', '中国毒理学会', null, null, '1', null, null, null, '医科', '18');
INSERT INTO `d_dept` VALUES ('159', 'D-19', '中国康复医学会', null, null, '1', null, null, null, '医科', '19');
INSERT INTO `d_dept` VALUES ('160', 'D-20', '中国免疫学会', null, null, '1', null, null, null, '医科', '20');
INSERT INTO `d_dept` VALUES ('161', 'D-21', '中华预防医学会', null, null, '1', null, null, null, '医科', '21');
INSERT INTO `d_dept` VALUES ('162', 'D-22', '中国法医学会', null, null, '1', null, null, null, '医科', '22');
INSERT INTO `d_dept` VALUES ('163', 'D-23', '中华口腔医学会', null, null, '1', null, null, null, '医科', '23');
INSERT INTO `d_dept` VALUES ('164', 'D-24', '中国医学救援协会', null, null, '1', null, null, null, '医科', '24');
INSERT INTO `d_dept` VALUES ('165', 'D-25', '中国女医师协会', null, null, '1', null, null, null, '医科', '25');
INSERT INTO `d_dept` VALUES ('166', 'D-26', '中国研究型医院学会', null, null, '1', null, null, null, '医科', '26');
INSERT INTO `d_dept` VALUES ('167', 'D-27W', '中国睡眠研究会', null, null, '1', null, null, null, '医科', '27');
INSERT INTO `d_dept` VALUES ('168', 'D-28W', '中国卒中学会', null, null, '1', null, null, null, '医科', '28');
INSERT INTO `d_dept` VALUES ('169', 'E-01', '中国自然辩证法研究会', null, null, '1', null, null, null, '交叉学科', '1');
INSERT INTO `d_dept` VALUES ('170', 'E-02', '中国管理现代化研究会', null, null, '1', null, null, null, '交叉学科', '2');
INSERT INTO `d_dept` VALUES ('171', 'E-03', '中国技术经济学会', null, null, '1', null, null, null, '交叉学科', '3');
INSERT INTO `d_dept` VALUES ('172', 'E-04', '中国现场统计研究会', null, null, '1', null, null, null, '交叉学科', '4');
INSERT INTO `d_dept` VALUES ('173', 'E-05', '中国未来研究会', null, null, '1', null, null, null, '交叉学科', '5');
INSERT INTO `d_dept` VALUES ('174', 'E-06', '中国科学技术史学会', null, null, '1', null, null, null, '交叉学科', '6');
INSERT INTO `d_dept` VALUES ('175', 'E-07', '中国科学技术情报学会', null, null, '1', null, null, null, '交叉学科', '7');
INSERT INTO `d_dept` VALUES ('176', 'E-08', '中国图书馆学会', null, null, '1', null, null, null, '交叉学科', '8');
INSERT INTO `d_dept` VALUES ('177', 'E-09', '中国城市科学研究会', null, null, '1', null, null, null, '交叉学科', '9');
INSERT INTO `d_dept` VALUES ('178', 'E-10', '中国科学学与科技政策研究会', null, null, '1', null, null, null, '交叉学科', '10');
INSERT INTO `d_dept` VALUES ('179', 'E-11', '中国农村专业技术协会', null, null, '1', null, null, null, '交叉学科', '11');
INSERT INTO `d_dept` VALUES ('180', 'E-12', '中国工业设计协会', null, null, '1', null, null, null, '交叉学科', '12');
INSERT INTO `d_dept` VALUES ('181', 'E-13', '中国工艺美术学会', null, null, '1', null, null, null, '交叉学科', '13');
INSERT INTO `d_dept` VALUES ('182', 'E-14', '中国科普作家协会', null, null, '1', null, null, null, '交叉学科', '14');
INSERT INTO `d_dept` VALUES ('183', 'E-15', '中国自然科学博物馆协会', null, null, '1', null, null, null, '交叉学科', '15');
INSERT INTO `d_dept` VALUES ('184', 'E-16', '中国可持续发展研究会', null, null, '1', null, null, null, '交叉学科', '16');
INSERT INTO `d_dept` VALUES ('185', 'E-17', '中国青少年科技辅导员协会', null, null, '1', null, null, null, '交叉学科', '17');
INSERT INTO `d_dept` VALUES ('186', 'E-18', '中国科教电影电视协会', null, null, '1', null, null, null, '交叉学科', '18');
INSERT INTO `d_dept` VALUES ('187', 'E-19', '中国科学技术期刊编辑学会', null, null, '1', null, null, null, '交叉学科', '19');
INSERT INTO `d_dept` VALUES ('188', 'E-20', '中国流行色协会', null, null, '1', null, null, null, '交叉学科', '20');
INSERT INTO `d_dept` VALUES ('189', 'E-21', '中国档案学会', null, null, '1', null, null, null, '交叉学科', '21');
INSERT INTO `d_dept` VALUES ('190', 'E-22', '中国国土经济学会', null, null, '1', null, null, null, '交叉学科', '22');
INSERT INTO `d_dept` VALUES ('191', 'E-23', '中国土地学会', null, null, '1', null, null, null, '交叉学科', '23');
INSERT INTO `d_dept` VALUES ('192', 'E-24', '中国科技新闻学会', null, null, '1', null, null, null, '交叉学科', '24');
INSERT INTO `d_dept` VALUES ('193', 'E-25', '中国老科学技术工作者协会', null, null, '1', null, null, null, '交叉学科', '25');
INSERT INTO `d_dept` VALUES ('194', 'E-26', '中国科学探险协会', null, null, '1', null, null, null, '交叉学科', '26');
INSERT INTO `d_dept` VALUES ('195', 'E-27', '中国城市规划学会', null, null, '1', null, null, null, '交叉学科', '27');
INSERT INTO `d_dept` VALUES ('196', 'E-28', '中国产学研合作促进会', null, null, '1', null, null, null, '交叉学科', '28');
INSERT INTO `d_dept` VALUES ('197', 'E-29', '中国知识产权研究会 ', null, null, '1', null, null, null, '交叉学科', '29');
INSERT INTO `d_dept` VALUES ('198', 'E-30', '中国发明协会', null, null, '1', null, null, null, '交叉学科', '30');
INSERT INTO `d_dept` VALUES ('199', 'E-32', '中国工程教育专业认证协会', null, null, '1', null, null, null, '交叉学科', '32');
INSERT INTO `d_dept` VALUES ('200', 'E-33', '中国检验检疫学会', null, null, '1', null, null, null, '交叉学科', '33');
INSERT INTO `d_dept` VALUES ('201', 'E-34', '中国女科技工作者协会', null, null, '1', null, null, null, '交叉学科', '34');
INSERT INTO `d_dept` VALUES ('202', 'E-35W', '中国创造学会', null, null, '1', null, null, null, '交叉学科', '35');
INSERT INTO `d_dept` VALUES ('203', 'E-36W', '中国经济科技开发国际交流协会', null, null, '1', null, null, null, '交叉学科', '36');
INSERT INTO `d_dept` VALUES ('204', 'E-37W', '中国高科技产业化研究会', null, null, '1', null, null, null, '交叉学科', '37');
INSERT INTO `d_dept` VALUES ('205', 'E-38W', '中国微量元素科学研究会', null, null, '1', null, null, null, '交叉学科', '38');
INSERT INTO `d_dept` VALUES ('206', 'E-39W', '中国国际经济技术合作促进会', null, null, '1', null, null, null, '交叉学科', '39');
INSERT INTO `d_dept` VALUES ('207', 'E-40W', '中国基本建设优化研究会', null, null, '1', null, null, null, '交叉学科', '40');
INSERT INTO `d_dept` VALUES ('208', 'E-41W', '中国科技馆发展基金会', null, null, '1', null, null, null, '交叉学科', '41');
INSERT INTO `d_dept` VALUES ('209', 'E-42W', '中国生物多样性保护与绿色发展基金会', null, null, '1', null, null, null, '交叉学科', '42');
INSERT INTO `d_dept` VALUES ('210', 'E-43W', '中国反邪教协会', null, null, '1', null, null, null, '交叉学科', '43');
INSERT INTO `d_dept` VALUES ('211', 'A-46G', '中国动物学会', null, null, '1', null, null, null, '理科', '46');


INSERT INTO `d_function` (`id`, `parent_id`, `function_name`, `display`, `state`, `icon`, `action`, `function_desc`, `insert_time`, `update_time`) VALUES ('19', '2', '学会管理', '1', '1', 'fa fa-users', '/api/core/dept/page', '5', '2018-01-10 17:19:19', '2018-01-10 17:19:19');
INSERT INTO `d_role_function` (`function_id`, `role_id`) VALUES ('19', '1');

