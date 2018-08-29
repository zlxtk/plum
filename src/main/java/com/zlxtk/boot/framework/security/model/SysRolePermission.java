package com.zlxtk.boot.framework.security.model;

import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;

import javax.persistence.*;

/**
 * @Description: 角色权限实体类
 * @Auther: tangyake
 * @Date: 2018/7/31 12:29
 */
@Data
@EqualsAndHashCode(callSuper=true)//equals和hashCode调研父类的方法 https://blog.csdn.net/zhanlanmg/article/details/50392266
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sys_role_permission")
public class SysRolePermission extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String roleCode;

    @NonNull
    @Column(nullable = false)
    private String permissionCode;
}
