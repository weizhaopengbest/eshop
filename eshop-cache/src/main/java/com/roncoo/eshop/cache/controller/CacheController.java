package com.roncoo.eshop.cache.controller;

import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.service.CacheService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author weizhaopeng
 * @date 2020/1/17
 */
@Controller
public class CacheController {

    @Resource
    private CacheService cacheService;

    @RequestMapping("/testPutCache")
    @ResponseBody
    public void testPutCache(ProductInfo productInfo) {
        System.out.println(productInfo.getId() + ":" + productInfo.getName());
        cacheService.saveProductInfo(productInfo);
    }

    @RequestMapping("/testGetCache")
    @ResponseBody
    public ProductInfo testGetCache(Long id) {
        ProductInfo productInfo = cacheService.findById(id);
        System.out.println(productInfo.getId() + ":" + productInfo.getName());
        return productInfo;
    }


}
