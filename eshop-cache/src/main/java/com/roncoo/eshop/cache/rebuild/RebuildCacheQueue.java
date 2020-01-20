package com.roncoo.eshop.cache.rebuild;

import com.roncoo.eshop.cache.model.ProductInfo;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author weizhaopeng
 * @date 2020/1/20
 */
public class RebuildCacheQueue {

    private ArrayBlockingQueue<ProductInfo> queue=new ArrayBlockingQueue(1000);

    public void putProductInfo(ProductInfo productInfo){
        try {
            queue.put(productInfo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ProductInfo takeProductInfo(){
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Singleton{

        private static RebuildCacheQueue instance;

        static {
            instance = new RebuildCacheQueue();
        }

        public static RebuildCacheQueue getSingleton(){
            return instance;
        }

    }


    public static RebuildCacheQueue getInstance(){
        return Singleton.getSingleton();
    }

    public static void init(){
        getInstance();
    }



}
