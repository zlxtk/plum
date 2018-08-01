package com.zlxtk.boot.plum.security.repository;

import com.zlxtk.boot.plum.base.repository.BaseRepository;
import com.zlxtk.boot.plum.security.model.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 15:02
 */
@Repository
public interface SysRoleRepository extends BaseRepository<SysRole,Long> {

    @Query(value = " SELECT r.* FROM sys_role AS r LEFT JOIN sys_user_role AS ur on r.roleCode=ur.roleCode " +
            " WHERE ur.username=:username AND r.enabled=1 AND ur.enabled=1 ", nativeQuery = true)
    List<SysRole> findAllByUsername(@Param("username") String username);

}
