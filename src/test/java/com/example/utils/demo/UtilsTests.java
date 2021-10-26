package com.example.utils.demo;

import com.alibaba.fastjson.JSON;
import com.example.utils.demo.util.EncryptionUtil;
import com.example.utils.demo.util.HttpClientUtil;
import com.example.utils.demo.util.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class UtilsTests {

    @Test
    public void test1() {
        System.out.print("Hello world!");
    }

    /**
     * base64加密
     */
    @Test
    public void encryptBASE64StrTest() {
        try {
            String key = EncryptionUtil.encryptBASE64("IbGYSaskGg48XSRSIIXTZQVKcbkH1hFj:lrs8qowzvugDxWk0kEFpua9ktXeBpSPg");
            System.out.print(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * base64解密
     */
    @Test
    public void decryptBASE64StrTest() {

        try {
            String value = EncryptionUtil.decryptBASE64Str("5bCP57qi");
            System.out.print(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * get请求
     */
    @Test
    public void sendHttpGetTest() {
        String url = "https://postman-echo.com/get?test=123";
        String result = HttpClientUtil.sendHttpGet(url);
        System.out.print(result);
    }

    /**
     * POST 请求
     * 获取access_token
     */
    @Test
    public void getAccessTokenTest() throws Exception{
        String url = "https://account-gyd.glodon.com/v3/api/oauth2/token";
        String appKey = "sSYtc8yYvjBj36ufc0rxS6kgEqUPVHaD";
        String appSecret = "HwKvuwLYMXOzDHR2PhLW8COzutVA41HD";
        String creds = String.format("%s:%s", appKey, appSecret);
        Map<String,String> contentMap = new HashMap<>();
        contentMap.put("grant_type", "client_credentials");
        String result = HttpClientUtil.sendPostFormData(url, contentMap, "Basic " + EncryptionUtil.encryptBASE64(creds));
        System.out.print(result);
    }

    /**
     * POST 请求
     * check token
     */
    @Test
    public void checkTokenTest() throws Exception{
        String url = "https://account-gyd.glodon.com/v3/api/oauth2/check_token";
        String appKey = "df9mCsrbLmAuSiiISs0jQhIeI5Ji9UnD";
        String appSecret = "JqRCaE8UTGkeL3btsCqvf4Pvli6jU8Rr";
        String creds = String.format("%s:%s", appKey, appSecret);
        Map<String,String> contentMap = new HashMap<>();
        contentMap.put("token", "cn-1b7a72a3-877a-4d5a-ae73-cff1a7a362b4");
        String result = HttpClientUtil.sendPostFormData(url, contentMap, "Basic " + EncryptionUtil.encryptBASE64(creds));
        System.out.print(result);
    }

    @Test
    public void tranTuoFengAndXiahuaxian() throws Exception{
        User user = new User();
        user.setUserName("小明");
        user.setUserEmail("123@163.com");
        user.setUserAge(18);
        //驼峰--->下划线
        String jsonStr = JsonUtils.toUnderlineJSONString(user);
        System.out.println("驼峰转换成下划线：" + jsonStr);
        //下划线-->驼峰
        User user1 = JsonUtils.toSnakeObject(jsonStr, User.class);
        System.out.println("下划线转换成驼峰形式：" + JSON.toJSONString(user1));
    }

}

@Data
class User {
    private String userName;
    private String userEmail;
    private int userAge;
}
