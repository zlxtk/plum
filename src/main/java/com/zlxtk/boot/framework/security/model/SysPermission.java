package com.zlxtk.boot.framework.security.model;

import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;

import javax.persistence.*;

/**
 * @Description: 权限实体类
 * @Auther: tangyake
 * @Date: 2018/7/31 12:29
 */
@Data
@EqualsAndHashCode(callSuper = true)//equals和hashCode调研父类的方法 https://blog.csdn.net/zhanlanmg/article/details/50392266
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sys_permission")
public class SysPermission extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 100)
    private String permissionCode;//权限编码

    @Column(length = 100)
    private String parentCode;//上级权限编码

    @NonNull
    @Column(nullable = false, length = 30)
    private String permissionType;//权限类型

    @Column()
    private String permissionUrl;
}
