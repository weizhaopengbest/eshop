package com.roncoo.eshop.cache.service;


import com.roncoo.eshop.cache.model.ProductInfo;

/**
 * @author weizhaopeng
 * @date 2020/1/17
 */
public interface CacheService {

    public ProductInfo findById(Long id);

    public ProductInfo saveProductInfo(ProductInfo productInfo);

}
