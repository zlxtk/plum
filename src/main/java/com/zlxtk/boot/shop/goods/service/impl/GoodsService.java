package com.zlxtk.boot.shop.goods.service.impl;


import com.zlxtk.boot.framework.base.service.impl.BaseService;
import com.zlxtk.boot.shop.goods.model.Goods;
import com.zlxtk.boot.shop.goods.repository.GoodsRepository;
import com.zlxtk.boot.shop.goods.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService extends BaseService<Goods, Long> implements IGoodsService {

    private GoodsRepository goodsRepository;

    @Autowired
    public GoodsService(GoodsRepository goodsRepository) {
        super(goodsRepository);
        this.goodsRepository = goodsRepository;
    }

    @Override
    public Goods findByGoodsCode(String goodsCode) {
        return goodsRepository.findByGoodsCode(goodsCode);
    }


}
