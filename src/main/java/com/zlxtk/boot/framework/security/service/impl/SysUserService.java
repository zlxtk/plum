package com.zlxtk.boot.framework.security.service.impl;

import com.zlxtk.boot.framework.base.service.impl.BaseService;
import com.zlxtk.boot.framework.security.model.SysPermission;
import com.zlxtk.boot.framework.security.model.SysRole;
import com.zlxtk.boot.framework.security.model.SysUser;
import com.zlxtk.boot.framework.security.repository.SysUserRepository;
import com.zlxtk.boot.framework.security.service.ISysPermissionService;
import com.zlxtk.boot.framework.security.service.ISysRoleService;
import com.zlxtk.boot.framework.security.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
    private ISysPermissionService sysPermissionService;

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

        Set<SysPermission> permissions = new HashSet<>();
        for (SysRole role : user.getRoles()) {
            permissions.addAll(role.getPermissions());
        }
        user.setMenus(sysPermissionService.formatPermission(new ArrayList<SysPermission>(permissions)));

        return user;
    }
}
