package com.example.utils.demo.work;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class AladinUtils {
    public static void main(String[] args) {
        // 加密方法
        String str = "pay_apportion_platform_unusable_assets={\"WechatShoppingMall\":[5]}";
        encodeTOString(str);

        // 解密方法
        String encodeStr = "eyJXZWNoYXRNaW5pUHJvZ3JhbSI6WzVdLCJXZWNoYXRTaG9wcGluZ01hbGwiOls1XX0=";
        decodeTOString(encodeStr);

        System.out.println(System.currentTimeMillis());
    }

    /**
     * 解密
     */
    private static void decodeTOString(String encodeStr) {
        try {
            byte[] result = Base64.getDecoder().decode(encodeStr);
            String property = new String(result,"GBK");
            System.out.println(property);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置-加密
     */
    private static void encodeTOString(String str) {
        try {
            if (Objects.nonNull(str) && !str.isEmpty()) {
                String[] split = str.split("=", 2);
                if (split.length != 2) {
                    return;
                }
                String encode = Base64.getEncoder().encodeToString(split[1].getBytes("GBK"));
                System.out.println(split[0] + "=" + encode);
            } else {
                // 中文base64编码
                File file = new File("/Users/zgq/Desktop/aladdin_properties.txt");
                // 按行读取配置文件
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        if (line.trim().isEmpty()) {
                            System.out.println(line);
                            continue;
                        }
                        if (!line.startsWith("#")) {
                            //System.out.println(line);
                            continue;
                        }

                        String[] split = line.split("=", 2);

                        if (split.length != 2) {
                            System.out.println(line);
                            continue;
                        }
                        System.out.println(line);
                        String encode = Base64.getEncoder().encodeToString(split[1].getBytes("GBK"));
                        System.out.println(split[0].split(" ", 2)[1] + "=" + encode);
                    } catch (Exception e) {
                        System.out.println("===" + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
