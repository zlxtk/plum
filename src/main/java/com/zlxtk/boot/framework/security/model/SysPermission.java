package com.zlxtk.boot.framework.security.model;

import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.List;

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
    @Column(nullable = false, length = 100, unique = true)
    private String permissionCode;//权限编码

    @NonNull
    @Column(nullable = false, length = 100)
    private String permissionName;//权限名称

    @Column(length = 100)
    private String parentCode;//上级权限编码

    @Column(length = 100)
    private String moduleCode;//所属module编码，方便查询用，新增时自动设置

    @Column
    private String description;//描述

    /**
     * 根，只有一个/,下级全是module或admin_module
     * 每个系统模块有前台和后台两个权限模块，
     *      比如shop系统模块，在权限中有/shop(前台)和/shop/admin(后台)两个权限模块数据
     */
    @NonNull
    @Column(nullable = false, length = 30)
    private String permissionType;//权限类型,对应SysPermissionTypeConstants中的常量

    /**
     * 级别
     */
    @NonNull
    @Column(length = 3)
    private Integer permissionLevel; //权限级别

    @Column()
    private String permissionUrl;//权限url

    @Column(length = 50)
    private String icon;//图标

    @Transient
    private List<SysPermission> childs;//下级权限

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SysPermission rhs = (SysPermission) obj;
        return new EqualsBuilder()
                .append(getPermissionCode(), rhs.getPermissionCode())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPermissionCode())
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
