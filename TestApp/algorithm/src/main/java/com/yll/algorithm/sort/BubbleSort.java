package com.yll.algorithm.sort;

/**
 * Created by yll on 18/3/21.
 * 冒泡排序
 * 20w个数据排序耗时->87548
 * 算法复杂度O(n²)
 */

public class BubbleSort {

    public void doBubbleSort(int[] a){

        if (a == null || a.length == 0){
            return;
        }
        int temp;
        for (int i = 0; i < a.length; i++){
            for (int j = i; j < a.length; j++){
                if (a[j] < a[i]){
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

}
