package com.zlxtk.boot.plum.security.model;

import com.zlxtk.boot.plum.base.BaseModel;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SysUser extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false,length = 50)
    private String username;

    @Column(length = 50)
    private  String truename;

    @Column(length = 50)
    private String nickname;

}
