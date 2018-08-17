package com.zlxtk.boot.framework.security.model;

import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * @Description: 角色实体类
 * @Auther: tangyake
 * @Date: 2018/7/31 12:29
 */
@Data
@EqualsAndHashCode(callSuper = true)//equals和hashCode调研父类的方法 https://blog.csdn.net/zhanlanmg/article/details/50392266
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 50)
    private String roleCode; //角色编码

    @NonNull
    @Column(nullable = false, length = 50)
    private String roleName; //角色名

    @Transient
    private Set<SysUser> users;

    @Transient
    private Set<SysPermission> permissions;
}
