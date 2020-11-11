package com.example.utils.demo.example;

/**
 * 主要做测试使用
 */
public class TestExample {

    /**
     * 整数
     */
    public static void AbsoluteIntegerSort() {
        int[] arr = {17, -27, 3, 21, -5, 2, 18, -40};
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length -1 - i ; j++) {
                if (Math.abs(arr[j]) > Math.abs(arr[j + 1])) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }

        System.out.println("输出数组：");
        for (int i:arr) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        TestExample.AbsoluteIntegerSort();
    }
}
