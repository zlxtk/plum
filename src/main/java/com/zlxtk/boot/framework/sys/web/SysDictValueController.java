/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.sys.web;

import com.zlxtk.boot.framework.base.web.controller.BaseController;
import com.zlxtk.boot.framework.base.web.response.JsonResponse;
import com.zlxtk.boot.framework.sys.model.SysDictValue;
import com.zlxtk.boot.framework.sys.service.ISysDictService;
import com.zlxtk.boot.framework.sys.service.ISysDictValueService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用途：数据字典值控制器
 * 作者: zlxtk
 * 时间: 2018/2/23  10:14
 */
@RestController
@RequestMapping("/sys/dictValue")
public class SysDictValueController extends BaseController<SysDictValue,Long> {

    private ISysDictValueService sysDictValueService;

    @Autowired
    private ISysDictService dictService;

    @Autowired
    public SysDictValueController(ISysDictValueService sysDictValueService) {
        super(sysDictValueService);
        this.sysDictValueService=sysDictValueService;
    }

    /**
     * 新增一个字典值
     * @param sysDictValue
     * @return
     */
    //设置权限，后面再开启
    //@PreAuthorize ("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
    @ApiOperation(value = "新增一个字典值", notes = "新增一个字典值")
    public JsonResponse create(@RequestBody(required = false) SysDictValue sysDictValue) {
        return super.createAjax( sysDictValue );
    }

    /**
     * 修改一个字典值
     * @param sysDictValue
     * @return
     */
    //设置权限，后面再开启
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
    @ApiOperation(value = "修改一个字典值", notes = "修改一个字典值")
    public JsonResponse update( @RequestBody(required = false) SysDictValue sysDictValue) {
        return super.updateAjax(sysDictValue );
    }

    /**
     * 根据id逻辑删除
     * @param id
     * @return
     */
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER','ROLE_ADMIN')")
    @ApiOperation(value = "根据id删除字典值", notes = "根据id删除字典值")
    @ApiImplicitParam (name = "id", value = "字典值ID",  dataType = "Integer", paramType = "query")
    public JsonResponse deleteById(@RequestParam(required = false) String id) {
        return super.deleteByIdAjax( Long.parseLong(id)  );
    }

    /**
     * 先修改再逻辑删除字典值
     * @param sysDictValue
     * @return
     */
    @ApiOperation(value = "先修改再逻辑删除字典值", notes = "先修改再逻辑删除字典值")
    public JsonResponse delete(SysDictValue sysDictValue) {
        return super.deleteAjax(sysDictValue);
    }

    /**
     * 批量逻辑删除字典值
     * @param ids
     * @return JsonResponse
     */
    //@PreAuthorize("hasAuthority('ROLE_SUPER')")  // 指定角色权限才能操作方法
    @ApiOperation(value = "批量逻辑删除字典值", notes = "批量逻辑删除字典值")
    public JsonResponse deleteAllByIds(@RequestBody(required = false) String[] ids) {
        Long[] idsL = new Long[ids.length];
        for(int i=0;i<ids.length;i++){
            idsL[i]=Long.parseLong(ids[i]);
        }
        return  super.deleteAllByIdsAjax(idsL);
    }

    /**
     *修改可见
     * @param id
     * @param enabled
     * @return
     */
    @ApiOperation(value = "修改可见", notes = "修改可见")
    @ApiImplicitParams ({@ApiImplicitParam(name = "id", value = "角色ID", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "enabled", value = "是否可用", required = true, dataType = "Boolean", paramType = "query")
    })
    public JsonResponse updateEnable(@RequestParam(required = false) String id, @RequestParam(required = false) Boolean enabled) {
        return  JsonResponse.success(sysDictValueService.updateEnable(enabled,Long.parseLong(id) ));
    }

    //批量修改可见

    /**
     *根据id查询字典值
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询字典值", notes = "根据id查询字典值")
    @ApiImplicitParam(name = "id", value = "字典类型ID", dataType = "Integer", paramType = "query")
    @PostMapping(value = {"/findById","/findById/sso"})
    public JsonResponse findById(@RequestParam(required = false) String id) {
        return super.findByIdAjax( Long.parseLong(id)  );
    }

    /**
     *获取字典值信息列表并分页
     * @param page
     * @param size
     * @param direction
     * @param properties
     * @param sysDictValue
     * @return
     */
    @ApiOperation(value = "获取字典值信息列表并分页", notes = "获取字典值信息列表并分页")
    @ApiImplicitParams({ //
            @ApiImplicitParam(name = "page", value = "当前页码", dataType = "int", paramType = "query", //
                    required = true, example = "1"), //
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "query", //
                    required = true, example = "10"), //
            @ApiImplicitParam(name = "direction", value = "排序规则（asc/desc）", dataType = "String", //
                    paramType = "query"), //
            @ApiImplicitParam(name = "properties", value = "排序规则（属性名称）", dataType = "String", //
                    paramType = "query") //
    })
    @PostMapping(value = {"/findAll","/findAll/sso"})
    public JsonResponse findAll(@RequestParam(required = false, defaultValue = "1") int page, //
                                @RequestParam(required = false, defaultValue = "10") int size, //
                                @RequestParam(required = false) String direction, //
                                @RequestParam(required = false) String properties, //
                                @RequestBody(required = false) SysDictValue sysDictValue //
    ) {
        return super.findAllAjax( page,size,direction, properties,sysDictValue);
    }



    /**
     * 新增子字典值
     * @param dictValue
     * @return
     */
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")  // 指定角色权限才能操作方法
    @PostMapping(value = "/createChild")
    @ResponseBody
    public JsonResponse createChild(@RequestBody(required = false) SysDictValue dictValue) {
        if (dictValue == null) {
            return JsonResponse.defaultErrorResponse();
        }
        dictValue.setParentId(dictValue.getId());
        dictValue.setId(null);
        SysDictValue newSysDictValue = sysDictValueService.insert(dictValue);
        return JsonResponse.defaultSuccessResponse();
    }

}
