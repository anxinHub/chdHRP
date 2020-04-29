package com.chd.base.tool;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.nutz.lang.Lang;

/**
 * 
 * @Title:
 * @Description: 基于 httpclient 4.5.5版本的 http工具类
 * @Copyright: Copyright (c) 2018年5月26日 下午1:37:48
 * @Company: 杭州亦童科技有限公司
 * @网站：www.e-tonggroup.com
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public final class HttpClientTool {
 
	private static final CloseableHttpClient httpClient;
	public static final String CHARSET = "UTF-8";

	// 采用静态代码块，初始化超时时间配置，再根据配置生成默认httpClient对象
	static {
		Properties p = new Properties();
		ServerUrl su=new ServerUrl();
		try {
			p = su.getConf();

			if (Lang.isEmpty(p))
				p = new Properties();

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		int connectTimeout = Integer.parseInt(
				(Lang.isEmpty(p.getProperty("http.connectTimeout")) ? "5000" : p.getProperty("http.connectTimeout")));
		int socketTimeout = Integer
				.parseInt((Lang.isEmpty(p.get("http.socketTimeout")) ? "5000" : p.getProperty("http.socketTimeout")));

		RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout)
				.build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}
	/**
	 * HTTP Get 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @return 页面内容
	 */
	public static String doGet(String url, Map<String, String> params) {
		return doGet(url, params, CHARSET);
	}
	/**
	 * HTTPS Get 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @return 页面内容
	 */
	public static String doGetSSL(String url, Map<String, String> params) {
		return doGetSSL(url, params, CHARSET);
	}
	/**
	 * HTTP Post 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数  JSON格式
	 * @return 页面内容
	 * @throws IOException
	 */
	public static String doPost(String url, String params) throws IOException {
		return doPost(url, params, CHARSET);
	}
	/**
	 * HTTP Post 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @return 页面内容
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params) throws IOException {
		return doPost(url, params, CHARSET);
	}

	/**
	 * HTTP Get 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 */
	public static String doGet(String url, Map<String, String> params, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				// 将请求参数和url进行拼接
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * HTTP Post 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数 json格式
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 * @throws IOException
	 */
	public static String doPost(String url, String params, String charset) throws IOException {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		/*
		 List<NameValuePair> pairs = null;
		 if (params != null && !params.isEmpty()) {
			pairs = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String value = entry.getValue();
				if (value != null) {
					pairs.add(new BasicNameValuePair(entry.getKey(), value));
				}
			}
		}*/
		HttpPost httpPost = new HttpPost(url);
		if (null != params && params.length()>0){
			StringEntity entity = new StringEntity(params, charset);
            entity.setContentEncoding(charset);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
		}
		
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();

			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				response.close();
		}
		return null;
	}
	/**
	 * HTTP Post 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, String> params, String charset) throws IOException {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		List<NameValuePair> pairs = null;
		if (params != null && !params.isEmpty()) {
			pairs = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String value = entry.getValue();
				if (value != null) {
					pairs.add(new BasicNameValuePair(entry.getKey(), value));
				}
			}
		}
		HttpPost httpPost = new HttpPost(url);
		if (pairs != null && pairs.size() > 0) {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
		}
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();

			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				response.close();
		}
		return null;
	}

	/**
	 * HTTPS Get 获取内容
	 * 
	 * @param url
	 *            请求的url地址 ?之前的地址
	 * @param params
	 *            请求的参数
	 * @param charset
	 *            编码格式
	 * @return 页面内容
	 */
	public static String doGetSSL(String url, Map<String, String> params, String charset) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
			}
			HttpGet httpGet = new HttpGet(url);

			// https 注意这里获取https内容，使用了忽略证书的方式，当然还有其他的方式来获取https内容
			CloseableHttpClient httpsClient = HttpClientTool.createSSLClientDefault();
			CloseableHttpResponse response = httpsClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null) {
				result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将文件提交至文件服务器
	 * 
	 * @param file
	 *            文件对象
	 * @return FileStatus 上传结果
	 * @throws IOException
	 */
	public static String upload(String url, File file) throws IOException {
		CloseableHttpResponse response = null;
		String result = null;
		try {

			HttpPost httpPost = new HttpPost(url);
			MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
			mEntityBuilder.addBinaryBody("file", file);
			httpPost.setEntity(mEntityBuilder.build());
			response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity resEntity = response.getEntity();
				result = EntityUtils.toString(resEntity);
				// 消耗掉response
				EntityUtils.consume(resEntity);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null)
				response.close();
		}
		return result;
	}

	/**
	 * 执行文件下载
	 *
	 * @param httpClient
	 *            HttpClient客户端实例，传入null会自动创建一个
	 * @param remoteFileUrl
	 *            远程下载文件地址
	 * @param localFilePath
	 *            本地存储文件地址
	 * @param charset
	 *            请求编码，默认UTF-8
	 * @param closeHttpClient
	 *            执行请求结束后是否关闭HttpClient客户端实例
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static boolean download(String remoteFileUrl, String localFilePath, String charset)
			throws ClientProtocolException, IOException {
		CloseableHttpResponse response = null;
		InputStream in = null;
		FileOutputStream fout = null;
		try {
			HttpGet httpget = new HttpGet(remoteFileUrl);
			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return false;
			}
			in = entity.getContent();
			File file = new File(localFilePath);
			fout = new FileOutputStream(file);
			int l;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fout.write(tmp, 0, l);
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真
			}
			// 将文件输出到本地
			fout.flush();
			EntityUtils.consume(entity);
			return true;
		} finally {
			// 关闭低层流。
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception e) {
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * 这里创建了忽略整数验证的CloseableHttpClient对象
	 * 
	 * @return
	 */
	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
}