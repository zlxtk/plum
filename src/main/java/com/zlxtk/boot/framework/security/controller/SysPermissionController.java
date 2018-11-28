package com.zlxtk.boot.framework.security.controller;

import com.github.wenhao.jpa.Specifications;
import com.zlxtk.boot.framework.base.web.controller.BaseController;
import com.zlxtk.boot.framework.base.web.response.JsonResponse;
import com.zlxtk.boot.framework.security.model.SysPermission;
import com.zlxtk.boot.framework.security.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 18:08
 */
@Controller
@RequestMapping("/sys/permission")
public class SysPermissionController extends BaseController<SysPermission, Long> {

    private ISysPermissionService permissionService;

    @Autowired
    public SysPermissionController(ISysPermissionService permissionService) {
        super(permissionService);
        this.permissionService=permissionService;
    }


    /**
     * 根据moduleCode，下线模块下的所有权限，并分级
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/findAllChildsFromModuleCodeNoPageAjax")
    public JsonResponse findAllChildsFromModuleCodeNoPageAjax(String moduleCode) {
        Specification<SysPermission> specification = Specifications.<SysPermission>and()
                .eq("state", 1)
                .eq("moduleCode",moduleCode)
                .build();
        List<SysPermission> permissions= (List<SysPermission>) permissionService.findAll(specification);
        //设置上下级
        Map<String, List<SysPermission>> map = new HashMap<>();
        SysPermission returnP = null;
        for (SysPermission p : permissions) {
            if(StringUtils.isEmpty(p.getParentCode())){
                p.setParentCode("");
            }
            if (p.getPermissionCode().equals(moduleCode)) {
                returnP=p;
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
            permission.setChilds(map.get(permission.getPermissionCode()));
        }
        return JsonResponse.success(returnP);
    }
}
