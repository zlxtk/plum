package com.zlxtk.boot.framework.sys.service;

import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.sys.model.SysDictValue;

import java.util.List;

public interface ISysDictValueService extends IBaseService<SysDictValue,String> {

    int updateEnable(boolean enabled, String dictValueId);

    List<SysDictValue> findByParentId(String parentId);

    void deleteById(String id);



}
