package com.zlxtk.boot.framework.sys.repository;

import com.zlxtk.boot.framework.base.repository.BaseRepository;
import com.zlxtk.boot.framework.sys.model.SysDict;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictRepository extends BaseRepository<SysDict, Long> {

    List<SysDict> findByParentId(Long parentId);

    List<SysDict> findByState(Integer enabled);


}
