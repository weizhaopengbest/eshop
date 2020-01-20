package com.roncoo.eshop.cache.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.model.ShopInfo;
import com.roncoo.eshop.cache.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @author weizhaopeng
 * @date 2020/1/17
 */
@Service("cacheService")
public class CacheServiceImpl implements CacheService {

    public static final String CACHE_NAME = "local";

    @Resource
    private JedisCluster jedisCluster;



    @Cacheable(value = CACHE_NAME, key = "'product_info_'+#id")
    @Override
    public ProductInfo getProductInfoFromLocalCache(Long id) {
        return null;
    }

    @Cacheable(value = CACHE_NAME, key = "'shop_info_'+#id")
    @Override
    public ShopInfo getShopInfoFromLocalCache(Long id) {
        return null;
    }

    @Override
    public ProductInfo getProductInfoFromRedisCache(Long id) {
        String key = "product_info_" + id;
        String json = jedisCluster.get(key);
        ProductInfo productInfo = JSONObject.parseObject(json, ProductInfo.class);
        return productInfo;
    }

    @Override
    public ShopInfo getShopInfoFromRedisCache(Long id) {
        String key = "shop_info_" + id;
        String json = jedisCluster.get(key);
        ShopInfo shopInfo = JSONObject.parseObject(json, ShopInfo.class);
        return shopInfo;
    }

    @CachePut(value = CACHE_NAME, key = "'product_info_'+#productInfo.getId()")
    @Override
    public ProductInfo saveProductInfo2LocalCache(ProductInfo productInfo) {
        return productInfo;
    }

    @Override
    public void saveProductInfo2RedisCache(ProductInfo productInfo) {
        String key = "product_info_" + productInfo.getId();
        jedisCluster.set(key, JSONObject.toJSONString(productInfo));
    }

    @CachePut(value = CACHE_NAME, key = "'shop_info_'+#shopInfo.getId()")
    @Override
    public ShopInfo saveShopInfo2LocalCache(ShopInfo shopInfo) {
        return shopInfo;
    }

    @Override
    public void saveShopInfo2RedisCache(ShopInfo shopInfo) {
        String key = "shop_info_" + shopInfo.getId();
        jedisCluster.set(key, JSONObject.toJSONString(shopInfo));
    }


}
