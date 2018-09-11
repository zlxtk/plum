package com.zlxtk.boot.framework.security.service.impl;

import com.zlxtk.boot.framework.base.service.impl.BaseService;
import com.zlxtk.boot.framework.security.model.SysPermission;
import com.zlxtk.boot.framework.security.repository.SysPermissionRepository;
import com.zlxtk.boot.framework.security.service.ISysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 15:08
 */
@Slf4j
@Service
public class SysPermissionService extends BaseService<SysPermission, Long> implements ISysPermissionService {

    private SysPermissionRepository permissionRepository;

    @Autowired
    public SysPermissionService(SysPermissionRepository permissionRepository) {
        super(permissionRepository);
        this.permissionRepository = permissionRepository;
    }

    @Override
    public List<SysPermission> findAllByRoleCode(String roleCode) {
        return permissionRepository.findAllByRoleCode(roleCode);
    }

    /**
     * 整理权限列表的上下级
     * 数据库中查出来的是列表，用这个方法组装列表，使其整合为上下级结构
     *
     * @param permissions 无上下级结构的权限列表
     * @return
     */
    @Override
    public Map<String, SysPermission> formatPermission(List<SysPermission> permissions) {
        //以上级parentCode为keys组装成map
        Map<String, List<SysPermission>> map = new HashMap<>();
        Map<String, SysPermission> returnMap = new HashMap<>();
        for (SysPermission p : permissions) {
            if (StringUtils.isEmpty(p.getParentCode())) {
                p.setParentCode("");
            }
            if (p.getParentCode().equals("/")) {
                returnMap.put(p.getPermissionCode(), p);
            }
            if (map.get(p.getParentCode()) == null) {
                List<SysPermission> list = new ArrayList<>();
                list.add(p);
                map.put(p.getParentCode(), list);
            } else {
                map.get(p.getParentCode()).add(p);
            }
        }
        //循环组装
        for (SysPermission permission : permissions) {
            if(map.get(permission.getPermissionCode())==null){
                permission.setChilds(new ArrayList<SysPermission>());
            }else{
                permission.setChilds(map.get(permission.getPermissionCode()));
            }
        }
        return returnMap;
    }
}
