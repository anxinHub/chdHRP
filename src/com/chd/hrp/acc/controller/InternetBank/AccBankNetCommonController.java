/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.acc.controller.InternetBank;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.InternetBank.AccBankNetCommonService;

/**
 * 
 * @Description: 银企互联公用模块
 * @Table: MAT_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccBankNetCommonController extends BaseController {

	private static Logger logger = Logger.getLogger(AccBankNetCommonController.class);

	// 引入Service服务
	@Resource(name = "accBankNetCommonService")
	private final AccBankNetCommonService accBankNetCommonService = null;

	/**
	 * @Description 查询 银行信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/queryAccBankForInternet", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccBankForInternet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return accBankNetCommonService.queryAccBankForInternet(mapVo);

	}

	/**
	 * @Description 查询 银行信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/querySupBankForInternet", method = RequestMethod.POST)
	@ResponseBody
	public String querySupBankForInternet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		return accBankNetCommonService.querySupBankForInternet(mapVo);

	}

	@RequestMapping(value = "/hrp/acc/internetbank/queryAccBankNetICBCCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccBankNetICBCCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return accBankNetCommonService.queryAccBankNetICBCCode(mapVo);
	}
	
	@RequestMapping(value = "/hrp/acc/internetbank/queryAccICBCIBPSCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccICBCIBPSCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String key = String.valueOf(mapVo.get("key"));
		
		key = key.replace("*", ".*");
		
		mapVo.put("key", key);
		
		return accBankNetCommonService.queryAccICBCIBPSCode(mapVo);
	}
	
	@RequestMapping(value = "/hrp/acc/internetbank/queryAccWageItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccWageItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		return accBankNetCommonService.queryAccWageItem(mapVo);
	}
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcommon/accBankNetICBCIBPSMainPage", method = RequestMethod.GET)
	public String accBankNetPaymentMainPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/accbanknetcommon/accBankNetICBCIBPSMain";

	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcommon/queryAccICBCIBPSMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccICBCIBPSMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str = accBankNetCommonService.queryAccICBCIBPSMain(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	/**
	 * 导入数据页面
	 * */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcommon/importAccICBCIBPSPage", method = RequestMethod.GET)
	public String importAccICBCIBPSPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/acc/internetbank/accbanknetcommon/accICBCIBPSImport";
	}
	
	/*
	 * 解析XML文件，返回节点信息
	 */
	private InputStream requestInputStream(MultipartFile mFile)  throws Exception{
		// 读取XML文件内容
		BufferedReader br = new BufferedReader(new InputStreamReader(mFile.getInputStream(), "GB2312"));
		
		StringBuffer buffer = new StringBuffer();
		
		String line = " ";
		
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}

		InputStream requestInputStream = new ByteArrayInputStream(buffer.toString().getBytes());

		return requestInputStream;
	}
	
	@RequestMapping(value="/hrp/acc/internetbank/accbanknetcommon/readAccICBCIBPSFiles",method = RequestMethod.POST)  
    public String readAccICBCIBPSFiles(@RequestParam Map<String, Object> entityMap,Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response) throws IOException { 

		String reMsg = "导入成功";
		
		try{
			
			SAXReader saxReader = new SAXReader();Document document = null;
			
			plupload.setRequest(request);
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();
			
			MultiValueMap<String, MultipartFile> fileMap = multipartRequest.getMultiFileMap();
			
			if(fileMap == null){reMsg = "请上传文件"; return reMsg;}
			
//			List<Map<String, Object>> tableList = accBankNetCommonService.queryAccICBCIBPS(entityMap);//数据库数据
//			
			Map<String,Object> tableMap = new HashMap<String,Object>();
//			
//			for(Map tmpMap : tableList){
//				
//				tableMap.put(tmpMap.get("ibps_code").toString(), tmpMap.get("ibps_code"));
//				
//				tableMap.put(tmpMap.get("ibps_name").toString(), tmpMap.get("ibps_name"));
//				
//			}
			
			accBankNetCommonService.deleteBatchICBCIBPS();
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			//List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
			
			//开始迭代获取
			Iterator<String> iter = fileMap.keySet().iterator();
			
			while (iter.hasNext()) {

				List<MultipartFile> fileList = fileMap.get(iter.next());

				for (MultipartFile multipartFile : fileList) {

					document =saxReader.read(requestInputStream(multipartFile));
					
					Element root = document.getRootElement();
					
					List<Element> childList = root.selectNodes("/IbpsBankCodeConf/ibps/ibpsName");
					
					for (int i = 0; i < childList.size(); i++) {

						Element e = childList.get(i);
						
						if(e.attributeValue("paySysBankCode") ==null || "".equals(e.attributeValue("paySysBankCode"))){continue;}
						
						if(e.attributeValue("name") ==null || "".equals(e.attributeValue("name"))){continue;}

						tableMap.put(e.attributeValue("paySysBankCode"), e.attributeValue("name"));

					}
	 
				}
				
			}
			
			int count = 1;
			
			for(String key : tableMap.keySet()){
				
				Map<String,Object> tmpMap = new HashMap<String,Object>();
				
				tmpMap.put("ibps_code", key);
				
				tmpMap.put("ibps_name", tableMap.get(key));
				
				tmpMap.put("spell_code", StringTool.toPinyinShouZiMu(tableMap.get(key).toString()));
				
				tmpMap.put("wbx_code", StringTool.toWuBi(tableMap.get(key).toString()));
				
				tmpMap.put("is_stop", "0");
				
				addList.add(tmpMap);
				
				//设置500条提交一次
				if (count>0 && count % 500 == 0) {
					
					accBankNetCommonService.addBatchICBCIBPS(addList);
					
					addList.removeAll(addList);
					
				}
				count++;
				
			}

			if(addList.size() > 0){
				
				accBankNetCommonService.addBatchICBCIBPS(addList);
			}

		}catch(Exception e){
			
			logger.error(e.getMessage(), e);

			reMsg = "操作失败";
		}

		
		response.getWriter().print(reMsg);
		
		return null;
    } 
}
