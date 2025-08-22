package com.example.utils.demo.work;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @program: openPlatform
 * @description: 工具类
 * @author: Mr.YS
 * @CreatDate: 2019/5/20/020 17:20
 */
public class Util {

    /**
     * 获取到订单号
     *
     * @return 订单号
     */
    public static String getMerchantOrderId() {
        return  DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + RandomStringUtils.randomNumeric(7);
    }

    /**
     * @param appId   10037e6f66f2d0f901672aa27d690006
     * @param appKey  47ace12ae3b348fe93ab46cee97c6fde
     * @param content json字符串 String body = "{\"merchantCode\":\"123456789900081\",\"terminalCode\":\"00810001\",\"merchantOrderId\":\"20123333644493200\",\"transactionAmount\":\"1\",\"merchantRemark\":\"测试\",\"payMode\":\"CODE_SCAN\",\"payCode\":\"285668667587422761\",\"transactionCurrencyCode\":\"156\"}";
     * @return
     * @throws Exception
     * @Description : open-body-sig方式获取到Authorization 的值
     */
    public static String getOpenBodySig(String appId, String appKey, String timestamp, String nonce, String content) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
        String bodyDigest = DigestUtils.sha256Hex(content); //eg:cc18f43baa87fe658146221d3f16e3b5a50d30f0c984407f74913ef6dcda8ee1
        String str1_C = appId + timestamp + nonce + bodyDigest;
        byte[] localSignature = hmacSHA256(str1_C.getBytes(), appKey.getBytes());
        String signature = Base64.encodeBase64String(localSignature);
        System.out.println(signature);
        return signature;
    }

    /**
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @Description
     */
    public static byte[] hmacSHA256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data);
    }

    public static String send(String url, String entity, String authorization) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", authorization);
        StringEntity se = new StringEntity(entity, "UTF-8");
        se.setContentType("application/json");
        httpPost.setEntity(se);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity1 = response.getEntity();
        String resStr = null;
        if (entity1 != null) {
            resStr = EntityUtils.toString(entity1, "UTF-8");
        }
        httpClient.close();
        response.close();
        return resStr;

    }

}
