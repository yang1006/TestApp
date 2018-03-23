package com.yll.algorithm;


import com.yll.algorithm.sort.BubbleSort;
import com.yll.algorithm.sort.MergeSortTest;
import com.yll.algorithm.sort.QuickSort;

import java.util.Random;


/**
 * Created by yll on 17/7/19.
 */

public class MainClass {

    public static void main(String args[]) {

        /**随机200000个数据进行归并排序 */
        int length = 200000;
        int[] a = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            a[i] = random.nextInt(length * 2);
        }
//        printArray(a);
        long time = System.currentTimeMillis();
//        new QuickSort().doQuickSort(a);
//        new MergeSortTest().doMergeSort(a);
//        new BubbleSort().doBubbleSort(a);
        System.out.println("排序耗时->" + (System.currentTimeMillis() - time));
//        printArray(a);

    }

    private static void printArray(int[] a){
        for (int i : a){
            System.out.println(i);
        }
    }




}
