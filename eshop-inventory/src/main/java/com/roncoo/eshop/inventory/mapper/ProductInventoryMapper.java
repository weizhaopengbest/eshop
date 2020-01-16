package com.roncoo.eshop.inventory.mapper;

import com.roncoo.eshop.inventory.model.ProductInventory;
import org.apache.ibatis.annotations.Param;

/**
 * @author weizhaopeng
 * @date 2020/1/16
 */
public interface ProductInventoryMapper {


    void updateProductInventory(ProductInventory productInventory);

    /**
     * 根据商品id查询商品库存信息
     * @param productId
     * @return
     */
    ProductInventory findProductInventory(@Param("productId") Integer productId);


}
