package com.zlxtk.boot.shop.order.model;

import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;

import javax.persistence.*;

/**
 * 订单详情类
 */
@Data
@EqualsAndHashCode(callSuper = true)//equals和hashCode调研父类的方法 https://blog.csdn.net/zhanlanmg/article/details/50392266
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shop_order_info")
public class OrderInfo extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String orderCode;//订单号

    @Column(length = 50)
    private String goodsCode;//货号

    @Column(length = 50)
    private String goodsName;//商品名

    @Column()
    private Integer count;//售出数量

    @Column()
    private Integer price;//销售价格

    @Column(length = 255)
    private String remark;//备注

}
