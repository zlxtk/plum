package com.zlxtk.boot.framework.security.controller;

import com.github.wenhao.jpa.Specifications;
import com.zlxtk.boot.framework.base.web.controller.BaseController;
import com.zlxtk.boot.framework.base.web.response.JsonResponse;
import com.zlxtk.boot.framework.security.model.SysPermission;
import com.zlxtk.boot.framework.security.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/8/1 18:08
 */
@Controller
public class SysPermissionController extends BaseController<SysPermission, Long> {

    private ISysPermissionService permissionService;

    @Autowired
    public SysPermissionController(ISysPermissionService permissionService) {
        super(permissionService);
        this.permissionService=permissionService;
    }


    /**
     * 基础分页查询，查询条件为对象中不为null的属性
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/findChildMenuNoPageAjax")
    public JsonResponse findChildMenuNoPageAjax(String moduleCode) {
        Specification<SysPermission> specification = Specifications.<SysPermission>and()
                .eq("state", 1)
                .eq("parentCode",moduleCode)
                .eq("permissionType.value","MENU")
                .build();
        List<SysPermission> permissions= (List<SysPermission>) permissionService.findAll(specification);
        return JsonResponse.success(permissions);
    }
}
