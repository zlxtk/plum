package com.zlxtk.boot.framework.security.service;

import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.security.model.SysRole;

import java.util.List;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 15:05
 */
public interface ISysRoleService extends IBaseService<SysRole,Long> {

    List<SysRole> findAllByUsername(String username);
}
