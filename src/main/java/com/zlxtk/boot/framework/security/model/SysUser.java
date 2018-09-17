package com.zlxtk.boot.framework.security.model;

import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@EqualsAndHashCode(callSuper = true)//equals和hashCode调研父类的方法 https://blog.csdn.net/zhanlanmg/article/details/50392266
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(length = 50)
    private String truename;

    @Column(length = 50)
    private String nickname;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String email;

    @Column(length = 2)
    private String sex;

    @Column(length = 13, unique = true)
    private String phone;

    @Transient
    private Set<SysRole> roles;

    @Transient
    private Map<String, SysPermission> menus;//用户的菜单<MODULE权限的code,MODULE菜单>

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        Set<SysRole> roles = this.getRoles();
        for (SysRole role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }
        return auths;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

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
        SysUser rhs = (SysUser) obj;
        return new EqualsBuilder()
                .append(getUsername(), rhs.getUsername())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getUsername())
                .toHashCode();
    }
}
