package com.zlxtk.boot.framework.security.service;

import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.security.model.SysPermission;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 15:05
 */
public interface ISysPermissionService extends IBaseService<SysPermission, Long> {

    List<SysPermission> findAllByRoleCode(String roleCode);

    Map<String, SysPermission> formatPermission(List<SysPermission> permissions);

}
