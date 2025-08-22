package com.example.utils.demo.work;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.w3c.dom.stylesheets.LinkStyle;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: openPlatform
 * @description:   公众号支付
 */
public class PublicPay {
    static String appId = "469381425d378866015d7e2d2327094a";
    static String appKey = "d7ab20874cea4a3d899c1b1aab721569";
    static String timestamp;
    static String nonce;
    static String authorization;
    public static void main(String[] args) throws Exception {
        //pay(4);
        //query();
        //refund();

//        Pattern pattern = Pattern.compile(null);
//        Matcher matcher = pattern.matcher(null);
//        if (!matcher.find()) {
//            System.out.println("匹配不到");
//        } else {
//            System.out.println("匹配到");
//        }

        List<String> res = new ArrayList<>();
        //判断对账里是否有在线
        if (res == null) {
            System.out.println("res空啦");
        } else {
            String re = res.get(0);
            System.out.println("res获取空啦");
        }
    }

    public static void pay(int payFlay) throws Exception {
        timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        nonce = UUID.randomUUID().toString().replace("-", "");
        JSONObject json = new JSONObject();
        json.put("instMid", "YUEDANDEFAULT"); // 业务类型
        json.put("mid", "8984101541156C0"); // 商户号
        json.put("tid", "7MUN8GAH");    // 终端号
        json.put("totalAmount", "1");      // 支付总金额
        json.put("merOrderId", "14GH"+ Util.getMerchantOrderId()); // 商户订单号
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间
        json.put("returnUrl", "https://testwelfare.dmall-os.cn/payResult?venderid=42961");    // 回跳地址

        /* 分账部分 */
//        json.put("divisionFlag", true); // 分账标记
//        json.put("platformAmount", 10); // 平台商户分账金额
//        ArrayList subOrders = new ArrayList<>();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("mid", "子商户号");
//        jsonObject.put("totalAmount", 10);
//        subOrders.add(jsonObject);
//        json.put("subOrders", subOrders);
        System.out.println("请求报文:\n"+json);
        String signatrue = Util.getOpenBodySig(appId, appKey, timestamp, nonce, json.toString());
        String url = "";
        if (payFlay == 1) {
            // 公众号-下单
            url = "https://test-api-open.chinaums.com/v1/netpay/webpay/pay";
            // 线上环境
            url = "https://api-mop.chinaums.com/v1/netpay/webpay/pay";
        } else if (payFlay == 2) {
            // H5支付-微信H5支付下单
            url = "https://test-api-open.chinaums.com/v1/netpay/wxpay/h5-pay";
            // 线上环境
            url = "https://api-mop.chinaums.com/v1/netpay/wxpay/h5-pay";
        } else if (payFlay == 3) {
            // H5支付-支付宝H5支付下单
            url = "https://test-api-open.chinaums.com/v1/netpay/trade/h5-pay";
            // 线上环境
            url = "https://api-mop.chinaums.com/v1/netpay/trade/h5-pay";
        } else if (payFlay == 4) {
            // 微信H5转小程序支付下单
            url = "https://test-api-open.chinaums.com/v1/netpay/wxpay/h5-to-minipay";
            // 线上环境
            url = "https://api-mop.chinaums.com/v1/netpay/wxpay/h5-to-minipay";
        }
        //GET 请求形式拼接认证方式
        String param="authorization=OPEN-FORM-PARAM" + "&appId=" + appId + "&timestamp=" + timestamp + "&nonce=" + nonce + "&content=" + URLEncoder.encode(json.toString(), "UTF-8") + "&signature=" + URLEncoder.encode(signatrue, "UTF-8");
        System.out.println("URL:\n" + url + "?" + param);
    }


    //支付查询
    public static void query() throws  Exception {
        timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        nonce = UUID.randomUUID().toString().replace("-", "");
        JSONObject json = new JSONObject();
        json.put("instMid", "YUEDANDEFAULT"); // 业务类型
        json.put("mid", "8984101541156C0"); // 商户号
        json.put("tid", "7MUN8GAH");    // 终端号

        json.put("srcReserve", "28296107302795900022640021644242");
        json.put("merOrderId", "14GH202410301713117879042"); // 商户订单号
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间

        System.out.println("请求报文:\n"+json);
        String signatrue = Util.getOpenBodySig(appId, appKey, timestamp, nonce, json.toString());
        String url = "https://test-api-open.chinaums.com/v1/netpay/query";
        url = "https://api-mop.chinaums.com/v1/netpay/query";
         //url = "https://test-api-open.chinaums.com/v1/netpay/refund-query";
        //post 请求形式拼接认证方式
        authorization="OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + signatrue + "\"";
        System.out.println("请求头："+authorization);
        String send = Util.send(url, json.toString(), authorization);
        System.out.println("返回结果:\n" + send);
    }


    //退款
    public static void refund() throws  Exception {
        timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        nonce = UUID.randomUUID().toString().replace("-", "");
        JSONObject json = new JSONObject();
        json.put("instMid", "YUEDANDEFAULT"); // 业务类型
        json.put("mid", "898340149000005"); // 商户号
        json.put("tid", "88880001");    // 终端号

        json.put("merOrderId", "1017202203101303118379751113"); // 商户订单号
        json.put("refundOrderId", "1017202203101309013075978093"); // 商户订单号
        json.put("refundAmount", 2); // 商户订单号

        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间

        System.out.println("请求报文:\n"+json);
        String signatrue = Util.getOpenBodySig(appId, appKey, timestamp, nonce, json.toString());
        String url = "https://test-api-open.chinaums.com/v1/netpay/refund";
        //post 请求形式拼接认证方式
        authorization="OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + signatrue + "\"";
        System.out.println("请求头："+authorization);
        String send = Util.send(url, json.toString(), authorization);
        System.out.println("返回结果:\n" + send);
    }



}
