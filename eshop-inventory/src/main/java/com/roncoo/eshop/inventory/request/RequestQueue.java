package com.roncoo.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author weizhaopeng
 * @date 2020/1/16
 */
public class RequestQueue {

    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues= new ArrayList<>();

    /**
     * 标识位map
     */
    private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();


    /**
     * 添加内存队列
     * @param queue
     */
    public void addQueue(ArrayBlockingQueue<Request> queue){
        queues.add(queue);
    }

    /**
     * 获取内存队列大小
     * @return
     */
    public Integer queueSize(){
        return queues.size();
    }

    /**
     * 获取内存队列
     * @param index
     * @return
     */
    public ArrayBlockingQueue<Request> getQueue(int index){
        ArrayBlockingQueue<Request> requests = queues.get(index);
        return requests;
    }


    public Map<Integer, Boolean> getFlagMap(){
        return flagMap;
    }





    private static class Singleton{
        private static RequestQueue instance;

        static {
            instance=new RequestQueue();
        }

        public static RequestQueue getInstance(){
            return instance;
        }
    }


    public static RequestQueue getInstance(){
        return RequestQueue.Singleton.getInstance();
    }



}
