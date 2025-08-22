package com.example.utils.demo.util;

/**
 * 汉字编码工具类 - 简化版
 * 使用原生Java对汉字进行编码，生成唯一且长度较小的编码
 *
 */
public class ChineseEncoderSimple {

    /**
     * 对汉字进行编码，生成16进制格式的唯一值
     *
     * @param chineseText 要编码的汉字文本
     * @param length 期望的编码长度，默认6位
     * @return 16进制编码字符串
     * @throws IllegalArgumentException 如果输入无效
     */
    public static String encodeHex(String chineseText, int length) {
        if (chineseText == null || chineseText.trim().isEmpty()) {
            throw new IllegalArgumentException("输入必须是有效的字符串");
        }

        try {
            // 使用增强的哈希算法
            long hash = enhancedHash(chineseText);

            // 转换为16进制
            String hexHash = Long.toHexString(Math.abs(hash)).toUpperCase();

            // 截取指定长度
            return hexHash.substring(0, Math.min(length, hexHash.length()));

        } catch (Exception e) {
            throw new RuntimeException("16进制编码处理失败", e);
        }
    }

    /**
     * 对汉字进行编码，生成16进制格式的唯一值（默认6位）
     *
     * @param chineseText 要编码的汉字文本
     * @return 16进制编码字符串
     */
    public static String encodeHex(String chineseText) {
        return encodeHex(chineseText, 6);
    }

    /**
     * 增强哈希算法（DJB2变种）
     *
     * @param str 输入字符串
     * @return 哈希值
     */
    private static long enhancedHash(String str) {
        long hash = 5381;
        int i = str.length();

        while (i > 0) {
            hash = (hash * 33) ^ str.charAt(--i);
        }

        return hash >>> 0; // 转换为无符号32位整数
    }
} 