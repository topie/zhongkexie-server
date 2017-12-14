package com.topie.zhongkexie.core.service.impl;

import com.topie.zhongkexie.core.service.ICommonService;
import com.topie.zhongkexie.database.core.dao.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by chenguojun on 2017/6/27.
 */
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    private CommonMapper commonMapper;
    @Override
    public List<Map> selectByCommonTableBySql(String sql) {
        return commonMapper.selectByCommonTableBySql(sql);
    }
}
