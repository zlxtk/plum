package com.zlxtk.boot.framework.security.model;

import com.zlxtk.boot.framework.base.model.BaseModel;
import com.zlxtk.boot.framework.security.enums.SysPermissionTypeEnum;
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
    private String permissionCode;//权限编码,根为/，必须以上级权限编码为前缀，比如/parentCode/childCode

    @Column(length = 100)
    private String parentCode;//上级权限编码

    @Column
    private String description;//描述

    @NonNull
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private SysPermissionTypeEnum permissionType;//权限类型

    @Column()
    private String permissionUrl;

    @Column(length = 50)
    private String icon;//图标

    @NonNull
    @Column(length = 3)
    private Integer permissionLevel; //权限级别,共3级，根为0级，模块为1级，访问菜单为2级、3级，按钮和执行方法为3级

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


    public String getPermissionType() {
        return this.permissionType.getValue();
    }
}
