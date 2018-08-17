package com.zlxtk.boot.framework.sys.service;

import com.simbest.boot.base.repository.Condition;
import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.sys.model.SysDict;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ISysDictService extends IBaseService<SysDict, String> {
    List<SysDict> findByParentId(String parentId);
    List<SysDict> findByEnabled(Boolean enabled);
    List<SysDict> findByAll();

    SysDict  findById(String Id);

    SysDict save(SysDict dict);

    Specification<SysDict> getSpecification(Condition conditions);


}
