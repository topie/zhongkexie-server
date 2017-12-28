package com.topie.zhongkexie.core.service;

import com.topie.zhongkexie.common.baseservice.IService;
import com.topie.zhongkexie.database.core.model.ScorePaperUser;

/**
 * 
 * @author root
 *
 */
public interface IScorePagerUserService extends IService<ScorePaperUser>{

	void check(int id, short result);

}
