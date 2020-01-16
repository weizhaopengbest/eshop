package com.roncoo.eshop.inventory.request;

import com.roncoo.eshop.inventory.model.ProductInventory;
import com.roncoo.eshop.inventory.service.ProductInventoryService;

/**
 * @author weizhaopeng
 * @date 2020/1/16
 */
public class ProductInventoryCacheReloadRequest implements Request {


    private Integer productId;

    /**
     * 是否强制刷新标识
     */
    private boolean forceRefresh;

    private ProductInventoryService productInventoryService;

    public ProductInventoryCacheReloadRequest(Integer productId, boolean forceRefresh,
                                              ProductInventoryService productInventoryService) {
        this.productId = productId;
        this.forceRefresh = forceRefresh;
        this.productInventoryService = productInventoryService;
    }

    @Override
    public void process() {
        // 从数据库中查询最新的商品库存数量
        ProductInventory productInventory = productInventoryService.findProductInventory(productId);
        // 将最新的商品库存数量，刷新到redis缓存中
        productInventoryService.setProductInventoryCache(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productId;
    }

    @Override
    public boolean isForceRefresh() {
        return forceRefresh;
    }



}
