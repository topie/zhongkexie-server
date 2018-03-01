ALTER TABLE d_score_answer add answer_real bit(1) DEFAULT b'1' COMMENT '答题真实性';
UPDATE `d_score_answer` SET `answer_real`=true ;

