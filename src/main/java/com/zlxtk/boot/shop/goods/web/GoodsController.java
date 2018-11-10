/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.shop.goods.web;

import com.github.wenhao.jpa.Specifications;
import com.zlxtk.boot.framework.base.web.controller.BaseController;
import com.zlxtk.boot.shop.goods.model.Goods;
import com.zlxtk.boot.shop.goods.service.IGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用途：商品控制器
 */

@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController<Goods, Long> {

    private IGoodsService goodsService;

    @Autowired
    public GoodsController(IGoodsService goodsService) {
        super(goodsService);
        this.goodsService=goodsService;
    }

    @RequestMapping(value ={"/","/index"})
    public String index(Model model,
                        @RequestParam(required = false, defaultValue = "1") int page, //
                        @RequestParam(required = false, defaultValue = "10") int size, //
                        @RequestParam(required = false) String direction, //
                        @RequestParam(required = false) String properties,
                        @RequestParam(required = false) String goodsCode) {

        Pageable pageable = goodsService.getPageable(page, size, direction, properties);

        Specification<Goods> specification = Specifications.<Goods>and()
                .eq(StringUtils.isNotBlank(goodsCode),"goodsCode",goodsCode)
                .build();
        // 获取查询结果
        Page<Goods> pages = goodsService.findAll(specification, pageable);

        model.addAttribute("pages", pages);
        model.addAttribute("goodsCode",goodsCode);
        return "goods/index";
    }

    @RequestMapping(value ={"/goodsInfo"})
    public String goodsInfo(Model model) {
        return "goods/goodsInfo";
    }

    @PostMapping(value = "/createGoods")
    public String create(Model model,Goods goods) {
        try {
            goods = goodsService.insert(goods);
        } catch (Exception e) {
            model.addAttribute("errorMessage","创建失败。"+e.getMessage());
        }
        return "goods/goodsInfo";
    }


}
