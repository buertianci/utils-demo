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

    /**
     * 插入排序
     * 原理：
     * 1.通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入,
     * 2.插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序），
     * 3.因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。
     * @param array
     */
    public static void insertionSort(int[] array) {
        //对空一些特殊情况做判断
        if (array == null || array.length <= 0 || array.length == 1) {
            return;
        }
        for (int i = 0; i < array.length - 1; i++) {
            //第一个值默认排序，从第二个开始
            int current = array[i+1];
            //将当前i记录一下，目的为移位做中间变量
            int preIndex = i;
            //如果当前值比它左边的值要小，那就先将左边的值逐一右移
            while(preIndex >= 0 && current < array[preIndex]) {
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            //上面右移完事后，再将当前值赋值到当前位置上
            array[preIndex + 1] = current;
        }

    }

    /**
     * 归并排序
     * 原理：
     * 1.归并排序是一种概念上最简单的排序算法，与快速排序一样，
     * 2.归并排序也是基于分治法的,归并排序将待排序的元素序列分成两个长度相等的子序列，
     * 3.为每一个子序列排序，然后再将他们合并成一个子序列。合并两个子序列的过程也就是两路归并。
     * @param array
     */
    public static void mergeSort(int[] array) {
        if (array == null || array.length <= 0) {
            return;
        }
        int start = 0;
        int end = array.length-1;
        mergeSort(array, start, end);
    }
    private static void mergeSort(int[] array, int start, int end) {
        if (start < end) {
            int mid = (start + end)/2; //划分子序列
            mergeSort(array, start, mid);
            mergeSort(array, mid+1, end);
            mergeSort(array, start, mid, end);
        }
    }
    private static void mergeSort(int[] array, int left, int mid, int right) {
        int temp[] = new int[array.length];
        //leftT,midT是检测指针，k是存放指针
        int leftT = left, midT = mid + 1, k = left;
        while (leftT <= mid && midT <= right) {
            if (array[leftT] <= array[midT]) {
                temp[k++] = array[leftT++];
            } else {
                temp[k++] = array[midT++];
            }
        }

        while (leftT <= mid) temp[k++] = array[leftT++];
        while (midT <= right) temp[k++] = array[midT++];

        for (int i = left; i <= right; i++) {
            array[i] = temp[i];
        }
    }

    /**
     * 堆排序
     * 原理：
     * 1.构建初始堆，将待排序列构成一个大顶堆(或者小顶堆)，升序大顶堆，降序小顶堆；
     * 2.将堆顶元素与堆尾元素交换，并断开(从待排序列中移除)堆尾元素。
     * 3.重新构建堆。
     * 4.重复2~3，直到待排序列中只剩下一个元素(堆顶元素)。
     * @param array
     */
    public static void heapSort(int[] array) {
        //创建堆
        for (int i = (array.length - 1) / 2; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(array, i, array.length);
        }

        //调整堆结构+交换堆顶元素与末尾元素
        for (int i = array.length - 1; i > 0; i--) {
            //将堆顶元素与末尾元素进行交换
            int temp = array[i];
            array[i] = array[0];
            array[0] = temp;

            //重新对堆进行调整
            adjustHeap(array, 0, i);
        }
    }
    /**
     * 调整堆
     * @param arr 待排序列
     * @param parent 父节点
     * @param length 待排序列尾元素索引
     */
    private static void adjustHeap(int[] arr, int parent, int length) {
        //将temp作为父节点
        int temp = arr[parent];
        //左孩子
        int lChild = 2 * parent + 1;

        while (lChild < length) {
            //右孩子
            int rChild = lChild + 1;
            // 如果有右孩子结点，并且右孩子结点的值大于左孩子结点，则选取右孩子结点
            if (rChild < length && arr[lChild] < arr[rChild]) {
                lChild++;
            }
            // 如果父结点的值已经大于孩子结点的值，则直接结束
            if (temp >= arr[lChild]) {
                break;
            }
            // 把孩子结点的值赋给父结点
            arr[parent] = arr[lChild];
            //选取孩子结点的左孩子结点,继续向下筛选
            parent = lChild;
            lChild = 2 * lChild + 1;
        }
        arr[parent] = temp;
    }

    /**
     * 基数排序
     * 原理：
     * 1.基数排序(radix sort)又称桶排序（bucket sort），
     * 2.相对于常见的比较排序，基数排序是一种分配式排序，
     * 3.即通过将所有数字分配到应在的位置最后再覆盖到原数组完成排序的过程。
     * @param array
     * @param d
     */
    public static void radixSort(int[] array, int d) {
        int n=1; //代表位数对应的数：1,10,100...
        int k=0; //保存每一位排序后的结果用于下一位的排序输入
        int length = array.length;
        int[][] bucket = new int[10][length]; //排序桶用于保存每次排序后的结果，这一位上排序结果相同的数字放在同一个桶里
        int[] order = new int[length]; //用于保存每个桶里有多少个数字
        while(n < d) {
            for(int num: array) { //将数组array里的每个数字放在相应的桶里
                int digit = (num/n)%10;
                bucket[digit][order[digit]] = num;
                order[digit]++;
            }
            for(int i=0; i<length; i++) { //将前一个循环生成的桶里的数据覆盖到原数组中用于保存这一位的排序结果
                if(order[i] != 0) { //这个桶里有数据，从上到下遍历这个桶并将数据保存到原数组中
                    for(int j=0; j<order[i]; j++) {
                        array[k] = bucket[i][j];
                        k++;
                    }
                }
                order[i] = 0; //将桶里计数器置0，用于下一次位排序
            }
            n*=10;
            k=0; //将k置0，用于下一轮保存位排序结果
        }
    }

    public static void main(String[] args) {
        // 1.冒泡排序
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

        // 2.快速排序
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

        // 3.二分查找
        int[] arrayInt = new int[1000];
        for (int i=0; i < 1000; i++) {
            arrayInt[i] = i;
        }
        binarySearch(arrayInt, 200);
        System.out.println();

        // 4.选择排序
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

        // 5.插入排序
        int[] array3 = {2,6,1,3,9,34,27,18,28,87,73,90};
        System.out.print("插入排序前： ");
        for (int i=0; i < array3.length; i++) {
            System.out.print(array3[i]+" ");
        }
        System.out.println();
        insertionSort(array3);
        System.out.print("插入排序后： ");
        for (int i=0; i < array3.length; i++) {
            System.out.print(array3[i]+" ");
        }
        System.out.println();
        System.out.println();

        // 6.归并排序
        int[] array4 = {2,6,1,3,9,34,27,18,28,87,73,90};
        System.out.print("归并排序前： ");
        for (int i=0; i < array4.length; i++) {
            System.out.print(array4[i]+" ");
        }
        System.out.println();
        mergeSort(array4);
        System.out.print("归并排序后： ");
        for (int i=0; i < array4.length; i++) {
            System.out.print(array4[i]+" ");
        }
        System.out.println();
        System.out.println();

        // 7.基数排序
        int[] array6 = {2,6,1,3,9,34,27,18,28,87,73,90};
        System.out.print("基数排序： ");
        for (int i=0; i < array6.length; i++) {
            System.out.print(array6[i]+" ");
        }
        System.out.println();
        radixSort(array6, 100);
        System.out.print("基数排序： ");
        for (int i=0; i < array6.length; i++) {
            System.out.print(array6[i]+" ");
        }
        System.out.println();
        System.out.println();

        // 8.堆排序
        int[] array7 = {2,6,1,3,9,34,27,18,28,87,73,90};
        System.out.print("堆排序： ");
        for (int i=0; i < array7.length; i++) {
            System.out.print(array7[i]+" ");
        }
        System.out.println();
        heapSort(array7);
        System.out.print("堆排序： ");
        for (int i=0; i < array7.length; i++) {
            System.out.print(array7[i]+" ");
        }
        System.out.println();
        System.out.println();
    }
}
