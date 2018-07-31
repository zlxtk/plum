package com.zlxtk.boot.plum.security.model;

import com.zlxtk.boot.plum.base.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Description: 角色实体类
 * @Auther: tangyake
 * @Date: 2018/7/31 12:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SysRole extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
