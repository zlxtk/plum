package com.zlxtk.boot.framework.security.repository;

import com.zlxtk.boot.framework.base.repository.BaseRepository;
import com.zlxtk.boot.framework.security.model.SysPermission;
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
public interface SysPermissionRepository extends BaseRepository<SysPermission,Long> {

    @Query(value = " SELECT r.* FROM sys_permission AS r LEFT JOIN sys_role_permission AS ur on r.permissionCode=ur.permissionCode " +
            " WHERE ur.roleCode=:roleCode AND r.state=1 AND ur.state=1 ", nativeQuery = true)
    List<SysPermission> findAllByRoleCode(@Param("roleCode") String roleCode);

}
