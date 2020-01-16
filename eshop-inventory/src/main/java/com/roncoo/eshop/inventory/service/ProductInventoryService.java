package com.roncoo.eshop.inventory.service;

import com.roncoo.eshop.inventory.model.ProductInventory;

/**
 * @author weizhaopeng
 * @date 2020/1/16
 */
public interface ProductInventoryService {

    /**
     * 更新数据库中的商品库存
     */
    void updateProductInventory(ProductInventory productInventory);

    /**
     * 删除缓存中的商品库存
     */
    void removeProductInventory(ProductInventory productInventory);


    /**
     * 根据商品id查询商品库存信息
     * @param productId
     * @return
     */
    ProductInventory findProductInventory(Integer productId);

    /**
     * 设置商品库存的缓存
     * @param productInventory
     */
    void setProductInventoryCache(ProductInventory productInventory);


    ProductInventory getProductInventoryCache(Integer productId);




}
