package com.yll.algorithm;

import com.yll.algorithm.sort.MergeSortTest;

import java.util.Random;

/**
 * Created by yll on 17/7/19.
 */

public class MainClass {

    public static void main(String args[]){

        /**随机200000个数据进行归并排序 */
        int length = 200000;
        int[] a = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++){
            a[i] = random.nextInt(length * 2);
        }
        long time = System.currentTimeMillis();
        new MergeSortTest().doMergeSort(a);

        System.out.println("time->" + (System.currentTimeMillis() - time));

    }




}
