package com.zlxtk.boot.shop.goods.model;

import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * 商品实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)//equals和hashCode调研父类的方法 https://blog.csdn.net/zhanlanmg/article/details/50392266
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shop_goods")
public class Goods extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, length = 50, unique = true)
    private String goodsCode;

    @Column(length = 50)
    private String goodsName;

    @Column()
    private Integer count;//商品库存

    @Column()
    private Integer price;//销售价格

    @Column()
    private Integer leastPrice;//最低售价

    @Column()
    private Integer costing;//进货价格

    @Column(length = 500)
    private String imgUrl;//图片路径

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
        Goods rhs = (Goods) obj;
        return new EqualsBuilder()
                .append(getGoodsCode(), rhs.getGoodsCode())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getGoodsCode())
                .toHashCode();
    }
}
