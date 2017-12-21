DROP TABLE IF EXISTS `d_common_statics`;
CREATE TABLE `d_common_statics` (
  `s_key` varchar(64) NOT NULL,
  `s_value` varchar(1000) DEFAULT NULL,
  `s_description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`s_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `d_message`;
CREATE TABLE `d_message` (
  `m_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sp_id` int(11) DEFAULT NULL COMMENT '评价表ID',
  `create_time` varchar(24) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `title` varchar(100) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
