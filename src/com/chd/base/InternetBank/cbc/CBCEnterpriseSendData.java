package com.chd.base.InternetBank.cbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.Request;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;

public class CBCEnterpriseSendData {
	
	private static Logger logger = Logger.getLogger(CBCEnterpriseSendData.class);
	
	private CBCEnterpriseSendData() {
	}

	private static volatile CBCEnterpriseSendData instance = null;

	public static CBCEnterpriseSendData getInstance() {
		if (instance == null) {
			synchronized (CBCEnterpriseSendData.class) {
				if (instance == null) {
					instance = new CBCEnterpriseSendData();
				}
			}
		}
		return instance;
	}
	
	public Map<String,Object> getCCB(Map payMap, List<Map<String, Object>> rdlist){
		try {
			//组装发送报文
			String reqXml = buildData(payMap,rdlist);
			//发送报文获取返回数据
			String responseXml = sendXMLPost("",reqXml);
			//解析返回报文数据
			Map<String,Object> map=readerXml(responseXml);
			
			return map;
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	
	public String buildData(Map payMap, List<Map<String, Object>> rdlist) {

		try {
			StringBuffer content = new StringBuffer();

			content.append("<?xml version=\"1.0\" encoding=\"GB18030\" standalone=\"yes\" ?>");

			content.append("<TX>");
			content.append("<REQUEST_SN>" +(payMap.get("request_sn") != null ? payMap.get("request_sn") : "")+ "</REQUEST_SN>");
			content.append("<CUST_ID>" + (payMap.get("cust_id") != null ? payMap.get("cust_id") : "") + "</CUST_ID>");
			content.append("<USER_ID>" + (payMap.get("user_id") != null ? payMap.get("user_id") : "") + "</USER_ID>");
			content.append("<PASSWORD>" + (payMap.get("password") != null ? payMap.get("password") : "") + "</PASSWORD>");
			content.append("<TX_CODE>" +(payMap.get("tx_code") != null ? payMap.get("tx_code") : "")+ "</TX_CODE>");
			content.append("<LANGUAGE>CN</LANGUAGE>");
			content.append("<TX_INFO>");

			content.append("<AMOUNT>" + (payMap.get("amount") != null ? payMap.get("amount") : "") + "</AMOUNT>");
			content.append("<COUNT>" + (payMap.get("count") != null ? payMap.get("count") : "") + "</COUNT>");
			content.append("<CHK_RECVNAME>" + (payMap.get("CHK_RECVNAME") != null ? payMap.get("CHK_RECVNAME") : "") + "</CHK_RECVNAME>");
			content.append("<CHNL_TYPE>" + (payMap.get("CHNL_TYPE") != null ? payMap.get("CHNL_TYPE") : "") + "</CHNL_TYPE>");
			for (Map<String, Object> rdMap : rdlist) {
				content.append("<FILE_CTX>");
				content.append(rdMap.get("cont")+"|"+payMap.get("payaccnamecn")+"|"+payMap.get("payaccno")+"|"+payMap.get("cbc_no1")+"|"+rdMap.get("account_name")+"|"
					+rdMap.get("account_code")+"|"+rdMap.get("user_cbc_no1")+"|"+rdMap.get("cbc_open_name")+"|"+rdMap.get("CBC_NO")+"|"+""+"|"
					+1+"|"+rdMap.get("payamt")+"|"+"01"+"|"+payMap.get("use_name"));
				content.append("</FILE_CTX>");
			}
			content.append("</TX_INFO>");
			content.append("<SIGN_INFO>" + (payMap.get("SIGN_INFO") != null ? payMap.get("SIGN_INFO") : "") + "</SIGN_INFO>");
			content.append("<SIGNCERT>" + (payMap.get("SIGNCERT") != null ? payMap.get("SIGNCERT") : "") + "</SIGNCERT>");
			content.append("</TX>");
			String reContent = content.toString();
			logger.debug("本地构建数据 =" + reContent);
			return reContent;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}

	}
	
	public String sendXMLPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //conn.addRequestProperty("referer", referer);//"http://life.ccb.com/"
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;            
            while ((line = in.readLine()) != null) {          	
                result += line+"\n";    
            }
        } catch (Exception e) {
            logger.debug("发送 POST 请求出现异常！"+e);
            throw new SysException(e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
       
        return result;
    }   
	
	public static Map<String, Object> readerXml(String xml) {
		List<Map<String, Object>> responseList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
	
		SAXReader saxReader = new SAXReader();
	
		Document document = null;
	
		try {
			
			document = saxReader.read(new StringReader(xml));
	
			Element root = document.getRootElement();
			
			Node REQUEST_SN = root.selectSingleNode("/TX/REQUEST_SN");
			
			Node CUST_ID = root.selectSingleNode("/TX/CUST_ID");
			
			Node RETURN_CODE = root.selectSingleNode("/TX/RETURN_CODE");
			
			Node RETURN_MSG = root.selectSingleNode("/TX/RETURN_MSG");
			
			Node LANGUAGE = root.selectSingleNode("/TX/LANGUAGE");
	
			Node SUCCESS_NUM = root.selectSingleNode("/TX/TX_INFO/SUCCESS_NUM");
			
			Node SUCCESS_AMOUNT = root.selectSingleNode("/TX/TX_INFO/SUCCESS_AMOUNT");
			
			Node ERROR_NUM = root.selectSingleNode("/TX/TX_INFO/ERROR_NUM");
			map.put("request_sn", REQUEST_SN);
			map.put("cust_id", CUST_ID);
			map.put("return_code", RETURN_CODE);
			map.put("return_msg", RETURN_MSG);
			map.put("language", LANGUAGE);
			map.put("success_num", SUCCESS_NUM);
			map.put("success_amount", SUCCESS_AMOUNT);
			map.put("error_num", ERROR_NUM);
	
			List<Node> ERROR_LIST =root.selectNodes("/TX/TX_INFO/ERROR_LIST");
			if(ERROR_LIST!=null && ERROR_LIST.size()>0){
				for (Node node : ERROR_LIST) {
					Map<String, Object> mapVo = new HashMap<String,Object>();
					Iterator itt = ((Element) node).elementIterator();
					while (itt.hasNext()) {
						Element Child = (Element) itt.next();
						System.out.println("节点名：" + Child.getName() + "--节点值：" + Child.getStringValue());
						mapVo.put(Child.getName(), Child.getStringValue());
					}
					mapVo.put("request_sn", REQUEST_SN);
					mapVo.put("flag", "ERROR");
					responseList.add(mapVo);
				}
			}
			List<Node> SUCCESS_LIST = root.selectNodes("/TX/TX_INFO/SUCCESS_LIST");
			if(SUCCESS_LIST!=null && SUCCESS_LIST.size()>0){
				for (Node node : SUCCESS_LIST) {
					Map<String, Object> mapVo = new HashMap<String,Object>();
					Iterator itt = ((Element) node).elementIterator();
					while (itt.hasNext()) {
						Element Child = (Element) itt.next();
						System.out.println("节点名：" + Child.getName() + "--节点值：" + Child.getStringValue());
						mapVo.put(Child.getName(), Child.getStringValue());
					}
					mapVo.put("request_sn", REQUEST_SN);
					mapVo.put("flag", "SUCCESS");
					responseList.add(mapVo);
				}
			}
			
			map.put("responseList", responseList);
			
			} catch (Exception e) {
				
			throw new SysException(e);
		}
	
		return map;
	}
}
