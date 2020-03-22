package com.example.utils.demo.util;

public class AlgorithmUtils {
    /**
     * 冒泡排序
     * 原理：
     * 1.比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     * 2.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     * 3.针对所有的元素重复以上的步骤，除了最后一个。
     * 4.持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        for (int i = 0; i < array.length-1; i++) {
            for (int j = 0; j< array.length-1-i; j++) {
                if (array[j+1] < array[j]) {
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 快速排序
     * 原理：
     * 1.首先取一个基准值，一般都是数组第一个数
     * 2.假设有个哨兵从左往右找比基准值小的值 a
     * 3.假设有个哨兵从右往左找比基准值大的值 b
     * 4.一定先从左边开始找，然后交换a,b的值
     * 5.继续上面操作，直到左右哨兵相遇为止用相遇点的值交换基准值
     * 6.交换基准值后分出来左右两边，然后采用递归继续区分
     * @param array
     * @param low
     * @param high
     */
    public static void quickSort(int[] array, int low, int high) {
        int i; //用于记录左边哨兵
        int j; //用于记录右边哨兵
        int pivot; //用于记录基准值
        int temp; //用于交换临时变量
        //low不能大于high
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        pivot = array[low]; //一般都是将最左边的第一个数值当作基准值
        while (i < j) {
            while (array[j] >= pivot && i < j) {
                j --;
            }
            while (array[i] <= pivot && i < j) {
                i ++;
            }
            if (i < j) {
                temp = array[j];
                array[j] = array[i];
                array[i] = temp;
            }
        }
        //交换基准值
        array[low] = array[i];
        array[i] = pivot;
        //递归调左边
        quickSort(array, low, j-1);
        //递归调右边
        quickSort(array, j+1, high);
    }

    /**
     * 二分查找
     * 数组必须有序
     * @param array
     * @param key
     * @return
     */
    public static void binarySearch(int[] array, Integer key) {
        int low = 0; //低位
        int high = array.length; //高位
        int mid = 0; //可以随便给中位赋值下标
        boolean flag = false;
        while (low <= high) {
            mid = (low + high)/2;
            if (key > array[mid]) {
                low = mid + 1;
            } else if (key < array[mid]) {
                high = mid - 1;
            } else {
                flag = true;
                System.out.println(mid);
                break;
            }
        }
        if (flag) {
            System.out.println("mid=" + mid +"========"+ "find="+ array[mid]);
        }else{
            System.out.println(-low - 1);    //如果没找到，那肯定是低位大于了高位，且低位和高位相挨着，返回没有找到的find值应该所在的下标位置（即-low - 1）
        }
    }

    /**
     * 选择排序
     * 原理：
     * 1.对于给定的一组记录，经过第一轮比较后得到最小的记录，
     * 2.然后将该记录与第一个记录的位置进行交换；
     * 3.接着对不包括第一个记录以外的其他记录进行第二轮比较，得到最小的记录并与第二个记录进行位置交换；
     * 4.重复该过程，直到进行比较的记录只有一个时为止。
     * @param array
     */
    public static void selectSort(int[] array) {
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                int minIndex = i;
                for (int j = i + 1; j < array.length; j++) {
                    if (array[j] < array[minIndex]) {
                        // 保持最小值索引
                        minIndex = j;
                    }
                }
                // 交换当前索引 i 和最小值索引 minIndex 两处的值
                if (i != minIndex) {
                    int temp = array[i];
                    array[i] = array[minIndex];
                    array[minIndex] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        // 冒泡排序
        int[] array = {2,6,1,3,9,34,27,18,28,87,73,90};
        System.out.print("冒泡排序前： ");
        for (int i=0; i < array.length; i++) {
            System.out.print(array[i]+" ");
        }
        System.out.println();
        array = bubbleSort(array);
        System.out.print("冒泡排序后： ");
        for (int i=0; i<array.length; i++) {
            System.out.print(array[i]+" ");
        }
        System.out.println();
        System.out.println();

        // 快速排序
        int[] array1 = {2,6,1,3,9,34,27,18,28,87,73,90};
        System.out.print("快速排序前： ");
        for (int i=0; i < array1.length; i++) {
            System.out.print(array1[i]+" ");
        }
        System.out.println();
        quickSort(array1, 0, array1.length-1);
        System.out.print("快速排序后： ");
        for (int i=0; i < array1.length; i++) {
            System.out.print(array1[i]+" ");
        }
        System.out.println();
        System.out.println();

        // 二分查找
        int[] arrayInt = new int[1000];
        for (int i=0; i < 1000; i++) {
            arrayInt[i] = i;
        }
        binarySearch(arrayInt, 200);
        System.out.println();

        // 选择排序
        int[] array2 = {2,6,1,3,9,34,27,18,28,87,73,90};
        System.out.print("选择排序前： ");
        for (int i=0; i < array2.length; i++) {
            System.out.print(array2[i]+" ");
        }
        System.out.println();
        selectSort(array2);
        System.out.print("选择排序后： ");
        for (int i=0; i < array2.length; i++) {
            System.out.print(array2[i]+" ");
        }
        System.out.println();
        System.out.println();
    }
}
