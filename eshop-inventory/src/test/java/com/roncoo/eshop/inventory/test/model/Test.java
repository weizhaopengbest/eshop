package com.roncoo.eshop.inventory.test.model;

import java.util.HashMap;
import java.util.Map;

public class Test {


    @org.junit.Test
    public void test1(){
        Map<Integer,Boolean> flagMap =new HashMap<>();
        flagMap.put(1,true);

        Boolean flag = flagMap.get(1);

        if(flag){
            flagMap.put(1,false);
            System.out.println(flag +" flag1");
        }

        System.out.println(flag +" flag2");

        System.out.println(flagMap.get(1) +" flag3");
        System.out.println();
    }

    @org.junit.Test
    public void test2(){
        System.out.println(3 & 5);
        System.out.println(5%4);

    }

}
