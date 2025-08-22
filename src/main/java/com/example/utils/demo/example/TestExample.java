package com.example.utils.demo.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

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
        // TestExample.AbsoluteIntegerSort();

        Long totalPrice = 50L;
        Long totalCount = 3L;
        Long unitPrice = new BigDecimal(totalPrice).divide(new BigDecimal(totalCount), 0, RoundingMode.UP).longValue();
        System.out.println(unitPrice);

        System.out.println(Long.MAX_VALUE);

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        List<String> confirmedPayExtTradeNoMap = list.stream()
                .filter(posPayRecord -> posPayRecord.equals("2")).
                collect(Collectors.toList());

        System.out.println(confirmedPayExtTradeNoMap);

        System.out.println(new Date().getTime());
        System.out.println(Integer.MAX_VALUE);

        Map<String,Map<String,Object>> attrKeyValueMap = JSON.parseObject("{\"sku\":{\"perLimit\":2},\"product\":{\"useDate\":{\"useEndDate\":\"2025-03-09\",\"useDateType\":1,\"useStartDate\":\"2025-02-27\"},\"useTime\":{\"useTimeType\":1},\"imageList\":[{\"url\":\"https://test-bucket.dmallcdn.com/voucher/test/third_party_voucher_web/2502272026/557aa0ea44a441d587e7e75c3b301ded\"}],\"showChannel\":1,\"canNoUseDate\":{\"enable\":false},\"recPersonNum\":1,\"refundPolicy\":1,\"dishesImageList\":[],\"applicationScope\":{\"applicationScopeType\":1,\"specialConsumptionDesc\":\"\"},\"descriptionRichText\":\"其他信息测试一下\",\"environmentImageList\":[],\"refundNeedMerchantConfirm\":true}}", Map.class);
        Map<String, Object> productMap = attrKeyValueMap.get("product");
        JSONObject desRichText = new JSONObject();
        desRichText.put("note_type",1);// 1-文本，2-图片
        desRichText.put("content", productMap.get("descriptionRichText"));// 1-内容
        System.out.println(Arrays.asList(desRichText));
        System.out.println(JSON.toJSONString(Arrays.asList(desRichText)));

        String[] split = new String[0];
        String[] split1 = new String[0];
        String keyword =" 12445    323,435 124  ";
        split = keyword.split(" ");
        split1 = keyword.split(",");
        System.out.println(JSON.toJSONString(split));
        for (String skuStr : split) {
            if (StringUtils.isBlank(skuStr)) {
                continue;
            }
            if (!StringUtils.isNumeric(skuStr)) {
                System.out.println("skuId不是数字");
            }
            System.out.println(skuStr);
        }

        if ("50".equals(String.valueOf(50))) {
            System.out.println("true");
        }
        String userId = "1245852";
        System.out.println(userId.substring(userId.length() - 2));

        try {
           Integer inn = null;
           System.out.println(inn.intValue());
        } catch (Exception e) {
            System.out.println(e);
        }


        String[] sapStoreIdArr = "123124,134235,465578".split(",");
        String[] sapStoreIds = new String[sapStoreIdArr.length];
        System.arraycopy(sapStoreIdArr, 0, sapStoreIds, 0, sapStoreIdArr.length);
        System.out.println(JSON.toJSONString(sapStoreIds));


    }
}
