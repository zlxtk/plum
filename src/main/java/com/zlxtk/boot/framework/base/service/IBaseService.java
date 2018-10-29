package com.zlxtk.boot.framework.base.service;

import com.zlxtk.boot.framework.base.model.BaseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/7/31 20:20
 */
public interface IBaseService<T extends BaseModel, PK extends Serializable> {

    List<T> findAll();

    Page<T> findAll(Specification<T> var1, Pageable var2);

    Iterable<T> findAll(Specification<T> var1);

    Page<T> findAll(Pageable var1);

    T findById(PK id);

    T insert(T o);

    T update(T o);

    void deleteById(PK id);

    void delete(T o);

    void deleteAll(Iterable<? extends T> iterable);

    void deleteAll();

    void deleteAllByIds(Iterable<? extends PK> ids);

    Pageable getPageable(int page, int size, String direction, String properties);
}
