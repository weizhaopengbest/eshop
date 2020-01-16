package com.roncoo.eshop.inventory.controller;

import com.roncoo.eshop.inventory.model.ProductInventory;
import com.roncoo.eshop.inventory.request.ProductInventoryCacheReloadRequest;
import com.roncoo.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.service.ProductInventoryService;
import com.roncoo.eshop.inventory.service.RequestAsyncProcessService;
import com.roncoo.eshop.inventory.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author weizhaopeng
 * @date 2020/1/16
 */
@Controller
public class ProductInventoryController {


    @Autowired
    ProductInventoryService productInventoryService;

    @Autowired
    RequestAsyncProcessService requestAsyncProcessService;


    /**
     * 更新商品库存
     *
     * @param productInventory
     * @return
     */
    @RequestMapping("/updateProductInventory")
    @ResponseBody
    public Response updateProductInventory(ProductInventory productInventory) {
        Response response = null;
        try {
            Request request = new ProductInventoryDBUpdateRequest(
                    productInventory, productInventoryService);
            requestAsyncProcessService.process(request);
            response = new Response(Response.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(Response.FAILURE);
        }
        return response;
    }


    /**
     * 获取商品库存
     *
     * @param
     * @return
     */
    @RequestMapping("/getProductInventory")
    @ResponseBody
    public ProductInventory getProductInventory(Integer productId) {
        ProductInventory productInventory = null;
        try {
            Request request = new ProductInventoryCacheReloadRequest(
                    productId, productInventoryService);
            requestAsyncProcessService.process(request);

            //将请求扔给service异步处理之后，就需要while true一会，在这里hang住
            //去尝试等待前面有商品库存更新的操作，同时缓存刷新的操作，将最新的数据刷到缓存
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            long waitTime = 0L;

            //等待超过200ms 没有从缓冲中获取数据
            while (true) {
                if (waitTime > 200) {
                    break;
                }

                //尝试去redis读取一次商品库存的缓存数据
                productInventory = productInventoryService.getProductInventoryCache(productId);

                //如果读取到了结果，那么久返回
                if (productInventory != null) {
                    return productInventory;
                } else {
                    Thread.sleep(20);
                    endTime = System.currentTimeMillis();
                    waitTime = endTime - startTime;
                }
            }
            //直接尝试从数据库中读取数据
            productInventory = productInventoryService.findProductInventory(productId);
            if (productInventory != null) {
                //将缓存刷新一下
                requestAsyncProcessService.process(request);
                return productInventory;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProductInventory(productId, -1L);
    }


}
