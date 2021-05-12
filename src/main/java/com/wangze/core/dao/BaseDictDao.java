package com.wangze.core.dao;

import com.wangze.core.entity.BaseDict;

import java.util.List;

public interface BaseDictDao {
    List<BaseDict> selectBaseDictByTypeCode(String typecode);


}
