package com.zlxtk.boot.framework.sys.service;

import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.sys.model.SysDictValue;

import java.util.List;

public interface ISysDictValueService extends IBaseService<SysDictValue,Long> {

    int updateEnable(boolean enabled, Long dictValueId);

    List<SysDictValue> findByParentId(Long parentId);

    void deleteById(Long id);



}
