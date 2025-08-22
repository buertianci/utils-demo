package com.example.utils.demo.example;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FixPosOrder {

    private static String pwd = "pos1120250815";
    
    // Excel数据实体类
    @Data
    public static class AccountData {
        private String posOrderNo;
        private String extTradeNos;
    }
    
    // Excel监听器
    public static class AccountDataListener extends AnalysisEventListener<AccountData> {
        private List<AccountData> dataList = new ArrayList<>();
        
        @Override
        public void invoke(AccountData data, AnalysisContext context) {
            System.out.println("读取到数据: " + data);
            dataList.add(data);
        }
        
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            System.out.println("Excel读取完成，共" + dataList.size() + "条数据");
        }
        
        public List<AccountData> getDataList() {
            return dataList;
        }
    }
    
    public static void main(String[] args) {
        // Excel文件路径 - 请修改为你的实际文件路径
        String excelFilePath = "/Users/zgq/Desktop/家园便利-支付明细信息.xlsx";
        
        try {
            // 1. 读取Excel文件
            System.out.println("开始读取Excel文件: " + excelFilePath);
            AccountDataListener listener = new AccountDataListener();
            EasyExcel.read(excelFilePath, AccountData.class, listener).sheet().doRead();
            
            List<AccountData> dataList = listener.getDataList();
            if (dataList.isEmpty()) {
                System.out.println("Excel文件中没有数据");
                return;
            }
            
            // 2. 批量调用API
            System.out.println("开始批量调用API...");
            for (int i = 0; i < dataList.size(); i++) {
                AccountData data = dataList.get(i);
                System.out.println("\n处理第" + (i + 1) + "条数据: data: {}" + data);
                System.out.println("posOrderNo: " + data.getPosOrderNo());
                System.out.println("extTradeNos: " + data.getExtTradeNos());
                System.out.println("pwd: " + pwd);
                
                String result = callApi(data);
                System.out.println("API响应: " + result);
                
                // 添加延迟避免请求过于频繁
                Thread.sleep(100);
            }
            
            System.out.println("\n所有数据处理完成！");
            
        } catch (Exception e) {
            System.err.println("处理过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 调用API
     */
    private static String callApi(AccountData data) throws Exception {
        String baseUrl = "http://10.13.202.247:8080/backdoor/checkAccount";
        
        // 构建请求URL
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?");
        urlBuilder.append("posOrderNo=").append(URLEncoder.encode(data.getPosOrderNo(), StandardCharsets.UTF_8.name()));
        urlBuilder.append("&extTradeNos=").append(URLEncoder.encode(data.getExtTradeNos(), StandardCharsets.UTF_8.name()));
        urlBuilder.append("&pwd=").append(URLEncoder.encode(pwd, StandardCharsets.UTF_8.name()));
        
        String urlString = urlBuilder.toString();
        System.out.println("请求URL: " + urlString);
        
        // 发送GET请求
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        try {
            // 设置请求头
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("User-Agent", "PostmanRuntime-ApipostRuntime/1.1.0");
            connection.setRequestProperty("content-type", "multipart/form-data");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            
            // 获取响应
            int responseCode = connection.getResponseCode();
            System.out.println("响应码: " + responseCode);
            
            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }
            
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            return response.toString();
            
        } finally {
            connection.disconnect();
        }
    }
}
