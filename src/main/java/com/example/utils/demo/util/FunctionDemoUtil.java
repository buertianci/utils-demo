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

    /**
     * 题目描述： N 辆车沿着一条车道驶向位于 target 英里之外的共同目的地。
     * 每辆车 i 以恒定的速度 speed[i] （英里/小时），从初始位置 position[i] （英里） 沿车道驶向目的地。
     * 一辆车永远不会超过前面的另一辆车，但它可以追上去，并与前车以相同的速度紧接着行驶。
     * 此时，我们会忽略这两辆车之间的距离，也就是说，它们被假定处于相同的位置。
     * 车队 是一些由行驶在相同位置、具有相同速度的车组成的非空集合。
     * 注意，一辆车也可以是一个车队。即便一辆车在目的地才赶上了一个车队，它们仍然会被视作是同一个车队。
     * 输入：target = 12 position = [10,8,0,5,3] speed = [2,4,1,1,3]
     * 输出：3
     * @param target
     * @param position
     * @param speed
     * @return
     */
    public static int carTeam(int target, int[] position, int[] speed) {

        //返回车队数量
        int carTeamNum = 0;
        if (position.length == 0) {
            return 0;
        }

        //便于计算先排个序-位置
        for (int i=0; i<position.length-1; i++) {
            for (int j=i + 1; j<position.length-i-1; j++) {
                if (position[j+1] > position[j]) {
                    int temp = position[j];
                    position[j] = position[j+1];
                    position[j+1] = temp;
                }
            }
        }

        double[] time = new double[position.length];

        //计算每辆车速度
        for (int i=0; i<position.length; i++) {
            time[i] = (target - position[i]) / (speed[i]*1.0);
        }

        double firstCarTime = time[0];
        for (int i=0; i<time.length;) {
            if (firstCarTime >= time[i]) {
                i ++;
            } else {
                carTeamNum++;
                firstCarTime = time[i++];
            }
        }
        carTeamNum++ ;
        return carTeamNum;
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
        //会有多少车队到达目的地
        int[] position = new int[]{10, 8, 0, 5 ,3};
        int[] speed = new int[]{2, 4, 1, 1, 3};
        System.out.println(carTeam(12, position,speed));
    }
}
