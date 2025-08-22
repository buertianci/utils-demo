package com.example.utils.demo.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ChineseEncoderExample {

    /**
     * 基本使用示例
     */
    public static void basicUsageExample() {

        String text2 = "调用三方退款异常，请联系商家IT，三方返回失败原因：支付失败无需退款";
        System.out.println("原文: " + text2);
        System.out.println("16进制编码: " + ChineseEncoderSimple.encodeHex(text2));

        String text3 = "调用三方退款异常，请联系商家IT，三方返回失败原因：退款金额与支付金额不等";
        System.out.println("原文: " + text3);
        System.out.println("16进制编码: " + ChineseEncoderSimple.encodeHex(text3));

        System.out.println();
    }

    /**
     * 生成唯一编码
     */
    public static String generateAcCode(Long venderId){
        String acCode = "AC";
        acCode += venderId;
        acCode += DateFormatUtils.format(new Date(), "yyyyMMdd");
        // 校验编码是否已经存在
        boolean flag = true;
        for (int i = 0; i < 3; i++) {
            String randomNum = String.format("%04d", 1000 + (int)(Math.random() * 9000));
            int num = 1;
            if (num == 0) {
                acCode = acCode + randomNum;
                flag = false;
                break;
            }
        }
        if (flag) {
            acCode += String.format("%04d", 1000 + (int)(Math.random() * 9900));
        }
        return acCode;
    }

    /**
     * 主方法
     */
    public static void main(String[] args) {
        basicUsageExample();
        System.out.println(String.format("%06d", 11002));
        System.out.println(String.format("%04d", 1000 + (int)(Math.random() * 9000)));
        System.out.println(String.format("%04d", 1000 + (int)(Math.random() * 9000)));
        System.out.println(generateAcCode(1102L));
        String[] storeIdArr = "1545,2342".split(",");
        // 将 storeIdArr 转换成 List 用流式方法
        List<Integer> storeIdList = Arrays.stream(storeIdArr).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(storeIdList));
    }
}
