package com.yll.algorithm;


import com.yll.algorithm.sort.BubbleSort;
import com.yll.algorithm.sort.MergeSortTest;
import com.yll.algorithm.sort.QuickSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * Created by yll on 17/7/19.
 */

public class MainClass {

    public static void main(String args[]) {

        /**随机200000个数据进行归并排序 */
//        int length = 200000;
//        int[] a = new int[length];
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            a[i] = random.nextInt(length * 2);
//        }
//        printArray(a);
//        long time = System.currentTimeMillis();
//        new QuickSort().doQuickSort(a);
//        new MergeSortTest().doMergeSort(a);
//        new BubbleSort().doBubbleSort(a);
//        System.out.println("排序耗时->" + (System.currentTimeMillis() - time));
//        printArray(a);


    }

    private static void printArray(int[] a){
        for (int i : a){
            System.out.println(i);
        }
    }

    private static void printList(ArrayList<ArrayList<Integer>> list){
        for (ArrayList<Integer> l : list){
            for (int i = 0; i < l.size(); i++){
                System.out.print(String.valueOf(i));
            }
            System.out.println("");
        }
    }

    /***请在当天内完成以下题目（以纸笔或者文本形式完成，请勿使用IDE等开发工具）
     1、请写出实际代码（可以使用任意熟悉的编码语言）
     2、需要考虑时间、空间复杂度

     百词斩的Lily非常喜欢旅游，经常和他老婆自驾出游。但是Lily是一个非常讲求性价比的程序员，所以每次在外面吃饭的时候都要控制价格。
     今年国庆Lily和他老婆出游，Lily规定每顿饭的价格上限为n，他老婆想点k个菜，餐厅提供m个菜。
     请问，在不超过Lily价格上限的情况下，点k个菜有多少种点法（同一种菜只能点一份）。

     输入：
     1、第一行为价格上限n，想点菜的个数k，以及菜品个数m
     2、接下来m行，每行一个菜品价格
     3、以上输入均为正整数
     输出：
     1、输出为一行，代表点菜的方案数
     Sample Input:
     10 2 4
     10
     3
     5
     12
     Sample Output:
     1

     （Sample说明：因为需要点两个菜，且总价不超过10，所以只能有第二和第三个菜的组合方案。其他方案加起来都会超过10）
     */





}
