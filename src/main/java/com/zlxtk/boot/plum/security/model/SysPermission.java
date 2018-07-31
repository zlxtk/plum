package com.zlxtk.boot.plum.security.model;

import com.zlxtk.boot.plum.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Description: 权限实体类
 * @Auther: tangyake
 * @Date: 2018/7/31 12:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SysPermission extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
