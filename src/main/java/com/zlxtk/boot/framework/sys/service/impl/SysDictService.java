package com.zlxtk.boot.framework.sys.service.impl;


import com.zlxtk.boot.framework.base.service.impl.BaseService;
import com.zlxtk.boot.framework.sys.model.SysDict;
import com.zlxtk.boot.framework.sys.repository.SysDictRepository;
import com.zlxtk.boot.framework.sys.service.ISysDictService;
import com.zlxtk.boot.framework.sys.service.ISysDictValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictService extends BaseService<SysDict, String> implements ISysDictService {

    private SysDictRepository dictRepository;

    @Autowired
    private ISysDictValueService dictValueService;

    @Autowired
    public SysDictService(SysDictRepository dictRepository ) {
        super(dictRepository);
        this.dictRepository = dictRepository;
    }

    @Override
    public List<SysDict> findByParentId(String parentId) {
        return dictRepository.findByParentId(parentId);
    }

    @Override
    @Cacheable
    public List<SysDict> findByEnabled(Boolean enabled) {
        return dictRepository.findByEnabled(enabled);
    }



}
