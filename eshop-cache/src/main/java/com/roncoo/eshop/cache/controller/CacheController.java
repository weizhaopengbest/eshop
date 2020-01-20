package com.roncoo.eshop.cache.controller;

import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.model.ShopInfo;
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
        cacheService.saveProductInfo2LocalCache(productInfo);
    }

    @RequestMapping("/testGetCache")
    @ResponseBody
    public ProductInfo testGetCache(Long id) {
        ProductInfo productInfo = cacheService.getProductInfoFromLocalCache(id);
        System.out.println(productInfo.getId() + ":" + productInfo.getName());
        return productInfo;
    }

    @RequestMapping("/getProductInfo")
    @ResponseBody
    public ProductInfo getProductInfo(Long productId) {
        ProductInfo productInfo = null;
        productInfo = cacheService.getProductInfoFromRedisCache(productId);
        if(productInfo == null){
            productInfo = cacheService.getProductInfoFromLocalCache(productId);
        }
        if(productInfo == null){
            //从数据源重新拉取缓存，重建缓存

        }

        System.out.println(productInfo.getId() + ":" + productInfo.getName());
        return productInfo;
    }


    @RequestMapping("/getShopInfo")
    @ResponseBody
    public ShopInfo getShopInfo(Long shopId) {
        ShopInfo shopInfo = null;
        shopInfo = cacheService.getShopInfoFromRedisCache(shopId);
        if(shopInfo == null){
            shopInfo = cacheService.getShopInfoFromLocalCache(shopId);
        }
        if(shopInfo == null){
            //从数据源重新拉取缓存，重建缓存
        }
        return shopInfo;
    }





}
