package com.zlxtk.boot.plum.base.service;

import com.zlxtk.boot.plum.base.model.BaseModel;
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

    T insert(T var1);

    T update(T var1);

    void deleteById(PK var1);

    void delete(T var1);

    void deleteAll(Iterable<? extends T> var1);

    void deleteAll();

    void deleteAllByIds(Iterable<? extends PK> var1);
}
