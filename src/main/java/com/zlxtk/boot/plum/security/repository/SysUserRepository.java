package com.zlxtk.boot.plum.security.repository;

import com.zlxtk.boot.plum.base.repository.BaseRepository;
import com.zlxtk.boot.plum.security.model.SysUser;
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
public interface SysUserRepository extends BaseRepository<SysUser, Long> {

    SysUser findByUsername(String username);

    @Query(value = " SELECT u.* FROM sys_user AS u LEFT JOIN sys_user_role AS ur on ur.username=u.username " +
            "WHERE ur.roleCode=:roleCode AND u.enabled=1 AND ur.enabled=1 ", nativeQuery = true)
    List<SysUser> findAllByRoleCode(@Param("roleCode") String roleCode);

}
