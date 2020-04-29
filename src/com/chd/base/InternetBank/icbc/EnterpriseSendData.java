package com.chd.base.InternetBank.icbc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
import com.chd.hrp.acc.serviceImpl.InternetBank.AccBankNetWageServiceImpl;

public class EnterpriseSendData {

	private static Logger logger = Logger.getLogger(EnterpriseSendData.class);

	static String sendTime = DateUtil.dateToString(new Date(), "yyyyMMddHHmmssSSS");

	private EnterpriseSendData() {
	}

	private static volatile EnterpriseSendData instance = null;

	public static EnterpriseSendData getInstance() {
		if (instance == null) {
			synchronized (EnterpriseSendData.class) {
				if (instance == null) {
					instance = new EnterpriseSendData();
				}
			}
		}
		return instance;
	}

	public String buildData(Map payMap, List<Map<String, Object>> rdlist) {

		StringBuffer content = new StringBuffer();

		StringBuffer zipContent = new StringBuffer();

		content.append("<?xml version=\"1.0\" encoding = \"GBK\"?><CMS><eb>");

		content.append("<pub>");
		content.append("<TransCode>" + (payMap.get("TransCode") != null ? payMap.get("TransCode") : "") + "</TransCode>");
		content.append("<CIS>" + (payMap.get("CIS") != null ? payMap.get("CIS") : "") + "</CIS>");
		content.append("<BankCode>" + (payMap.get("BankCode") != null ? payMap.get("BankCode") : "") + "</BankCode>");
		content.append("<ID>" + (payMap.get("ID") != null ? payMap.get("ID") : "") + "</ID>");
		content.append("<TranDate>" + (payMap.get("trandate") != null ? DateUtil.dateToString((Date) payMap.get("trandate"), "yyyyMMdd") : "") + "</TranDate>");
		content.append("<TranTime>" + (payMap.get("trantime") != null ? DateUtil.dateToString((Date) payMap.get("trantime"), "HHmmssSSS") : "") + "</TranTime>");
		content.append("<fSeqno>" + (payMap.get("fseqno") != null ? payMap.get("fseqno") : "") + "</fSeqno>");
		content.append("</pub>");

		content.append("<in>");

		content.append("<OnlBatF>" + (payMap.get("onlbatf") != null ? payMap.get("onlbatf") : "") + "</OnlBatF>");
		content.append("<SettleMode>" + (payMap.get("settlemode") != null ? payMap.get("settlemode") : "") + "</SettleMode>");
		content.append("<TotalNum>" + (payMap.get("totalnum") != null ? payMap.get("totalnum") : "") + "</TotalNum>");
		content.append("<TotalAmt>" + (payMap.get("totalamt") != null ? payMap.get("totalamt") : "") + "</TotalAmt>");
		content.append("<SignTime>" + (DateUtil.dateToString(new Date(), "yyyyMMddHHmmssSSS")) + "</SignTime>");
		content.append("<ReqReserved1>" + (payMap.get("reqreserved1") != null ? payMap.get("reqreserved1") : "") + "</ReqReserved1>");
		content.append("<ReqReserved2>" + (payMap.get("reqreserved2") != null ? payMap.get("reqreserved2") : "") + "</ReqReserved2>");

		for (Map<String, Object> rdMap : rdlist) {

			zipContent.append("<rd>");
			zipContent.append("<iSeqno>" + (rdMap.get("iseqno") != null ? rdMap.get("iseqno") : "") + "</iSeqno>");
			zipContent.append("<ReimburseNo>" + (rdMap.get("reimburseno") != null ? rdMap.get("reimburseno") : "") + "</ReimburseNo>");
			zipContent.append("<ReimburseNum>" + (rdMap.get("reimbursenum") != null ? rdMap.get("reimbursenum") : "") + "</ReimburseNum>");
			zipContent.append("<StartDate>" + (rdMap.get("startdate") != null ? rdMap.get("startdate") : "") + "</StartDate>");
			zipContent.append("<StartTime>" + (rdMap.get("starttime") != null ? rdMap.get("starttime") : "") + "</StartTime>");
			zipContent.append("<PayType>" + (rdMap.get("paytype") != null ? rdMap.get("paytype") : "") + "</PayType>");
			zipContent.append("<PayAccNo>" + (rdMap.get("payaccno") != null ? rdMap.get("payaccno") : "") + "</PayAccNo>");
			zipContent.append("<PayAccNameCN>" + (rdMap.get("payaccnamecn") != null ? rdMap.get("payaccnamecn") : "") + "</PayAccNameCN>");
			zipContent.append("<PayAccNameEN>" + (rdMap.get("payaccnameen") != null ? rdMap.get("payaccnameen") : "") + "</PayAccNameEN>");
			zipContent.append("<RecAccNo>" + (rdMap.get("recaccno") != null ? rdMap.get("recaccno") : "") + "</RecAccNo>");
			zipContent.append("<RecAccNameCN>" + (rdMap.get("recaccnamecn") != null ? rdMap.get("recaccnamecn") : "") + "</RecAccNameCN>");
			zipContent.append("<RecAccNameEN>" + (rdMap.get("recaccnameen") != null ? rdMap.get("recaccnameen") : "") + "</RecAccNameEN>");
			zipContent.append("<SysIOFlg>" + (rdMap.get("sysioflg") != null ? rdMap.get("sysioflg") : "") + "</SysIOFlg>");
			zipContent.append("<IsSameCity>" + (rdMap.get("issamecity") != null ? rdMap.get("issamecity") : "") + "</IsSameCity>");
			if(rdMap.get("prop") != null && !"".equals(rdMap.get("prop"))){zipContent.append("<Prop>" + rdMap.get("prop")+ "</Prop>");}
			zipContent.append("<RecICBCCode>" + (rdMap.get("reicbccode") != null ? rdMap.get("reicbccode") : "") + "</RecICBCCode>");
			zipContent.append("<RecCityName>" + (rdMap.get("reccityname") != null ? rdMap.get("reccityname") : "") + "</RecCityName>");
			zipContent.append("<RecBankNo>" + (rdMap.get("recbankno") != null ? rdMap.get("recbankno") : "") + "</RecBankNo>");
			zipContent.append("<RecBankName>" + (rdMap.get("recbankname") != null ? rdMap.get("recbankname") : "") + "</RecBankName>");
			zipContent.append("<CurrType>" + (rdMap.get("currtype") != null ? rdMap.get("currtype") : "") + "</CurrType>");// 币种编码
			zipContent.append("<PayAmt>" + (rdMap.get("payamt").toString()) + "</PayAmt>");
			zipContent.append("<UseCode>" + (rdMap.get("usecode") != null ? rdMap.get("usecode") : "") + "</UseCode>");
			zipContent.append("<UseCN>" + (rdMap.get("usecn") != null ? rdMap.get("usecn") : "") + "</UseCN>");
			zipContent.append("<EnSummary>" + (rdMap.get("ensummary") != null ? rdMap.get("ensummary") : "") + "</EnSummary>");
			zipContent.append("<PostScript>" + (rdMap.get("postscript") != null ? rdMap.get("postscript") : "") + "</PostScript>");
			zipContent.append("<Summary>" + (rdMap.get("summary") != null ? rdMap.get("summary") : "") + "</Summary>");
			zipContent.append("<Ref>" + (rdMap.get("ref") != null ? rdMap.get("ref") : "") + "</Ref>");
			zipContent.append("<Oref>" + (rdMap.get("oref") != null ? rdMap.get("oref") : "") + "</Oref>");
			zipContent.append("<ERPSqn>" + (rdMap.get("erpsqn") != null ? rdMap.get("erpsqn") : "") + "</ERPSqn>");
			zipContent.append("<BusCode>" + (rdMap.get("buscode") != null ? rdMap.get("buscode") : "") + "</BusCode>");
			zipContent.append("<ERPcheckno>" + (rdMap.get("erpcheckno") != null ? rdMap.get("erpcheckno") : "") + "</ERPcheckno>");
			zipContent.append("<CrvouhType>" + (rdMap.get("crvouhtype") != null ? rdMap.get("crvouhtype") : "") + "</CrvouhType>");
			zipContent.append("<CrvouhName>" + (rdMap.get("crvouhname") != null ? rdMap.get("crvouhname") : "") + "</CrvouhName>");
			zipContent.append("<CrvouhNo>" + (rdMap.get("crvouhno") != null ? rdMap.get("crvouhno") : "") + "</CrvouhNo>");
			zipContent.append("<ReqReserved3>" + (rdMap.get("reqreserved3") != null ? rdMap.get("reqreserved3") : "") + "</ReqReserved3>");
			zipContent.append("<ReqReserved4>" + (rdMap.get("reqreserved4") != null ? rdMap.get("reqreserved4") : "") + "</ReqReserved4>");
			zipContent.append("</rd>");

		}

		if ("&zipFlag=1".equals(payMap.get("zipFlag").toString())) {

			content.append("<zip>" + Base64Util.ZipandBase64(zipContent.toString().getBytes()) + "</zip>");

		} else {

			content.append(zipContent);

		}

		content.append("</in>");

		content.append("</eb></CMS>");

		String reContent = content.toString();

		logger.debug("本地构建数据 =" + reContent);

		return reContent;

	}

	public String getSendSign(Map<String, Object> payMap, String xmlContent) {

		String reSignXml = "0";

		try {

			logger.debug("开始工行签名数据");

			java.net.URL aURL = new java.net.URL(payMap.get("IP") + ":" + payMap.get("SignPort"));

			java.net.HttpURLConnection urlConn = (java.net.HttpURLConnection) aURL.openConnection();

			urlConn.setRequestMethod("POST");
			urlConn.setDoInput(true);

			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);

			urlConn.setRequestProperty("Content-Length", String.valueOf(xmlContent.trim().getBytes().length));// 设置工行的标准提交Content

			urlConn.setRequestProperty("Content-Type", "INFOSEC_SIGN/1.0");// 设置工行的标准提交Content

			BufferedOutputStream out = new BufferedOutputStream(urlConn.getOutputStream());

			out.write(xmlContent.trim().getBytes("GBK"));

			out.flush();
			out.close();

			urlConn.connect();

			logger.debug("发送请求");

			int responseCode = urlConn.getResponseCode();

			if (responseCode != 200) {
				logger.debug("请求失败IP地址失败");
				return "0";
			}

			Thread.sleep(2000);

			BufferedInputStream bbb = new BufferedInputStream(urlConn.getInputStream());

			byte[] reContent = new byte[urlConn.getContentLength()];

			bbb.read(reContent);

			reSignXml = new String(reContent, "GBK").replaceAll("[\n'\":%]", "");

			logger.debug("工行签名数据 =" + reSignXml);

			urlConn.disconnect();

		} catch (Exception e) { 
			e.printStackTrace();
			reSignXml = "系统提示：工行NC服务端工具未启动,请重启NC服务,重新支付！";
		}

		return reSignXml;

	}

	public static String sendData(Map<String, Object> payMap, String signXml) {

		int bs = signXml.indexOf("<sign>") + 6;
		int es = signXml.indexOf("</sign>");

		String reXml = null;

		try {

			String repSignContent = signXml.substring(bs, es);

			String urlStr = payMap.get("IP") + ":" + payMap.get("HttpPort") + "/servlet/ICBCCMPAPIReqServlet?userID=" + payMap.get("ID") + "&PackageID="
					+ payMap.get("PackageID") + "&SendTime=" + sendTime + payMap.get("zipFlag");

			HttpClient myclient = new HttpClient(); // 构建http客户端
			
			myclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");  

			PostMethod mypost = new PostMethod(urlStr); // 加密端口

			mypost.addParameter("Version", payMap.get("Version").toString());

			mypost.addParameter("GroupCIS", payMap.get("CIS").toString());

			mypost.addParameter("BankCode", payMap.get("BankCode").toString());

			mypost.addParameter("TransCode", payMap.get("TransCode").toString());

			mypost.addParameter("PackageID", payMap.get("PackageID").toString());

			mypost.addParameter("ID", payMap.get("ID").toString());

			mypost.addParameter("Cert", "");

			mypost.addParameter("reqData", repSignContent);// System.out.println("repSignContent="+repSignContent);

			/**
			 * repSignContent是经过发签名端口加密后的数据，如果是查询交易repSignContent为包明文,
			 * 不需要发NC449端口签名。 System.out.println("发送到加密端的数据：" + repSignContent);
			 */

			myclient.executeMethod(mypost); // 获得http返回码
											// System.out.println("开始发送到加密端...");System.out.println("已返回数据...");
			Thread.sleep(2000);
			
			// String postResult = mypost.getResponseBody();

			byte[] decodeResult = Base64Util.getbyteFromBASE64(mypost.getResponseBodyAsString().substring(8));

			reXml = new String(decodeResult, "GBK");

			logger.debug("工行返回数据 = " + reXml);

			mypost.releaseConnection(); // 释放http连接

		} catch (Exception e) {

			e.printStackTrace();

			reXml = "系统提示：工行NC服务端工具未启动,请重启NC服务,重新支付！";
		}

		return reXml;

	}

	public Map<String, Object> getICBC(List<Map<String, Object>> payList, List<Map<String, Object>> rdList) {

		Map<String, Object> reMap = new HashMap<String, Object>();

		Map<String, String> configMap = ConfigData.getConfigData();// 获取配置数据

		// 组装数据 基本数据
		Map<String, Object> payMap = payList.get(0);

		payMap.put("IP", configMap.get("IP"));

		payMap.put("HttpPort", configMap.get("HttpPort"));

		payMap.put("SignPort", configMap.get("SignPort"));

		payMap.put("CIS", configMap.get("CIS"));

		payMap.put("BankCode", configMap.get("BankCode"));

		payMap.put("ID", configMap.get("ID"));

		String xmlContent = buildData(payMap, rdList);// 组装提交XML格式数据

		String reSignXml = getSendSign(payMap, xmlContent);// 根据组装数据获取签名

		if (reSignXml.indexOf("sign") == -1) {

			reMap.put("RetCode", "error");

			reMap.put("RetMsg", "工行NC服务端工具未启动,请重启NC服务");
			
			return reMap;

		} 
		
		String reMsg = sendData(payMap, reSignXml);// 获取返回的数据

		if (reMsg.indexOf("CMS") != -1) {

			reMap = readerXml(reMsg);

		} else {

			reMap.put("errMsg", reMsg);

			reMap.put("error", "error");

		}

		return reMap;
	}

	public Map<String, Object> readerXml(String xml) {

		Map<String, Object> map = new HashMap<String, Object>();

		SAXReader saxReader = new SAXReader();

		Document document = null;

		try {

			document = saxReader.read(new StringReader(xml));

			Element root = document.getRootElement();

			Node retCodeNode = root.selectSingleNode("/CMS/eb/pub/RetCode");

			Node retMsgNode = root.selectSingleNode("/CMS/eb/pub/RetMsg");

			map.put("RetCode", retCodeNode.getText().trim());

			map.put("RetMsg", retMsgNode.getText().trim());

			map.put("error", "0");
			
			if ("".equals(retMsgNode.getText().trim())) {// 如果返回信息为空 代表数据提交成功// 银行处理成功 接着寻找节点

				Node iRetCodeNode = root.selectSingleNode("/CMS/eb/out/rd/iRetCode");

				Node ordernoNode = root.selectSingleNode("/CMS/eb/out/rd/OrderNo");

				Node iRetMsgNode = root.selectSingleNode("/CMS/eb/out/rd/iRetMsg");

				Node resultNode = root.selectSingleNode("/CMS/eb/out/rd/Result");

				map.put("orderno", ordernoNode.getText().trim());

				map.put("iretcode", iRetCodeNode.getText().trim());

				map.put("iretmsg", iRetMsgNode.getText().trim());

				map.put("result", resultNode.getText().trim());

				if ("6".equals(resultNode.getText().trim())) {

					Node instrRetCodeNode = root.selectSingleNode("/CMS/eb/out/rd/instrRetCode");

					Node instrRetMsgNode = root.selectSingleNode("/CMS/eb/out/rd/instrRetMsg");

					map.put("iretcode", instrRetCodeNode.getText().trim());

					map.put("iretmsg", instrRetMsgNode.getText().trim());

				}

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return map;
	}

	public static void main(String[] args) {

		String str = "<?xml  version=\"1.0\" encoding=\"GBK\" ?>" + "<CMS>" + "<eb>" + "<pub>" + "<TransCode>PAYPER</TransCode>" + "<CIS>120790001527532</CIS>"
				+ "<BankCode>102</BankCode>" + "<ID>TZZXYY.y.1207</ID>" + "<TranDate>20170115</TranDate>" + "<TranTime>175238125</TranTime>"
				+ "<fSeqno>1320170115000008</fSeqno>" + "<SerialNo></SerialNo>" + "<RetCode>0</RetCode>" + "<RetMsg>成功</RetMsg>" + "</pub>" + "</eb>"
				+ "</CMS>";

		SAXReader saxReader = new SAXReader();

		Document document = null;

		try {

			document = saxReader.read(new StringReader(str));

			Element root = document.getRootElement();

			Node retCodeNode = root.selectSingleNode("/CMS/eb/pub/RetCode");

			Node retMsgNode = root.selectSingleNode("/CMS/eb/pub/RetMsg");

			System.out.println("e.getName()=" + retCodeNode.getName() + " " + retCodeNode.getText().trim());

			System.out.println("e.getName()=" + retMsgNode.getName() + " " + retMsgNode.getText().trim());

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
