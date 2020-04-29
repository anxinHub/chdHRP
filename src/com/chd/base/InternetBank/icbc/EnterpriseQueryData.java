package com.chd.base.InternetBank.icbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.chd.base.InternetBank.Base64Util;
import com.chd.base.util.DateUtil;

public class EnterpriseQueryData {

	private static Logger logger = Logger.getLogger(EnterpriseQueryData.class);

	static String sendTime = DateUtil.dateToString(new Date(), "yyyyMMddHHmmssSSS");

	private EnterpriseQueryData() {
	}

	private static volatile EnterpriseQueryData instance = null;

	public static EnterpriseQueryData getInstance() {
		if (instance == null) { 
			synchronized (EnterpriseQueryData.class) {
				if (instance == null) {
					instance = new EnterpriseQueryData();
				}
			}
		}
		return instance;
	}

	public String queryData(String xml, Map<String, Object> map) {

		String reXml = null;

		try {

			String urlStr = map.get("IP") + ":" + map.get("HttpPort") + "/servlet/ICBCCMPAPIReqServlet?userID=" + map.get("ID") + "&PackageID="
					+ map.get("PackageID") + "&SendTime=" + sendTime;

			HttpClient myclient = new HttpClient(); // 构建http客户端
			
			myclient.getParams().setContentCharset("GBK");  
			
			myclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");  

			PostMethod mypost = new PostMethod(urlStr); // 加密端口
			
			mypost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK"); 

			mypost.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");

			mypost.addParameter("Version", map.get("Version").toString());

			mypost.addParameter("GroupCIS", map.get("CIS").toString());

			mypost.addParameter("BankCode", map.get("BankCode").toString());

			mypost.addParameter("TransCode", map.get("TransCode").toString());

			mypost.addParameter("PackageID", map.get("PackageID").toString());

			mypost.addParameter("ID", map.get("ID").toString());

			mypost.addParameter("Cert", "");

			mypost.addParameter("reqData", xml);// System.out.println("repSignContent="+repSignContent);

			/**
			 * repSignContent是经过发签名端口加密后的数据，如果是查询交易repSignContent为包明文,
			 * 不需要发NC449端口签名。 System.out.println("发送到加密端的数据：" + repSignContent);
			 */

			myclient.executeMethod(mypost); // 获得http返回码
											// System.out.println("开始发送到加密端...");System.out.println("已返回数据...");

			String responseStr= mypost.getResponseBodyAsString();
					
			byte[] decodeResult = Base64Util.getbyteFromBASE64(responseStr.substring(8));

			reXml = new String(decodeResult, "GBK");

			logger.debug("查询指令返回数据 =" + reXml);

			mypost.releaseConnection(); // 释放http连接

		} catch (Exception e) {

			reXml = "系统提示：工行NC服务端工具未启动,请重启NC服务,重新支付！";
		}

		return reXml;

	}

	public String buildData(Map<String, Object> map) {

		StringBuffer content = new StringBuffer();

		StringBuffer zipContent = new StringBuffer();

		content.append("<?xml version=\"1.0\" encoding = \"GBK\"?><CMS><eb>");

		content.append("<pub>");
		content.append("<TransCode>" + (map.get("TransCode") != null ? map.get("TransCode") : "") + "</TransCode>");
		content.append("<CIS>" + (map.get("CIS") != null ? map.get("CIS") : "") + "</CIS>");
		content.append("<BankCode>" + (map.get("BankCode") != null ? map.get("BankCode") : "") + "</BankCode>");
		content.append("<ID>" + (map.get("ID") != null ? map.get("ID") : "") + "</ID>");
		content.append("<TranDate>" + (map.get("trandate") != null ? map.get("trandate") : "") + "</TranDate>");
		content.append("<TranTime>" + (map.get("trantime") != null ? map.get("trantime") : "") + "</TranTime>");
		content.append("<fSeqno>" + (map.get("PackageID") != null ? map.get("PackageID") : "") + "</fSeqno>");
		content.append("</pub>");

		content.append("<in>");
		content.append("<QryfSeqno>" + (map.get("fseqno") != null ? map.get("fseqno") : "") + "</QryfSeqno>");
		content.append("<QrySerialNo></QrySerialNo>");
		content.append("<ReqReserved1></ReqReserved1>");
		content.append("<ReqReserved2></ReqReserved2>");
		// content.append("<rd>");
		// content.append("<iSeqno></iSeqno>");
		// content.append("<QryiSeqno></QryiSeqno>");
		// content.append("<QryOrderNo></QryOrderNo>");
		// content.append("<ReqReserved3></ReqReserved3>");
		// content.append("<ReqReserved4></ReqReserved4>");
		// content.append("</rd>");
		content.append("</in>");

		content.append("</eb></CMS>");

		logger.debug("本地查询数据 =" + content.toString());

		return content.toString();

	}

	public Map<String, Object> getICBC(Map<String, Object> qMap) {

		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, String> configMap = ConfigData.getConfigData();// 获取配置数据

		qMap.put("IP", configMap.get("IP"));

		qMap.put("HttpPort", configMap.get("HttpPort"));

		qMap.put("SignPort", configMap.get("SignPort"));

		qMap.put("CIS", configMap.get("CIS"));

		qMap.put("BankCode", configMap.get("BankCode"));

		qMap.put("ID", configMap.get("ID"));

		String content = buildData(qMap);

		String reXml = queryData(content, qMap);

		map = readerXml(reXml);

		return map;
	}

	public Map<String, Object> readerXml(String xml) {

		Map<String, Object> map = new HashMap<String, Object>();

		SAXReader saxReader = new SAXReader();

		Document document = null;

		try {

			// 先判断是否包换ZIP节点
			if (xml.indexOf("<zip>") != -1) {

				int bx = xml.indexOf("<zip>") + 5;
				int ex = xml.indexOf("</zip>");

				String zipRd = xml.substring(bx, ex);

				byte[] zipRdByte = Base64Util.UnZipandUnbase64(zipRd);

				String rdXml = new String(zipRdByte, "GBK");

				xml = xml.replaceAll("<zip>([\\s\\S]*?)</zip>", rdXml);
			}

			// 解析XML 根据获取数据层级来解析
			document = saxReader.read(new StringReader(xml));

			Element root = document.getRootElement();

			Node retCodeNode = root.selectSingleNode("/CMS/eb/pub/RetCode");

			Node retMsgNode = root.selectSingleNode("/CMS/eb/pub/RetMsg");

			map.put("retcode", retCodeNode.getText().trim());

			map.put("retmsg", retMsgNode.getText().trim());

			if ("".equals(retMsgNode.getText().trim())) {// 如果返回信息为空 代表数据提交成功// 银行处理成功 接着寻找节点

				Node qrySerialNoNode = root.selectSingleNode("/CMS/eb/out/QrySerialNo");

				if (!"".equals(qrySerialNoNode)) {// 有返回银行流水 代表银行接收整包数据

					map.put("serialno", qrySerialNoNode.getText().trim());

					List<Element> childList = root.selectNodes("/CMS/eb/out/rd");

					List<Map<String, Object>> rdList = new ArrayList<Map<String, Object>>();

					for (int i = 0; i < childList.size(); i++) {

						Element e = childList.get(i);

						Map<String, Object> rdMap = new HashMap<String, Object>();

						Node iSeqnoNode = e.selectSingleNode("iSeqno");
						
						Node qryiSeqNoNode = e.selectSingleNode("QryiSeqno");

						Node qryOrderNoNode = e.selectSingleNode("QryOrderNo");

						Node iRetCodeNode = e.selectSingleNode("iRetCode");

						Node iRetMsgNode = e.selectSingleNode("iRetMsg");

						Node resultNode = e.selectSingleNode("Result");

						Node refNode = e.selectSingleNode("Ref");

						Node orefNode = e.selectSingleNode("Oref");

						Node eRPSqnNode = e.selectSingleNode("ERPSqn");

						Node busCodeNode = e.selectSingleNode("BusCode");

						rdMap.put("iseqno", qryiSeqNoNode.getText().trim());

						rdMap.put("orderno", qryOrderNoNode.getText().trim());

						rdMap.put("iretcode", iRetCodeNode.getText().trim());

						rdMap.put("iretmsg", iRetMsgNode.getText().trim());

						rdMap.put("result", resultNode.getText().trim());

						rdMap.put("ref", refNode.getText().trim());

						rdMap.put("oref", orefNode.getText().trim());

						rdMap.put("erpsqn", eRPSqnNode.getText().trim());

						rdMap.put("buscode", busCodeNode.getText().trim());

						if ("6".equals(resultNode.getText().trim())) {

							Node instrRetCodeNode = e.selectSingleNode("instrRetCode");

							Node instrRetMsgNode = e.selectSingleNode("instrRetMsg");

							rdMap.put("iretcode", instrRetCodeNode.getText().trim());

							rdMap.put("iretmsg", instrRetMsgNode.getText().trim());

						}

						rdList.add(rdMap);

					}

					map.put("rdList", rdList);

				} else {

					return map;

				}

			} else {

				return map;

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return map;
	}

}
