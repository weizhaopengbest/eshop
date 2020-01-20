package com.roncoo.eshop.cache.service;


import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.model.ShopInfo;

/**
 * @author weizhaopeng
 * @date 2020/1/17
 */
public interface CacheService {


    ProductInfo getProductInfoFromLocalCache(Long id);

    ShopInfo getShopInfoFromLocalCache(Long id);

    ProductInfo getProductInfoFromRedisCache(Long id);

    ShopInfo getShopInfoFromRedisCache(Long id);


    ProductInfo saveProductInfo2LocalCache(ProductInfo productInfo);

    void saveProductInfo2RedisCache(ProductInfo productInfo);

    ShopInfo saveShopInfo2LocalCache(ShopInfo shopInfo);

    void saveShopInfo2RedisCache(ShopInfo shopInfo);




}
