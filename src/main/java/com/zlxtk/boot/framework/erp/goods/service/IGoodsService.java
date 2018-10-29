package com.zlxtk.boot.framework.erp.goods.service;

import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.erp.goods.model.Goods;

public interface IGoodsService extends IBaseService<Goods, Long> {

    Goods findByGoodsCode(String goodsCode);
}
