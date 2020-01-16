package com.roncoo.eshop.inventory.request;

import com.roncoo.eshop.inventory.model.ProductInventory;
import com.roncoo.eshop.inventory.service.ProductInventoryService;

/**
 *
 * @author weizhaopeng
 * @date 2020/1/16
 */
public class ProductInventoryDBUpdateRequest implements Request {



    private ProductInventory productInventory;

    private ProductInventoryService productInventoryService;


    @Override
    public void process() {
        //删除redis中的缓存
        productInventoryService.removeProductInventory(productInventory);
        //修改数据库中的库存
        productInventoryService.updateProductInventory(productInventory);
    }

    @Override
    public Integer getProductId() {
        return productInventory.getProductId();
    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }


    public ProductInventoryDBUpdateRequest(ProductInventory productInventory, ProductInventoryService productInventoryService) {
        this.productInventory = productInventory;
        this.productInventoryService = productInventoryService;
    }
}
