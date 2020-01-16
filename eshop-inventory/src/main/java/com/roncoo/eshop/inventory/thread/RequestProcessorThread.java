package com.roncoo.eshop.inventory.thread;

import com.roncoo.eshop.inventory.request.ProductInventoryCacheReloadRequest;
import com.roncoo.eshop.inventory.request.ProductInventoryDBUpdateRequest;
import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.request.RequestQueue;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author weizhaopeng
 * @date 2020/1/16
 */
public class RequestProcessorThread implements Callable<Boolean> {

    private ArrayBlockingQueue<Request> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            while (true){
                Request request = queue.take();

                RequestQueue requestQueue = RequestQueue.getInstance();
                Map<Integer, Boolean> flagMap = requestQueue.getFlagMap();

                if (request instanceof ProductInventoryDBUpdateRequest) {
                    //如果是一个更新数据库的请求，那么就将那个productId 对应的标识位置为true
                    flagMap.put(request.getProductId(), true);

                } else if (request instanceof ProductInventoryCacheReloadRequest) {
                    Boolean flag = flagMap.get(request.getProductId());

                    //如果flag 是空
                    if(flag == null){
                        flagMap.put(request.getProductId(), false);
                    }

                    //如果是一个缓存刷新的请求，如果标识不为空，而且是true，就说明之前有一个这个商品的更新请求
                    if (flag != null && flag) {
                        flagMap.put(request.getProductId(), false);
                    }

                    //如果是缓存刷新请求，而且发现标识不为空，但是标识是false，说明前面已经有一个数据更新请求+一个缓存刷新请求了
                    if (flag != null && !flag) {
                        //这种请求，直接过滤，不需要放入队列中。
                        return true;
                    }
                }

                request.process();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
