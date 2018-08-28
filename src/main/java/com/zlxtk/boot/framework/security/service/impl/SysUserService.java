package com.zlxtk.boot.framework.security.service.impl;

import com.zlxtk.boot.framework.base.service.impl.BaseService;
import com.zlxtk.boot.framework.security.model.SysUser;
import com.zlxtk.boot.framework.security.repository.SysUserRepository;
import com.zlxtk.boot.framework.security.service.ISysRoleService;
import com.zlxtk.boot.framework.security.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 15:08
 */
@Slf4j
@Service
public class SysUserService extends BaseService<SysUser, Long> implements ISysUserService, UserDetailsService {

    private SysUserRepository userRepository;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    public SysUserService(SysUserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        user.setRoles(new HashSet<>(sysRoleService.findAllByUsername(username)));

        //TODO 用户菜单
        return user;
    }
}
