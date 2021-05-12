package com.wangze.core.service;

import com.wangze.core.entity.BaseDict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseDictService {
    //根据类别代码查询数据字典

    public List<BaseDict> findBaseDictByTypeCode(String typecode);


}
