ALTER TABLE `d_score_paper`
ADD COLUMN `score`  decimal(8,4) NULL AFTER `approve_status`;
ALTER TABLE `d_score_index`
ADD COLUMN `weight`  decimal(8,4) NULL ;
ALTER TABLE `d_score_item`
ADD COLUMN `weight`  decimal(8,4) NULL ;