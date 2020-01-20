package com.roncoo.eshop.cache.rebuild;

import com.roncoo.eshop.cache.model.ProductInfo;
import com.roncoo.eshop.cache.service.CacheService;
import com.roncoo.eshop.cache.spring.SpringContext;
import com.roncoo.eshop.cache.zk.ZookeeperSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author weizhaopeng
 * @date 2020/1/20
 */
public class RebuildCacheThread implements Runnable {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void run() {
        RebuildCacheQueue rebuildCacheQueue = RebuildCacheQueue.getInstance();
        ZookeeperSession zookeeperSession=ZookeeperSession.getInstance();
        CacheService cacheService = (CacheService) SpringContext.getApplicationContext().getBean("cacheService");

        while (true){
            ProductInfo productInfo = rebuildCacheQueue.takeProductInfo();
            zookeeperSession.acquireDistributedLock(productInfo.getId());

            ProductInfo productInfoFromRedisCache = cacheService.getProductInfoFromRedisCache(productInfo.getId());
            if(productInfoFromRedisCache!=null){
                try {
                    Date date= sdf.parse(productInfoFromRedisCache.getModifiedTime());
                    Date existedDate= sdf.parse(productInfo.getModifiedTime());
                    if(date.before(existedDate)){
                        System.out.println(" 修改 时间在当前数据之前， 修改失败" );
                        continue;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            cacheService.saveProductInfo2RedisCache(productInfo);
            ZookeeperSession.getInstance().releaseDistributedLock(productInfo.getId());
        }
    }




}
