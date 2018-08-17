/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.sys.model;


import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;

import javax.persistence.*;

/**
 * 用途：数据字典
 * 作者: lishuyi
 * 时间: 2018/1/30  17:17
 */
@Data
@EqualsAndHashCode(callSuper = true)//equals和hashCode调研父类的方法 https://blog.csdn.net/zhanlanmg/article/details/50392266
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SysDict extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, length = 255)
    private String dictType;

    @Column(nullable = false, length = 50)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer displayOrder;

    @Column
    private String parentId;
}
