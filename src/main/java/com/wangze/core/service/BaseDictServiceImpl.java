package com.wangze.core.service;

import com.wangze.core.dao.BaseDictDao;
import com.wangze.core.entity.BaseDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 王泽20
 */
@Service("baseDictService")
public class BaseDictServiceImpl implements BaseDictService{
    @Autowired
    private BaseDictDao baseDictDao;

    @Override
    public List<BaseDict> findBaseDictByTypeCode(String typecpde) {
        return baseDictDao.selectBaseDictByTypeCode(typecpde);
    }
}
