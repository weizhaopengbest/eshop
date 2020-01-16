package com.roncoo.eshop.inventory.service;

import com.roncoo.eshop.inventory.request.Request;

/**
 * 执行异步请求
 * @author weizhaopeng
 * @date 2020/1/16
 */
public interface RequestAsyncProcessService {

    void process(Request request);

}
