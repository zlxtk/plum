package com.zlxtk.boot.framework.sys.service;

import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.sys.model.SysDict;

import java.util.List;

public interface ISysDictService extends IBaseService<SysDict, String> {

    List<SysDict> findByParentId(String parentId);

    List<SysDict> findByEnabled(Boolean enabled);

}
