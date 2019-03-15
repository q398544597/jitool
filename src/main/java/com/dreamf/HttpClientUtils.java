package com.dreamf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * 功能描述: httpClient请求工具类
 *
 * @param:
 * @return:
 * @auther: miaoguoxin
 * @date: 2019/3/15 0015 10:59
 */
public class HttpClientUtils {


	//请务必设置超时时间，否则可能响应时间过长,导致请求阻塞
	private final static RequestConfig REQUEST_CONFIG=RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();

	private final static String CHARSET_NAME="UTF-8";

	/**
	 *
	 * 功能描述:httpClient get请求带头部
	 *
	 * @param:
	 * @return:
	 * @auther: miaoguoxin
	 * @date: 2018/10/24 0024 10:44
	 */
	public static <T> T get(String url,Map<String,String> headers,Class<T> clazz){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url);
		httpGet.setConfig(REQUEST_CONFIG);
		setHeaders(httpGet,headers);
		return requestExecute(httpClient,httpGet,clazz);
	}

	/**
	 *
	 * 功能描述:httpClient get请求
	 *
	 * @param:
	 * @return:
	 * @auther: miaoguoxin
	 * @date: 2018/10/24 0024 10:40
	 */
	public static <T> T get(String url,Class<T> clazz){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(url);
		httpGet.setConfig(REQUEST_CONFIG);
		return requestExecute(httpClient,httpGet,clazz);
	}

	/**
	 * post请求带头部
	 * @param url
	 * @param params
	 * @param clazz
	 * @param headers
	 * @param <T>
	 * @return
	 */
	public static <T> T post(String url,Map<String,String> params,Map<String,String> headers,Class<T> clazz){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost=new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);
		setHeaders(httpPost,headers);
		setParamsEntity(params,httpPost);
		return requestExecute(httpClient,httpPost,clazz);
	}
	/**
	 * 发送Post请求
	 * @param url
	 * @param params
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T post(String url, Map<String,String> params,Class<T> clazz){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost  httpPost=new HttpPost(url);
		httpPost.setConfig(REQUEST_CONFIG);
		setParamsEntity(params,httpPost);
		return requestExecute(httpClient,httpPost,clazz);
	}

	//设置头部
	private static void setHeaders(HttpUriRequest request,Map<String,String> headers){
		for (String key : headers.keySet()) {
			request.setHeader(key,headers.get(key));
		}
	}


	//设置post请求参数
	private static void setParamsEntity( Map<String,String> params,HttpPost httpPost){
		//设置参数
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			list.add(new BasicNameValuePair(key,params.get(key)));
		}
		if (!list.isEmpty()){
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, CHARSET_NAME);
				httpPost.setEntity(entity);
			}catch (Exception e){
				e.printStackTrace();
				httpPost.setEntity(null);
			}
		}
	}

	//如果没有传Class，那么直接返回字符串
	private static <T> T requestExecute(CloseableHttpClient httpClient, HttpUriRequest request , Class<T> clazz){
		T resultEntity = null;
		try{
			HttpResponse response=httpClient.execute(request);
			if (response!=null){
				HttpEntity resEntity=response.getEntity();
				if (resEntity!=null){
					String result= EntityUtils.toString(resEntity,CHARSET_NAME);
					resultEntity = JSON.parseObject(result,clazz);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			return null;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return resultEntity;
	}




	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Map<String,String> params=new HashMap<String,String>();
		JSONObject jsonObject = post("http://localhost:8081/msg/test2", params, JSONObject.class);
		System.out.println(jsonObject.toJSONString());
	}

}
