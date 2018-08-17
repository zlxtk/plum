package com.zlxtk.boot.framework.security.service;

import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.security.model.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 15:05
 */
public interface ISysUserService extends IBaseService<SysUser,Long>, UserDetailsService {

}
