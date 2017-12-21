ALTER TABLE d_score_answer
  ADD UNIQUE KEY upi(user_id, paper_id, item_id);
  
  ALTER TABLE d_score_paper_user add feedback varchar(2000); 

INSERT INTO d_function VALUES ('12', '7', '评价表审核', '1', '1', NULL, '/api/core/scorePaper/checkList', '1', NULL, NULL);
INSERT INTO d_role_function (role_id, function_id) VALUES ('1', '12');
INSERT INTO d_function VALUES ('13', '0', '学会填报', '1', '1', '', '#', '3', '2017-12-19 17:59:29', '2017-12-19 18:00:53');
INSERT INTO d_role_function (role_id, function_id) VALUES ('1', '13');
INSERT INTO d_function VALUES ('14', '13', '内容填报', '1', '1', '', '/api/core/scorePaper/reportList', '1', '2017-12-19 18:00:38', '2017-12-19 18:00:38');
INSERT INTO d_role_function (role_id, function_id) VALUES ('1', '14');
INSERT INTO d_function VALUES ('15', '13', '填报审核', '1', '1', '', '/api/core/scorePaper/reportCheck', '1', '2017-12-20 18:00:38', '2017-12-20 18:00:38');
INSERT INTO d_role_function (role_id, function_id) VALUES ('1', '15');

