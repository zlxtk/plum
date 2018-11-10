package com.zlxtk.boot.shop.goods.repository;

import com.zlxtk.boot.framework.base.repository.BaseRepository;
import com.zlxtk.boot.shop.goods.model.Goods;

public interface GoodsRepository extends BaseRepository<Goods, Long> {

    Goods findByGoodsCode(String goodsCode);
}
