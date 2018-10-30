package com.zlxtk.boot.framework.erp.order.model;

import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Set;

/**
 * 订单类
 */
@Data
@EqualsAndHashCode(callSuper = true)//equals和hashCode调研父类的方法 https://blog.csdn.net/zhanlanmg/article/details/50392266
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order")
public class Order extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 50, unique = true)
    private String orderCode;

    @Column()
    private String username;//顾客账号

    @Column()
    private String truename;//顾客真实姓名

    @Column()
    private String phone;//顾客电话

    @Column()
    private String addr;//顾客地址

    @Column()
    private Integer price;//订单总价格

    @Column(length = 255)
    private String remark;//备注

    @Transient
    private Set<OrderInfo> orderInfos;//订单详情

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
        Order rhs = (Order) obj;
        return new EqualsBuilder()
                .append(getOrderCode(), rhs.getOrderCode())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getOrderCode())
                .toHashCode();
    }
}
