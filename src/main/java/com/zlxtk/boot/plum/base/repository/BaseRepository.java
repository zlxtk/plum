package com.zlxtk.boot.plum.base.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: tangyake
 * @Date: 2018/7/31 20:14
 */
@NoRepositoryBean
public interface BaseRepository<T, PK extends Serializable> extends JpaRepository<T, PK>, JpaSpecificationExecutor<T> {

}

