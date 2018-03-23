package com.yll.algorithm.sort;

/**
 * Created by yll on 18/3/21.
 * 快速排序
 * 通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，
 * 然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
 * O(nlogn)
 */

public class QuickSort {

    private void doQuickSort(int[] a, int first, int last){
        int i = first, j = last;
        if (i >= j || i < 0 || j >= a.length){
            return;
        }
        int k = a[i];
        while (i < j){
            while (i < j && a[j] >= k){
                j--;
            }
            if (i < j){
                a[i] = a[j];
                i++;
            }
            while (i < j && a[i] <= k){
                i++;
            }
            if (i < j){
                a[j] = a[i];
                j--;
            }
            a[i] = k;
        }
        doQuickSort(a, first, i - 1);
        doQuickSort(a, j + 1, last);
    }

    public void doQuickSort(int[] a){
        if (a == null || a.length == 0){
            return;
        }
        doQuickSort(a, 0, a.length - 1);
    }
}
