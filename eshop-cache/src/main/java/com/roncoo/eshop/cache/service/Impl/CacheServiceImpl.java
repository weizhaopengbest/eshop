package com.roncoo.eshop.cache.service.Impl;

import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.service.CacheService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author weizhaopeng
 * @date 2020/1/17
 */
@Service
public class CacheServiceImpl implements CacheService {

    public static final String CACHE_NAME="local";


    @Cacheable(value = CACHE_NAME, key = "'key_'+#id" )
    @Override
    public ProductInfo findById(Long id) {
        return null;
    }

    @CachePut(value = CACHE_NAME, key = "'key_'+#productInfo.getId()" )
    @Override
    public ProductInfo saveProductInfo(ProductInfo productInfo) {
        return productInfo;
    }


}
