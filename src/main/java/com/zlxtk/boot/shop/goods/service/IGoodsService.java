package com.zlxtk.boot.shop.goods.service;

import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.shop.goods.model.Goods;

public interface IGoodsService extends IBaseService<Goods, Long> {

    Goods findByGoodsCode(String goodsCode);
}
