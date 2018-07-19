package com.zhaoqing;  
  
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zhaoqing.util.Configure;
import com.zhaoqing.util.DateUtil;
import com.zhaoqing.util.HttpsRequest;
import com.zhaoqing.util.Util;
  
public class WxPayApi  
{  
    /** 
    * 提交被扫支付API 
    * 收银员使用扫码设备读取微信用户刷卡授权码以后，二维码或条码信息传送至商户收银台， 
    * 由商户收银台或者商户后台调用该接口发起支付。 
    * @param WxPayData inputObj 提交给被扫支付API的参数 
    * @param int timeOut 超时时间 
    * @throws WxPayException 
    * @return 成功时返回调用结果，其他抛异常 
     * @throws IOException  
     * @throws KeyStoreException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData Micropay(WxPayData inputObj, int timeOut) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, WxPayException, ParserConfigurationException, SAXException  
    {  
        String url =Configure.PAY_API;  
        //检测必填参数  
        if (!inputObj.IsSet("body"))  
        {  
            throw new WxPayException("提交被扫支付API接口中，缺少必填参数body！");  
        }  
        else if (!inputObj.IsSet("out_trade_no"))  
        {  
            throw new WxPayException("提交被扫支付API接口中，缺少必填参数out_trade_no！");  
        }  
        else if (!inputObj.IsSet("total_fee"))  
        {  
            throw new WxPayException("提交被扫支付API接口中，缺少必填参数total_fee！");  
        }  
        else if (!inputObj.IsSet("auth_code"))  
        {  
            throw new WxPayException("提交被扫支付API接口中，缺少必填参数auth_code！");  
        }  
     
        inputObj.SetValue("spbill_create_ip", Configure.getIP());//终端ip  
        inputObj.SetValue("appid", Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id", Configure.getMchid());//商户号  
        inputObj.SetValue("nonce_str", Util.getUuid().toString().replace("-", ""));//随机字符串  
        inputObj.SetValue("sign", inputObj.MakeSign());//签名  
        String xml = inputObj.ToXml();  
  
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut);  
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
        WxPayData result = new WxPayData();  
        result.FromXml(response);  
  
  
  
        return result;  
    }  
  
      
    /** 
    *     
    * 查询订单 
    * @param WxPayData inputObj 提交给查询订单API的参数 
    * @param int timeOut 超时时间 
    * @throws WxPayException 
    * @return 成功时返回订单查询结果，其他抛异常 
     * @throws IOException  
     * @throws KeyStoreException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData OrderQuery(WxPayData inputObj, int timeOut) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, WxPayException, ParserConfigurationException, SAXException  
    {  
        String url = Configure.PAY_QUERY_API;  
        //检测必填参数  
        if (!inputObj.IsSet("out_trade_no") && !inputObj.IsSet("transaction_id"))  
        {  
            throw new WxPayException("订单查询接口中，out_trade_no、transaction_id至少填一个！");  
        }  
  
        inputObj.SetValue("appid", Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id", Configure.getMchid());//商户号  
        inputObj.SetValue("nonce_str", WxPayApi.GenerateNonceStr());//随机字符串  
        inputObj.SetValue("sign", inputObj.MakeSign());//签名  
  
        String xml = inputObj.ToXml();  
  
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut);  
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
        WxPayData result = new WxPayData();  
        result.FromXml(response);  
  
        return result;  
    }  
  
  
    /** 
    *  
    * 撤销订单API接口 
    * @param WxPayData inputObj 提交给撤销订单API接口的参数，out_trade_no和transaction_id必填一个 
    * @param int timeOut 接口超时时间 
    * @throws WxPayException 
    * @return 成功时返回API调用结果，其他抛异常 
     * @throws IOException  
     * @throws KeyStoreException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData Reverse(WxPayData inputObj, int timeOut) throws WxPayException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ParserConfigurationException, SAXException  
    {  
        String url =Configure.REVERSE_API;  
        //检测必填参数  
        if (!inputObj.IsSet("out_trade_no") && !inputObj.IsSet("transaction_id"))  
        {  
            throw new WxPayException("撤销订单API接口中，参数out_trade_no和transaction_id必须填写一个！");  
        }  
  
        inputObj.SetValue("appid", Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id", Configure.getMchid());//商户号  
        inputObj.SetValue("nonce_str", GenerateNonceStr());//随机字符串  
        inputObj.SetValue("sign", inputObj.MakeSign());//签名  
        String xml = inputObj.ToXml();  
  
  
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut);  
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
        WxPayData result = new WxPayData();  
        result.FromXml(response);  
  
        return result;  
    }  
  
  
    /** 
    *  
    * 申请退款 
    * @param WxPayData inputObj 提交给申请退款API的参数 
    * @param int timeOut 超时时间 
    * @throws WxPayException 
    * @return 成功时返回接口调用结果，其他抛异常 
     * @throws IOException  
     * @throws KeyStoreException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData Refund(WxPayData inputObj, int timeOut) throws WxPayException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ParserConfigurationException, SAXException  
    {  
        String url = Configure.REFUND_API;  
        //检测必填参数  
        if (!inputObj.IsSet("out_trade_no") && !inputObj.IsSet("transaction_id"))  
        {  
            throw new WxPayException("退款申请接口中，out_trade_no、transaction_id至少填一个！");  
        }  
        else if (!inputObj.IsSet("out_refund_no"))  
        {  
            throw new WxPayException("退款申请接口中，缺少必填参数out_refund_no！");  
        }  
        else if (!inputObj.IsSet("total_fee"))  
        {  
            throw new WxPayException("退款申请接口中，缺少必填参数total_fee！");  
        }  
        else if (!inputObj.IsSet("refund_fee"))  
        {  
            throw new WxPayException("退款申请接口中，缺少必填参数refund_fee！");  
        }  
        else if (!inputObj.IsSet("op_user_id"))  
        {  
            throw new WxPayException("退款申请接口中，缺少必填参数op_user_id！");  
        }  
  
        inputObj.SetValue("appid", Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id", Configure.getMchid());//商户号  
        inputObj.SetValue("nonce_str", Util.getUuid().toString().replace("-", ""));//随机字符串  
        inputObj.SetValue("sign", inputObj.MakeSign());//签名  
          
        String xml = inputObj.ToXml();  
    
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut);  
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
        WxPayData result = new WxPayData();  
        result.FromXml(response);  
  
        return result;  
    }  
  
  
    /** 
    *  
    * 查询退款 
    * 提交退款申请后，通过该接口查询退款状态。退款有一定延时， 
    * 用零钱支付的退款20分钟内到账，银行卡支付的退款3个工作日后重新查询退款状态。 
    * out_refund_no、out_trade_no、transaction_id、refund_id四个参数必填一个 
    * @param WxPayData inputObj 提交给查询退款API的参数 
    * @param int timeOut 接口超时时间 
    * @throws WxPayException 
    * @return 成功时返回，其他抛异常 
     * @throws IOException  
     * @throws KeyStoreException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData RefundQuery(WxPayData inputObj, int timeOut) throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, WxPayException, ParserConfigurationException, SAXException  
    {  
        String url = Configure.REFUND_QUERY_API;  
        //检测必填参数  
        if(!inputObj.IsSet("out_refund_no") && !inputObj.IsSet("out_trade_no") &&  
            !inputObj.IsSet("transaction_id") && !inputObj.IsSet("refund_id"))  
        {  
            throw new WxPayException("退款查询接口中，out_refund_no、out_trade_no、transaction_id、refund_id四个参数必填一个！");  
        }  
  
        inputObj.SetValue("appid",Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id",Configure.getMchid());//商户号  
        inputObj.SetValue("nonce_str",GenerateNonceStr());//随机字符串  
        inputObj.SetValue("sign",inputObj.MakeSign());//签名  
  
        String xml = inputObj.ToXml();  
      
        
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut);  
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
        WxPayData result = new WxPayData();  
        result.FromXml(response);  
  
        return result;  
    }  
  
  
    /** 
    * 下载对账单 
    * @param WxPayData inputObj 提交给下载对账单API的参数 
    * @param int timeOut 接口超时时间 
    * @throws WxPayException 
    * @return 成功时返回，其他抛异常 
     * @throws IOException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyStoreException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData DownloadBill(WxPayData inputObj, int timeOut) throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, IOException, WxPayException, ParserConfigurationException, SAXException  
    {  
        String url = Configure.DOWNLOAD_BILL_API;  
        //检测必填参数  
        if (!inputObj.IsSet("bill_date"))  
        {  
            throw new WxPayException("对账单接口中，缺少必填参数bill_date！");  
        }  
  
        inputObj.SetValue("appid", Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id", Configure.getMchid());//商户号  
        inputObj.SetValue("nonce_str", GenerateNonceStr());//随机字符串  
        inputObj.SetValue("sign", inputObj.MakeSign());//签名  
  
        String xml = inputObj.ToXml();  
  
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut);  
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
       
        WxPayData result = new WxPayData();  
        //若接口调用失败会返回xml格式的结果  
        if (response.startsWith("<xml>"))  
        {  
          
               result.FromXml(response);  
        }  
        //接口调用成功则返回非xml格式的数据  
        else  
            result.SetValue("result", response);  
  
        return result;  
    }  
  
    /** 
     * 拉取订单评价数据
     * @param WxPayData inputObj 提交给下载对账单API的参数 
     * @param int timeOut 接口超时时间 
     * @throws WxPayException 
     * @return 成功时返回，其他抛异常 
      * @throws IOException  
      * @throws NoSuchAlgorithmException  
      * @throws KeyStoreException  
      * @throws KeyManagementException  
      * @throws UnrecoverableKeyException  
      * @throws SAXException  
      * @throws ParserConfigurationException  
     */  
     public static WxPayData batchQueryComment(WxPayData inputObj,int offset, int timeOut) throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, IOException, WxPayException, ParserConfigurationException, SAXException  
     {  
         String url = Configure.batchQueryComment;  
         //检测必填参数  
         if (!inputObj.IsSet("offset"))  
         {  
             throw new WxPayException("对账单接口中，缺少必填参数offset！");  
         }  
   
         inputObj.SetValue("appid", Configure.getAppid());//公众账号ID  
         inputObj.SetValue("mch_id", Configure.getMchid());//商户号  
         inputObj.SetValue("nonce_str", GenerateNonceStr());//随机字符串  
         inputObj.SetValue("sign", inputObj.MakeSign());//签名 
         inputObj.SetValue("offset", offset);//位移
         inputObj.SetValue("begin_time", DateUtil.formatDateyyyyMMddHHmmss(DateUtil.addDay(new Date(),-7)));//开始日期
         inputObj.SetValue("end_time", DateUtil.formatDateyyyyMMddHHmmss(new Date()));//结束日期
        
         String xml = inputObj.ToXml();  
   
         HttpsRequest https=new HttpsRequest();  
         https.setSocketTimeout(timeOut);  
         String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
        
         WxPayData result = new WxPayData();  
         //若接口调用失败会返回xml格式的结果  
         if (response.startsWith("<xml>"))  
         {  
           
                result.FromXml(response);  
         }  
         //接口调用成功则返回非xml格式的数据  
         else  
             result.SetValue("result", response);  
   
         return result;  
     }  
  
    /** 
    *  
    * 转换短链接 
    * 该接口主要用于扫码原生支付模式一中的二维码链接转成短链接(weixin://wxpay/s/XXXXXX)， 
    * 减小二维码数据量，提升扫描速度和精确度。 
    * @param WxPayData inputObj 提交给转换短连接API的参数 
    * @param int timeOut 接口超时时间 
    * @throws WxPayException 
    * @return 成功时返回，其他抛异常 
     * @throws IOException  
     * @throws KeyStoreException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData ShortUrl(WxPayData inputObj, int timeOut) throws WxPayException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ParserConfigurationException, SAXException  
    {  
        String url = Configure.Shorturl_API;  
        //检测必填参数  
        if(!inputObj.IsSet("long_url"))  
        {  
            throw new WxPayException("需要转换的URL，签名用原串，传输需URL encode！");  
        }  
  
        inputObj.SetValue("appid",Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id",Configure.getMchid());//商户号  
        inputObj.SetValue("nonce_str",GenerateNonceStr());//随机字符串     
        inputObj.SetValue("sign",inputObj.MakeSign());//签名  
        String xml = inputObj.ToXml();  
      
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut);  
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
        WxPayData result = new WxPayData();  
        result.FromXml(response);  
  
  
  
      
        return result;  
    }  
  
  
    /** 
    *  
    * 统一下单 
    * @param WxPaydata inputObj 提交给统一下单API的参数 
    * @param int timeOut 超时时间 
    * @throws WxPayException 
    * @return 成功时返回，其他抛异常 
     * @throws IOException  
     * @throws KeyStoreException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData UnifiedOrder(WxPayData inputObj, int timeOut) throws WxPayException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ParserConfigurationException, SAXException  
    {  
        String url =Configure.UnifiedOrder_API;  
        //检测必填参数  
        if (!inputObj.IsSet("out_trade_no"))  
        {  
            throw new WxPayException("缺少统一支付接口必填参数out_trade_no！");  
        }  
        else if (!inputObj.IsSet("body"))  
        {  
            throw new WxPayException("缺少统一支付接口必填参数body！");  
        }  
        else if (!inputObj.IsSet("total_fee"))  
        {  
            throw new WxPayException("缺少统一支付接口必填参数total_fee！");  
        }  
        else if (!inputObj.IsSet("trade_type"))  
        {  
            throw new WxPayException("缺少统一支付接口必填参数trade_type！");  
        }  
  
        //关联参数  
        if (inputObj.GetValue("trade_type").toString() == "JSAPI" && !inputObj.IsSet("openid"))  
        {  
            throw new WxPayException("统一支付接口中，缺少必填参数openid！trade_type为JSAPI时，openid为必填参数！");  
        }  
        if (inputObj.GetValue("trade_type").toString() == "NATIVE" && !inputObj.IsSet("product_id"))  
        {  
            throw new WxPayException("统一支付接口中，缺少必填参数product_id！trade_type为JSAPI时，product_id为必填参数！");  
        }  
  
        //异步通知url未设置，则使用配置文件中的url  
        if (!inputObj.IsSet("notify_url"))  
        {  
            inputObj.SetValue("notify_url", Configure.NOTIFY_URL);//异步通知url  
        }  
  
        inputObj.SetValue("appid", Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id", Configure.getMchid());//商户号  
        inputObj.SetValue("spbill_create_ip", Configure.getIP());//终端ip           
        inputObj.SetValue("nonce_str", GenerateNonceStr());//随机字符串  
  
        //签名  
        inputObj.SetValue("sign", inputObj.MakeSign());  
        String xml = inputObj.ToXml();  
  
     
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut); 
        System.out.println("请求的xml: "+xml);
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API 
        System.out.println("响应信息: "+response);
        WxPayData result = new WxPayData();  
        result.FromXml(response);  
  
  
  
        return result;  
    }  
  
  
    /** 
    *  
    * 关闭订单 
    * @param WxPayData inputObj 提交给关闭订单API的参数 
    * @param int timeOut 接口超时时间 
    * @throws WxPayException 
    * @return 成功时返回，其他抛异常 
     * @throws IOException  
     * @throws KeyStoreException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData CloseOrder(WxPayData inputObj, int timeOut) throws WxPayException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ParserConfigurationException, SAXException  
    {  
          
        String url =Configure.CloseOrder_API;  
        //检测必填参数  
        if(!inputObj.IsSet("out_trade_no"))  
        {  
            throw new WxPayException("关闭订单接口中，out_trade_no必填！");  
        }  
  
        inputObj.SetValue("appid",Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id",Configure.getMchid());//商户号  
        inputObj.SetValue("nonce_str",GenerateNonceStr());//随机字符串         
        inputObj.SetValue("sign",inputObj.MakeSign());//签名  
        String xml = inputObj.ToXml();  
      
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut);  
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
        WxPayData result = new WxPayData();  
        result.FromXml(response);  
  
      
        return result;  
    }  
  
  
    /** 
    *  
    * 测速上报接口实现 
    * @param WxPayData inputObj 提交给测速上报接口的参数 
    * @param int timeOut 测速上报接口超时时间 
    * @throws WxPayException 
    * @return 成功时返回测速上报接口返回的结果，其他抛异常 
     * @throws IOException  
     * @throws KeyStoreException  
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     * @throws UnrecoverableKeyException  
     * @throws SAXException  
     * @throws ParserConfigurationException  
    */  
    public static WxPayData Report(WxPayData inputObj, int timeOut) throws WxPayException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, ParserConfigurationException, SAXException  
    {  
        String url = Configure.REPORT_API;  
        //检测必填参数  
        if(!inputObj.IsSet("interface_url"))  
        {  
            throw new WxPayException("接口URL，缺少必填参数interface_url！");  
        }   
        if(!inputObj.IsSet("return_code"))  
        {  
            throw new WxPayException("返回状态码，缺少必填参数return_code！");  
        }   
        if(!inputObj.IsSet("result_code"))  
        {  
            throw new WxPayException("业务结果，缺少必填参数result_code！");  
        }   
        if(!inputObj.IsSet("user_ip"))  
        {  
            throw new WxPayException("访问接口IP，缺少必填参数user_ip！");  
        }   
        if(!inputObj.IsSet("execute_time_"))  
        {  
            throw new WxPayException("接口耗时，缺少必填参数execute_time_！");  
        }  
  
        inputObj.SetValue("appid",Configure.getAppid());//公众账号ID  
        inputObj.SetValue("mch_id",Configure.getMchid());//商户号  
        inputObj.SetValue("user_ip",Configure.getIP());//终端ip  
        inputObj.SetValue("time",DateUtil.formatDateyyyyMMddHHmmss(new Date()));//商户上报时间     
        inputObj.SetValue("nonce_str",GenerateNonceStr());//随机字符串  
        inputObj.SetValue("sign",inputObj.MakeSign());//签名  
        String xml = inputObj.ToXml();  
  
        HttpsRequest https=new HttpsRequest();  
        https.setSocketTimeout(timeOut);  
        String response = https.sendPost(url, xml);//调用HTTP通信接口以提交数据到API  
        WxPayData result = new WxPayData();  
        result.FromXml(response);  
  
        return result;  
    }  
  
    /** 
    * 根据当前系统时间加随机序列来生成订单号 
     * @return 订单号 
    */  
    public static String GenerateOutTradeNo()  
    {  
        Random ran = new Random();  
        return String.format("{0}{1}{2}", Configure.getMchid(),DateUtil.formatDateyyyyMMddHHmmss(new Date()), ran.nextInt(999));  
    }  
  
    /** 
    * 生成时间戳，标准北京时间，时区为东八区，自1970年1月1日 0点0分0秒以来的秒数 
     * @return 时间戳 
    */  
    public static String GenerateTimeStamp()  
    {  
        //Java中的getTime方法默认的是从1970 1 1 算起所以可以直接调用  
        //date.getTime获得的是毫秒数，不是秒，所以最后的结果day应当再除以1000才对。  
        return String.valueOf(Calendar.getInstance().getTimeInMillis()/1000);  
    }  
  
    /** 
    * 生成随机串，随机串包含字母或数字 
    * @return 随机串 
    */  
    public static String GenerateNonceStr()  
    {  
        return Util.getUuid().toString().replace("-", "");  
    }  
}  