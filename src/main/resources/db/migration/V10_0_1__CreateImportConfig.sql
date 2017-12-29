INSERT INTO `d_function` (`id`, `parent_id`, `function_name`, `display`, `state`, `icon`, `action`, `function_desc`, `insert_time`, `update_time`) VALUES ('17', '7', '导入配置', '1', '1', 'fa fa-file-code-o', '/api/core/importConf/list', '5', '2017-12-27 15:58:01', '2017-12-27 15:59:52');
INSERT INTO `d_role_function` (`function_id`, `role_id`) VALUES ('17', '1');
UPDATE `d_function` SET `id`='1', `parent_id`='0', `function_name`='首页', `display`='1', `state`='1', `icon`='fa fa-home', `action`='/api/index', `function_desc`='1', `insert_time`='2017-12-21 21:28:15', `update_time`='2017-12-27 16:23:24' WHERE (`id`='1');
DROP TABLE IF EXISTS d_score_paper_import_conf;
CREATE TABLE `d_score_paper_import_conf` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_id` int(11) DEFAULT NULL COMMENT '附件ID',
  `paper_id` int(11) DEFAULT NULL COMMENT '评价表ID',
  `start` int(11) NOT NULL COMMENT '开始行数',
  `end` int(11) NOT NULL COMMENT '结束行数',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `dept_mapping` varchar(1000) DEFAULT NULL COMMENT '部门的对应关系excel->database{a:b}',
  `field_mapping` varchar(1000) DEFAULT NULL COMMENT '领域的对应关系excel->database{"事业部":"国防事业部门","事业部1":"国防事业部门1"}',
  `json_index` varchar(1000) DEFAULT NULL COMMENT '指标的列[{index:0,score:1},{index:2,score:3},{index:4,score:5},{index:6,score:7}]',
  `json_item` varchar(255) DEFAULT NULL COMMENT '题目的列{title:8,score:9,desc:10,org:12}',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='指标导入配置表';


INSERT INTO `d_score_paper_import_conf` (`id`, `file_id`, `paper_id`, `start`, `end`, `name`, `dept_mapping`, `field_mapping`, `json_index`, `json_item`, `create_time`) VALUES ('1', NULL, NULL, '3', '232', '2018年导入表格配置（四级指标）', '{}', '{}', '[{index:0,score:1},{index:2,score:3},{index:4,score:5},{index:6,score:7}]', '{title:8,score:9,desc:10,org:12}', NULL);
