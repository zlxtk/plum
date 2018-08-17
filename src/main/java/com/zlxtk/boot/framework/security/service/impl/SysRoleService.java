package com.zlxtk.boot.framework.security.service.impl;

import com.zlxtk.boot.framework.base.service.impl.BaseService;
import com.zlxtk.boot.framework.security.model.SysRole;
import com.zlxtk.boot.framework.security.repository.SysRoleRepository;
import com.zlxtk.boot.framework.security.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 15:08
 */
@Slf4j
@Service
public class SysRoleService extends BaseService<SysRole,Long> implements ISysRoleService {

    private SysRoleRepository roleRepository;

    @Autowired
    public SysRoleService(SysRoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    @Override
    public List<SysRole> findAllByUsername(String username) {
        return roleRepository.findAllByUsername(username);
    }
}
