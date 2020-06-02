package com.example.utils.demo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 一些方法的练习
 */
public class FunctionDemoUtil {

    /**
     * 反转字符串
     */
    public static class ReverseString {

        /**
         * 方法一：reserve方法
         * 这里使用StringBuilder
         * @param str
         * @return
         */
        public static String reserveStrByStringBuilder(String str) {
            if (str != null && str.length() > 0) {
                return new StringBuilder(str).reverse().toString();
            }
            return str;
        }

        /**
         * 方法二：递归方法
         * 使用字符串截取拼接方法
         * @param str
         * @return
         */
        public static String reverseStrByRecursion(String str) {
            if (str == null || str.length() <= 1) {
                return str;
            }
            return reverseStrByRecursion(str.substring(1)) + str.charAt(0);
        }

        /**
         * 方法三：自己实现
         * 使用字符数组实现
         * @return
         */
        public static String reverseStrBySelf(String str) {
            if (str != null && str.length() > 0) {
                int length = str.length();
                char[] strChar = new char[length];
                for (int i = length - 1; i >= 0; i--) {
                    strChar[length - 1 - i] = str.charAt(i);
                }
                return new String(strChar);
            }
            return str;
        }

        /**
         * Collections进行反转
         * @param str
         * @return
         */
        public static String reverseStrByCollection(String str) {
            if (str != null && str.length() > 0) {
                char[] chars = str.toCharArray();
                List charList = new ArrayList();
                for (char b: chars) {
                    charList.add(b);
                }
                Collections.reverse(charList);
                StringBuilder stringBuilder = new StringBuilder(charList.size());
                for (Object ch: charList) {
                    stringBuilder.append(ch);
                }
                return stringBuilder.toString();
            }
            return str;
        }
    }

    public static void main(String[] args) {
        //reserve方法
        System.out.println("reserve方法：    "+ReverseString.reserveStrByStringBuilder("abcdef"));
        //递归反转string
        System.out.println("递归方法：        "+ReverseString.reverseStrByRecursion("abcdef"));
        //字符串数组实现
        System.out.println("字符串数组方法：   "+ReverseString.reverseStrBySelf("abcdef"));
        //Collections实现
        System.out.println("Collections方法： "+ReverseString.reverseStrByCollection("abcdef"));
    }
}
