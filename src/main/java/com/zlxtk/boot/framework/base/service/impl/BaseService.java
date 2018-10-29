package com.zlxtk.boot.framework.base.service.impl;

import com.google.common.collect.Sets;
import com.zlxtk.boot.framework.base.exception.InsertExistObjectException;
import com.zlxtk.boot.framework.base.exception.UpdateNotExistObjectException;
import com.zlxtk.boot.framework.base.model.BaseModel;
import com.zlxtk.boot.framework.base.repository.BaseRepository;
import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/7/31 20:24
 */
@Slf4j
public class BaseService<T extends BaseModel, PK extends Serializable> implements IBaseService<T, PK> {

    private BaseRepository<T, PK> baseRepository;

    public BaseService() {
    }

    public BaseService(BaseRepository<T, PK> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public List<T> findAll() {
        log.debug("@Base Repository Service getAll");
        return baseRepository.findAll();
    }

    @Override
    public Page<T> findAll(Specification<T> conditions, Pageable pageable) {
        log.debug("@Base Repository Service findAll Specification object PageSize:" + pageable.getPageSize() + ":PageNumber:" + pageable.getPageNumber());
        return baseRepository.findAll(conditions, pageable);
    }

    @Override
    public Iterable<T> findAll(Specification<T> var1) {
        log.debug("@Base Repository Service object by findAllByIDs");
        return baseRepository.findAll(var1);
    }

    @Override
    public Page<T> findAll(Pageable var1) {
        log.debug("@Base Repository Service findAll object PageSize:" + var1.getPageSize() + ":PageNumber:" + var1.getPageNumber());
        return baseRepository.findAll(var1);
    }

    @Override
    public T findById(PK id) {
        log.debug("@Base Repository Service get single object by id: " + id);
        return this.baseRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public T insert(T o) {
        if (null == ObjectUtil.getEntityIdVaue(o)) {
            log.debug("@Base Repository Service create new object: " + o);
            return baseRepository.save(o);
        } else {
            throw new InsertExistObjectException();
        }
    }

    @Override
    @Transactional
    public T update(T o) {
        if (null != ObjectUtil.getEntityIdVaue(o)) {
            log.debug("@Base Repository Service update a already object: " + o);
            return baseRepository.save(o);
        } else {
            throw new UpdateNotExistObjectException();
        }
    }

    @Override
    @Transactional
    public void deleteById(PK id) {
        log.debug("@Base Repository Service deleteById object by id: " + id);
        baseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(T o) {
        log.debug("@Base Repository Service delete object: " + o);
        baseRepository.delete(o);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends T> iterable) {
        log.debug("@Base Repository Service deleteAll Iterable param");
        baseRepository.deleteAll(iterable);
    }

    @Override
    @Transactional
    public void deleteAll() {
        log.debug("@Base Repository Service deleteAll null param");
        baseRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteAllByIds(Iterable<? extends PK> ids) {
        log.debug("@Base Repository Service deleteAllByIds Iterable param");
        Set<T> psSet = Sets.newHashSet();
        for (PK pk : ids) {
            psSet.add(baseRepository.findById(pk).orElse(null));
        }
        baseRepository.deleteAll(psSet);
    }

    @Override
    public Pageable getPageable(int page, int size, String direction, String properties) {
        int pagePage = page < 1 ? 0 : page - 1;
        int pageSize = size < 1 ? 1 : (size > 100 ? 100 : size);
        PageRequest pageable;
        if (StringUtils.isNotEmpty(direction) && StringUtils.isNotEmpty(properties)) {
            Sort.Direction sortDirection;
            try {
                direction = direction.toUpperCase();
                sortDirection = Sort.Direction.valueOf(direction);
            } catch (IllegalArgumentException var11) {
                var11.printStackTrace();
                sortDirection = org.springframework.data.domain.Sort.Direction.ASC;
            }

            String[] sortProperties = properties.split(",");
            Sort sort = new Sort(sortDirection, sortProperties);
            pageable = PageRequest.of(pagePage, pageSize, sort);
        } else {
            pageable = PageRequest.of(pagePage, pageSize);
        }

        return pageable;
    }
}
