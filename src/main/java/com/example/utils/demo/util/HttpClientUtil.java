package com.example.utils.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpClientUtil {

    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String sendHttpGet(String url) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("Accept", "application/json");
            response = httpClient.execute(httpGet);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * POST请求，JSON格式参数
     * @param url
     * @param jsonString
     * @return
     */
    public static String sendHttpPost(String url, String jsonString) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"utf-8"));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * form-data有参数 POST 请求
     * @param url
     * @param reqParam
     * @param auth
     * @return
     * @throws Exception
     */
    public static String sendPostFormData(String url, Map<String, String> reqParam, String auth) throws Exception{

        //CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

            post.setHeader("Authorization", auth);
            // setConnectTimeout：设置连接超时时间，单位毫秒。
            // setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。
            // 这个属性是新加的属性，因为目前版本是可以共享连接池的。setSocketTimeout：请求获取数据的超时时间，单位毫秒。
            // 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
            post.setConfig(defaultRequestConfig);
            List<NameValuePair> parameters = new ArrayList<>(0);
            if (reqParam != null && reqParam.size() > 0) {
                for(Map.Entry<String, String> param : reqParam.entrySet()){
                    parameters.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            post.setEntity(formEntity);
            // 执行post请求.
            CloseableHttpResponse response = httpClient.execute(post);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity,Charset.forName("UTF-8"));
                }
            } finally {
                response.close();
            }
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
