ALTER TABLE `d_score_paper`
ADD COLUMN `score`  decimal(8,4) NULL AFTER `approve_status`;
ALTER TABLE `d_score_paper`
ADD COLUMN `falsity_count` int(3) DEFAULT '3' COMMENT '总虚假次数超过该值试卷为零' AFTER `approve_status`;
ALTER TABLE `d_score_paper`
ADD COLUMN `falsity_count_item` int(3) DEFAULT '3' COMMENT '四级指标超过该个数这个指标得分为零' AFTER `approve_status`;
ALTER TABLE `d_score_index`
ADD COLUMN `weight`  decimal(8,4) NULL ;
ALTER TABLE `d_score_item`
ADD COLUMN `weight`  decimal(8,4) NULL ;

ALTER TABLE `d_score_paper_user`
ADD COLUMN `score` decimal(8,4) DEFAULT '0.0000' COMMENT '得分';
ALTER TABLE `d_score_paper_user`
ADD COLUMN `subjective_score` decimal(8,4) DEFAULT '0.0000' COMMENT '主观分 附加分';