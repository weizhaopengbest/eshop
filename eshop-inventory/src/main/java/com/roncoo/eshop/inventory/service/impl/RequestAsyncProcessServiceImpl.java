package com.roncoo.eshop.inventory.service.impl;

import com.roncoo.eshop.inventory.request.Request;
import com.roncoo.eshop.inventory.request.RequestQueue;
import com.roncoo.eshop.inventory.service.RequestAsyncProcessService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求异步处理的service实现类
 *
 * @author weizhaopeng
 * @date 2020/1/16
 */
@Service
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {


    @Override
    public void process(Request request) {

        try {
            //做请求路由，根据每个请求的商品id，路由到对应的内存队列中去
            ArrayBlockingQueue<Request> queue = getRouteQueue(request.getProductId());
            queue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private ArrayBlockingQueue<Request> getRouteQueue(Integer productId) {
        RequestQueue requestQueue = RequestQueue.getInstance();

        //先取productId hash值
        String key = String.valueOf(productId);
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        //对hash值取模，将hash值路由到指定的内存队列中
        int index = (requestQueue.queueSize() - 1) & hash;
        return requestQueue.getQueue(index);
    }


}
