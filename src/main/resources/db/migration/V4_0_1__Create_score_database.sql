DROP TABLE IF EXISTS d_score_index;
CREATE TABLE d_score_index (
  id        INT(11)       NOT NULL AUTO_INCREMENT
  COMMENT '节点ID',
  parent_id INT(11)       NOT NULL DEFAULT 0
  COMMENT '父节点ID',
  name      VARCHAR(128)  NOT NULL DEFAULT ''
  COMMENT '指标名称',
  score     DECIMAL(8, 4) NOT NULL DEFAULT 0.00
  COMMENT '指标分值',
  sort      INT(11)                DEFAULT 0
  COMMENT '排序',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '题库指标表';

DROP TABLE IF EXISTS d_score_item;
CREATE TABLE d_score_item (
  id           INT(11)       NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  index_id     INT(11)       NOT NULL DEFAULT 0
  COMMENT '指标ID',
  title        VARCHAR(1024) NOT NULL DEFAULT ''
  COMMENT '题目',
  type         INT(11)       NOT NULL DEFAULT 0
  COMMENT '题目回答类型：0 填空 1 单选 2 多选',
  option_logic VARCHAR(1024) NOT NULL DEFAULT ''
  COMMENT '填空评分逻辑 例如 eq(1)=>0;gte(1)=>0.5;',
  score        DECIMAL(8, 4) NOT NULL DEFAULT 0.00
  COMMENT '题目分值',
  sort         INT(11)                DEFAULT 0
  COMMENT '排序',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '题库题目表';


DROP TABLE IF EXISTS d_score_item_option;
CREATE TABLE d_score_item_option (
  id           INT(11)       NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  item_id      INT(11)       NOT NULL DEFAULT 0
  COMMENT '题目ID',
  option_title VARCHAR(1024) NOT NULL DEFAULT ''
  COMMENT '选项文本',
  option_rate  DECIMAL(8, 4) NOT NULL DEFAULT 0.00
  COMMENT '选项系数',
  option_desc  VARCHAR(128)  NOT NULL DEFAULT ''
  COMMENT '选项描述',
  option_sort  INT(11)       NOT NULL DEFAULT 0
  COMMENT '排序',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '题库题目选项表';

DROP TABLE IF EXISTS d_score_paper;
CREATE TABLE d_score_paper (
  id             INT(11)   NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  title          VARCHAR(255)       DEFAULT ''
  COMMENT '试卷标题',
  content_json   TEXT COMMENT '内容json',
  begin          TIMESTAMP NULL
  COMMENT '开始时间',
  end            TIMESTAMP NULL
  COMMENT '结束时间',
  create_time    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  update_time    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '修改时间',
  status         SMALLINT  NOT NULL DEFAULT 0
  COMMENT '状态',
  approve_status SMALLINT  NOT NULL DEFAULT 0
  COMMENT '审批状态',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '题库试卷表';

DROP TABLE IF EXISTS d_score_paper_user;
CREATE TABLE d_score_paper_user (
  paper_id INT(11)  NOT NULL  DEFAULT 0
  COMMENT '试卷ID',
  user_id  INT(11)  NOT NULL  DEFAULT 0
  COMMENT '用户ID',
  status   SMALLINT NOT NULL  DEFAULT 0
  COMMENT '状态',
  PRIMARY KEY (paper_id, user_id)
)
  DEFAULT CHARSET = utf8
  COMMENT '题库试卷用户关系表';


DROP TABLE IF EXISTS d_score_answer;
CREATE TABLE d_score_answer (
  id            INT(11)       NOT NULL AUTO_INCREMENT
  COMMENT 'ID',
  user_id       INT(11)       NOT NULL DEFAULT 0
  COMMENT '用户ID',
  paper_id      INT(11)       NOT NULL DEFAULT 0
  COMMENT '试卷ID',
  index_id      INT(11)       NOT NULL DEFAULT 0
  COMMENT '指标ID',
  item_id       INT(11)       NOT NULL DEFAULT 0
  COMMENT '题目ID',
  item_score    DECIMAL(8, 4) NOT NULL DEFAULT 0.00
  COMMENT '题目分值',
  answer_value  VARCHAR(255)  NOT NULL DEFAULT ''
  COMMENT '回答值',
  answer_score  DECIMAL(8, 4) NOT NULL DEFAULT 0.00
  COMMENT '答题分值',
  answer_reason VARCHAR(1024) NOT NULL DEFAULT ''
  COMMENT '答题分值判定明细',
  PRIMARY KEY (id)
)
  DEFAULT CHARSET = utf8
  COMMENT '题库评审填报表';


INSERT INTO d_function VALUES ('7', '0', '评价表管理', '1', '1', NULL, '#', '3', NULL, NULL);
INSERT INTO d_role_function (role_id, function_id) VALUES ('1', '7');
INSERT INTO d_function VALUES ('8', '7', '评价指标管理', '1', '1', NULL, '/api/core/scoreIndex/list', '1', NULL, NULL);
INSERT INTO d_role_function (role_id, function_id) VALUES ('1', '8');
INSERT INTO d_function VALUES ('9', '7', '评价表题目管理', '1', '1', NULL, '/api/core/scoreItem/list', '2', NULL, NULL);
INSERT INTO d_role_function (role_id, function_id) VALUES ('1', '9');
INSERT INTO d_function VALUES ('10', '7', '评价表试卷管理', '1', '1', NULL, '/api/core/scorePaper/list', '3', NULL, NULL);
INSERT INTO d_role_function (role_id, function_id) VALUES ('1', '10');

