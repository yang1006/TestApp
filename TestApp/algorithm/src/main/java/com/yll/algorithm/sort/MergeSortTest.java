package com.yll.algorithm.sort;

/**
 * Created by yll on 17/7/20.
 * 归并排序
 * 归并排序，其的基本思路就是将数组分成二组A，B，如果这二组组内的数据都是有序的，那么就可以很方便的将这二组数据进行排序
 * 。如何让这二组组内数据有序了？
 * 可以将A，B组各自再分成二组。依次类推，当分出来的小组只有一个数据时，可以认为这个小组组内已经达到了有序，
 * 然后再合并相邻的二个小组就可以了。这样通过先递归的分解数列，再合并数列就完成了归并排序。
 */

public class MergeSortTest {

    /**
     * 合并操作
     * 将有序数组a[] 和b[] 合并到c[]
     * */
    private void mergeArray(int a[], int b[]){

        int aLength = a.length;
        int bLength = b.length;
        int[] c = new int[aLength + bLength];

        int i, j, k;
        i = j = k = 0;

        while (i < aLength && j < bLength){
            if (a[i] < b[j]){
                c[k++] = a[i++];
            }else {
                c[k++] = b[j++];
            }
        }

        while (i < aLength){
            c[k++] = a[i++];
        }

        while (j < bLength){
            c[k++] = b[j++];
        }

        for (int aC : c) {
            System.out.print(aC + " ");
        }
    }

    /** 将有二个有序数列a[first, mid]和a[mid + 1, last]合并。
     *  first 不一定是0, last也不一定是数组 length - 1*/
    private void mergeArray(int a[], int first, int mid, int last, int temp[]){

        int i = first, j = mid + 1;
        int k = 0;

        while (i <= mid && j <= last){
            if (a[i] < a[j]){
                temp[k++] = a[i++];
            }else {
                temp[k++] = a[j++];
            }
        }
        while (i <= mid){
            temp[k++] = a[i++];
        }
        while (j <= last){
            temp[k++] = a[j++];
        }

        for (i = 0; i < k; i++){
            a[first + i] = temp[i];
        }
    }

    /**递归分解数组 然后合并*/
    private void mergeSort(int a[], int first, int last, int temp[]){

        if (first < last){
            int mid = (first + last) / 2;
            mergeSort(a, first, mid, temp);         //左边有序
            mergeSort(a, mid + 1, last, temp);      //右边有序
            mergeArray(a, first, mid, last, temp);  //再将2个有序数组合并
        }
    }

    /**执行归并排序
     * @return 排序成功与否*/
    public boolean doMergeSort(int a[]){
        if (a == null || a.length == 0){
            return false;
        }
        int[] b = new int[a.length];
        mergeSort(a, 0, a.length - 1, b);
        return true;
    }
}
