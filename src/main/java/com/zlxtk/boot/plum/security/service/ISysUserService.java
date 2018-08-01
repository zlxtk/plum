package com.zlxtk.boot.plum.security.service;

import com.zlxtk.boot.plum.base.service.IBaseService;
import com.zlxtk.boot.plum.security.model.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 15:05
 */
public interface ISysUserService extends IBaseService<SysUser,Long>, UserDetailsService {

}
