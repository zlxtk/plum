package com.zlxtk.boot.framework.sys.repository;

import com.zlxtk.boot.framework.base.repository.BaseRepository;
import com.zlxtk.boot.framework.sys.model.SysDict;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictRepository extends BaseRepository<SysDict, String> {

    //    String SQL = "SELECT * FROM sys_dict WHERE parent_id=:id";
//    @Query(value = SQL, nativeQuery = true)
//    List<SysDict> findByParentId(@Param("id")Long parentId);
    List<SysDict> findByParentId(String parentId);

    List<SysDict> findByParentIdAndEnabled(String parentId, Boolean enabled);

    List<SysDict> findByEnabled(Boolean enabled);


}
