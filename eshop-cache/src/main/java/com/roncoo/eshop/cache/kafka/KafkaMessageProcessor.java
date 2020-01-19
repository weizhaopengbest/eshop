package com.roncoo.eshop.cache.kafka;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.model.ShopInfo;
import com.roncoo.eshop.cache.service.CacheService;
import com.roncoo.eshop.cache.spring.SpringContext;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * @author weizhaopeng
 * @date 2020/1/19
 */
public class KafkaMessageProcessor implements Runnable {


    private KafkaStream kafkaStream;

    private CacheService cacheService;


    public KafkaMessageProcessor(KafkaStream kafkaStream) {
        this.kafkaStream = kafkaStream;
        this.cacheService = (CacheService) SpringContext.getApplicationContext().getBean("cacheService");
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            String message = new String(it.next().message());

            //首先将message 转换成json对象
            JSONObject messageJSONObject=JSONObject.parseObject(message);
            String serviceId = messageJSONObject.getString("serviceId");

            //如果是商品信息服务
            // 如果是商品信息服务
            if("productInfoService".equals(serviceId)) {
                processProductInfoChangeMessage(messageJSONObject);
            } else if("shopInfoService".equals(serviceId)) {
                processShopInfoChangeMessage(messageJSONObject);
            }


        }
    }


    /**
     * 处理商品信息变更的消息
     * @param messageJSONObject
     */
    private void processProductInfoChangeMessage(JSONObject messageJSONObject){
        Long productId = messageJSONObject.getLong("productId");
        //调用商品信息服务接口
        String productInfoJSON = "{\"id\": 1, \"name\": \"iphone7手机\", \"price\": 5599, \"pictureList\":\"a.jpg,b.jpg\", \"specification\": \"iphone7的规格\", \"service\": \"iphone7的售后服务\", \"color\": \"红色,白色,黑色\", \"size\": \"5.5\", \"shopId\": 1}";
        ProductInfo productInfo=JSONObject.parseObject(productInfoJSON, ProductInfo.class);
        cacheService.saveProductInfo2LocalCache(productInfo);

        ProductInfo productInfoFromLocalCache = cacheService.getProductInfoFromLocalCache(1L);
        System.out.println("productInfoFromLocalCache = " + productInfoFromLocalCache);

        cacheService.saveProductInfo2RedisCache(productInfo);
    }

    /**
     * 处理店铺信息变更的消息
     * @param messageJSONObject
     */
    private void processShopInfoChangeMessage(JSONObject messageJSONObject){
        Long shopId = messageJSONObject.getLong("shopId");
        //调用商品信息服务接口
        String shopInfoJSON = "{\"id\": 1, \"name\": \"小王的手机店\", \"level\": 5, \"goodCommentRate\":0.99}";
        ShopInfo shopInfo=JSONObject.parseObject(shopInfoJSON, ShopInfo.class);
        cacheService.saveShopInfo2LocalCache(shopInfo);

        ShopInfo shopInfoFromLocalCache = cacheService.getShopInfoFromLocalCache(1L);
        System.out.println("shopInfoFromLocalCache = " + shopInfoFromLocalCache);


        cacheService.saveShopInfo2RedisCache(shopInfo);
    }



}
