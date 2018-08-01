/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.plum.base.web.controller;

import com.github.wenhao.jpa.PredicateBuilder;
import com.github.wenhao.jpa.Sorts;
import com.github.wenhao.jpa.Specifications;
import com.zlxtk.boot.plum.base.exception.GlobalExceptionRegister;
import com.zlxtk.boot.plum.base.model.BaseModel;
import com.zlxtk.boot.plum.base.service.IBaseService;
import com.zlxtk.boot.plum.base.web.response.JsonResponse;
import com.zlxtk.boot.plum.util.CustomBeanUtil;
import com.zlxtk.boot.plum.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;


/**
 * 基础 Controller
 * @param <T>
 * @param <PK>
 */
@Slf4j
public class BaseController<T extends BaseModel, PK extends Serializable> {

    private IBaseService<T, PK> service;

    public BaseController(IBaseService<T, PK> service) {
        this.service = service;
    }

    @PostMapping(value = "/findById")
    public JsonResponse findById(@RequestParam PK id) {
        return JsonResponse.success(service.findById(id));
    }

    /**
     * 基础分页查询，查询条件为对象中不为null的属性
     * @param page 页码
     * @param size 每页大小
     * @param direction 排序规则，只有asc 和desc
     * @param properties
     * @param o
     * @return
     */
    @PostMapping(value = "/findAll")
    public JsonResponse findAll(@RequestParam(required = false, defaultValue = "1") int page, //
                                @RequestParam(required = false, defaultValue = "10") int size, //
                                @RequestParam(required = false) String direction, //
                                @RequestParam(required = false) String properties,
                                @RequestBody T o) {
        //分页查询
        Sort sort = Sorts.builder().desc("id").build();
        if(!StringUtils.isEmpty(properties)){
            sort = Sorts.builder().asc(direction.equals("asc"),properties).build();
            sort = Sorts.builder().desc(direction.equals("desc"),properties).build();
        }

        PageRequest pageRequest = PageRequest.of(page <= 0 ? 0 : page - 1, size, sort);

        Map<String, Object> params = ObjectUtil.getEntityPersistentFieldValueExceptId(o);
        PredicateBuilder<T> predicateBuilder= Specifications.<T>and();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if(entry.getValue()!=null&&entry.getValue().toString().trim()!=""){
                predicateBuilder.eq(entry.getKey(), entry.getValue());
            }
        }

        Specification<T> specification =predicateBuilder.build();
        // 获取查询结果
        Page<T> pages = service.findAll(specification, pageRequest);
        return JsonResponse.success(null);
    }

    @PostMapping(value = "/create")
    public JsonResponse create(@RequestBody T o) {
        try {
            o = service.insert(o);
            return JsonResponse.success(o);
        } catch (Exception e) {
            return GlobalExceptionRegister.returnErrorResponse(e);
        }
    }

    /**
     * 更新对象，只会更新对象中不为空的属性，id必须有值
     * @param newObj
     * @return
     */
    @PostMapping(value = "/update")
    public JsonResponse update(@RequestBody T newObj) {
        T oldObj = service.findById((PK) ObjectUtil.getEntityIdVaue(newObj));
        CustomBeanUtil.copyPropertiesIgnoreNull(newObj, oldObj);
        try {
            oldObj = service.update(oldObj);
            return JsonResponse.success(oldObj);
        } catch (Exception e) {
            return GlobalExceptionRegister.returnErrorResponse(e);
        }
    }

    @PostMapping(value = "/deleteById")
    public JsonResponse deleteById(@RequestParam PK id) {
        try {
            service.deleteById(id);
            return JsonResponse.defaultSuccessResponse();
        } catch (Exception e) {
            return GlobalExceptionRegister.returnErrorResponse(e);
        }
    }

    @PostMapping(value = "/delete")
    public JsonResponse delete(@RequestBody T o) {
        try {
            service.delete(o);
            return JsonResponse.defaultSuccessResponse();
        } catch (Exception e) {
            return GlobalExceptionRegister.returnErrorResponse(e);
        }
    }

    @PostMapping(value = "/deleteAllByIds")
    public JsonResponse deleteAllByIds(@RequestBody PK[] ids) {
        try {
            service.deleteAllByIds(Arrays.asList(ids));
            return JsonResponse.defaultSuccessResponse();
        } catch (Exception e) {
            return GlobalExceptionRegister.returnErrorResponse(e);
        }
    }

}
