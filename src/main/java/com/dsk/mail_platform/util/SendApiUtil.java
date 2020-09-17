package com.dsk.mail_platform.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;





public class SendApiUtil {

    // 连接超时时间，默认30秒
    private static int socketTimeout = 30000;
    // 传输超时时间，默认30秒
    private static int connectTimeout = 30000;

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY ="40dcc1b17933d8a0d16e8ba7851a27c1";

    public static String doPost(String url, String json)
            throws ClientProtocolException, IOException {
        String result = "";
        // POST请求
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        request.setConfig(requestConfig);
        // 设置参数
        request.addHeader("Content-type", "application/json; charset=utf-8");
        request.setHeader("Accept", "application/json");
        request.setEntity(new StringEntity(json, "UTF-8"));

        // 生成客户端执行request 返回response

        CloseableHttpResponse response = client.execute(request);
        // 如果response返回状态码是200 则表示正确返回
        if (response.getStatusLine().getStatusCode() == 200) {
            result = entity2String(response);
        }

        return result;
    }

    // 读取response返回内容转成String
    public static String entity2String(HttpResponse response)
            throws UnsupportedOperationException, IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response
                .getEntity().getContent(), "UTF-8"));
        String line = "";
        StringBuffer buffer = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }




    //1.事件列表
    public static String getRequest1(){
        Calendar rightNow    =    Calendar.getInstance();
        Integer month = rightNow.get(Calendar.MONTH)+1; //第一个月从0开始，所以得到月份＋1
        Integer day = rightNow.get(rightNow.DAY_OF_MONTH);

        String result =null;
        String url ="http://api.juheapi.com/japi/toh";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("v","1.0");//版本，当前：1.0
        params.put("month",month);//月份，如：10
        params.put("day",day);//日，如：1

        try {
            result =net(url, params, "GET");
            JSONObject object = (JSONObject) JSONObject.parse(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //2.根据ID查询事件详情
    public static void getRequest2(){
        String result =null;
        String url ="http://api.juheapi.com/japi/tohdet";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("v","");//版本，当前：1.0
        params.put("id","");//事件ID

        try {
            result =net(url, params, "GET");
            JSONObject object = (JSONObject) JSONObject.parse(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
