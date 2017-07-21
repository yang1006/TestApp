package com.yll.algorithm;

import java.util.ArrayList;

/**
 * Created by yll on 17/7/20.
 * 一些测试代码
 */

public class ExceptionTest {

    private ArrayList<Bean> beanList = new ArrayList<>();
    /**
     * 测试 解决ConcurrentModificationException
     * 新建一个temp addAll(), 当beanList数据增删、clear掉时,不影响temp的数据
     * */
    public void test1(){

        for (int i = 0; i < 5; i++){
            Bean bean = new Bean(i, i + " bean");
            beanList.add(bean);
        }

        ArrayList<Bean> temp = new ArrayList<>();
        temp.addAll(beanList);

        System.out.println("操作前打印下 数据的内存地址: beanList ");
        for (Bean b : beanList){
            System.out.print(b);
            System.out.print("  ");
        }

        System.out.println("\ntemp ");
        for (Bean b : temp){
            System.out.print(b);
            System.out.print("  ");
        }

        beanList.clear();
        //beanList clear之后,temp数据内存地址不变
        System.out.println("\n操作后temp ");
        for (Bean b : temp){
            System.out.print(b);
            System.out.print("  ");
        }

    }

    private class Bean{
        int id;
        String name;
        Bean(int id, String name){
            this.id = id;
            this.name = name;
        }
    }
}
