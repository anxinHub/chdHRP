package com.chd.webservice.server.his.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.fastjson.JSON;
import com.chd.base.jdbc.ConfigInit;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.webservice.dao.WebServiceMapper;
import com.chd.webservice.server.his.HisService;

/**
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2017年2月26日 下午2:10:10
 * @Company: 杭州亦童科技有限公司
 * @网站：www.e-tonggroup.com
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
@Service("hisService")
public class HisServiceImpl implements HisService {
 
	private static Logger logger = Logger.getLogger(HisServiceImpl.class);

	// 消息码
	//private static Properties transType = ConfigInit.getTransType();

	// 交易码
	//private static Properties processingCode = ConfigInit.getProcessingCode();

	// 返回结果集交易码
	private static Properties processingCodeResultSet = ConfigInit.getProcessingCodeResultSet();
	// 需要删除医嘱的交易码
	//private static Properties processingCodeDel = ConfigInit.getProcessingCodeDel();

	@Resource(name = "webServiceMapper")
	private final WebServiceMapper webServiceMapper = null;
 
	@Autowired
	private DataSourceTransactionManager txManager;

	@SuppressWarnings("unchecked")
    @Override
	public String hrpCommIof(String transType, String processingCode, int group_id, int hos_id, String copy_code,String dsCode, String inputStr, int item_indicator) {

		Map<String, Object> entityMap = new HashMap<String, Object>();
		Map<String, Object> entityMapRetust = new HashMap<String, Object>();
		entityMap.put("prm_transtype", transType);
		entityMap.put("prm_processingcode", processingCode);
		entityMap.put("prm_group_id", group_id);
		entityMap.put("prm_hos_id", hos_id);
		entityMap.put("prm_copy_code", copy_code);
		entityMap.put("prm_inputstr", inputStr);
		entityMap.put("prm_item_indicator", item_indicator);
		entityMap.put("prm_dscode", dsCode);
		
		logger.debug("webService接口调用："+entityMap);
		
		
		//entityMapRetust.putAll(entityMap);
		//由于返回字符串格式不对例如："[{"aa":"123"},{"bb":"3456"}]"，应该为："[{\"aa\":\"123\"},{\"bb\":\"3456\"}]"
		entityMapRetust.remove("prm_inputstr");
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
		
		TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
		
		try {
			//判断是否为JSON字符串
			if(inputStr.length() > 0 && inputStr.contains("[{") && inputStr.contains("}]")){ 
				List<Map<String, Object>> jsonList = JsonListMapUtil.getListMap(inputStr);
				if(jsonList.size() == 0){
					txManager.rollback(status);
					entityMapRetust.put("prm_responsecode", "17");
					entityMapRetust.put("prm_outputstr", "JSON参数格式不正确！");
					
					logger.debug("webService接口返回："+entityMapRetust);
					return Json.toJson(entityMapRetust, JsonFormat.full().setDateFormat("yyyy-MM-dd"));
				}
				/*解析json再拼装json有点多此一举，不妨直接对json串进行分割
				 * int i = 0;
				StringBuffer str = new StringBuffer();
				for(Map<String, Object> map : jsonList){
					str.append("{");
					for(String key : map.keySet()){
						str.append(key.toLowerCase()).append(":").append(map.get(key)).append(",");
					}
					str.substring(0, str.length()-1);
					str.append("},");
					inputStrs[i] = str.toString();
					i++;
				}*/
				//String[] inputStrs = inputStr.split("\\^\\|");
				//去掉前后的“[”和“]”再进行分割组数
				String[] inputStrs = inputStr.substring(1, inputStr.length()-1).split("\\},\\{");
				int index = inputStrs.length;
				
				boolean is_rollback = false;
				
				for(int i = 0; i < index; i++){
					String str = inputStrs[i];
					entityMap.put("prm_inputstr", str);
					if(i == 0 && i == index - 1){
						if(i == index - 1){
							entityMap.put("prm_item_indicator", 0);
						}else{
							entityMap.put("prm_item_indicator", 1);
						}
					}else{
						if(i == index - 1){
							entityMap.put("prm_item_indicator", 9);
						}else{
							entityMap.put("prm_item_indicator", 2);
						}
					}

					if (processingCodeResultSet.containsKey(processingCode)) {
						
						webServiceMapper.hrpCommGroupIOF(entityMap); 
		
					} else {
	
						webServiceMapper.hrpCommIOF(entityMap);
						if(entityMap.get("prm_responsecode") == null || !"00".equals(entityMap.get("prm_responsecode").toString())){
							is_rollback = true;
							break;
						}
					}
				}
				
				if(!is_rollback){
					txManager.commit(status);
				}else{
					txManager.rollback(status);
					/*if (processingCodeDel.containsKey(processingCode)) {
						//当发生异常后删除医嘱信息
						Map<String, Object> deleteMap = new HashMap<String, Object>();
						deleteMap.putAll(entityMap);
						deleteMap.put("prm_transtype", "1004");
						deleteMap.put("processingcode", "100450");
						webServiceMapper.hrpCommIOF(deleteMap);
					}*/
				}
			}else{
				if (processingCodeResultSet.containsKey(processingCode)) {
					
					webServiceMapper.hrpCommGroupIOF(entityMap);
	
				} else {
	
					webServiceMapper.hrpCommIOF(entityMap);
	
					if(entityMap.get("prm_responsecode") != null && "00".equals(entityMap.get("prm_responsecode").toString())){
						txManager.commit(status);
					}else{
						txManager.rollback(status);
						/*if (processingCodeDel.containsKey(processingCode)) {
							//当发生异常后删除医嘱信息
							Map<String, Object> deleteMap = new HashMap<String, Object>();
							deleteMap.putAll(entityMap);
							deleteMap.put("prm_transtype", "1004");
							deleteMap.put("processingcode", "100450");
							webServiceMapper.hrpCommIOF(deleteMap);
						}*/
					}
				}
			}
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			txManager.rollback(status);
			/*try {
				if (processingCodeDel.containsKey(processingCode)) {
					//当发生异常后删除医嘱信息
					Map<String, Object> deleteMap = new HashMap<String, Object>();
					deleteMap.putAll(entityMap);
					entityMap.put("prm_transtype", "1004");
					entityMap.put("processingcode", "100450");
					webServiceMapper.hrpCommIOF(deleteMap);
				}
			} catch (Exception e1) {

				logger.debug(e1.getMessage());
			}*/
			
			entityMap.put("prm_responsecode", "17");
			entityMap.put("prm_outputstr", entityMap.get("prm_responsecode") == null ? e.getMessage() : entityMap.get("prm_responsecode").toString() + e.getMessage());
		}
		
		if(entityMap.get("prm_outputdata")!=null){
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			list=(List<Map<String, Object>>) entityMap.get("prm_outputdata");
			entityMapRetust.put("prm_outputdata", list);
		}
		if(entityMap.get("prm_outputstr") != null && entityMap.get("prm_outputstr").toString().startsWith("[{")){
			
			List<Map<String, Object>> list = JsonListMapUtil.getListMap(entityMap.get("prm_outputstr").toString());
			entityMapRetust.put("prm_outputdata", list);
			entityMapRetust.put("prm_outputstr", "出库成功！");
		}else{
			
			entityMapRetust.put("prm_outputstr", entityMap.get("prm_outputstr"));
		}
		entityMapRetust.put("prm_responsecode", entityMap.get("prm_responsecode"));
		
		logger.debug("webService接口返回："+entityMapRetust);
        
		return JSON.toJSONString(entityMapRetust); 
	}

	/*@SuppressWarnings("unchecked")
    @Override
	public Map<String, Object> hrpCommIofToMap(String transType, String processingCode, int group_id, int hos_id, String copy_code,String dsCode, String inputStr,
	        int item_indicator) {

		Map<String, Object> entityMap = new HashMap<String, Object>();
		Map<String, Object> entityMapRetust = new HashMap<String, Object>();
		entityMap.put("prm_transtype", transType);
		entityMap.put("prm_processingcode", processingCode);
		entityMap.put("prm_group_id", group_id);
		entityMap.put("prm_hos_id", hos_id);
		entityMap.put("prm_copy_code", copy_code);
		entityMap.put("prm_inputstr", inputStr);
		entityMap.put("prm_item_indicator", item_indicator);
		entityMap.put("prm_dscode", dsCode);
		
		entityMapRetust.putAll(entityMap);
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
		
		TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
		
		try {
			List<Map<String, Object>> jsonList = JsonListMapUtil.getListMap(inputStr);
			if(jsonList.size() == 0){
				txManager.rollback(status);
				entityMapRetust.put("prm_responsecode", "17");
				entityMapRetust.put("prm_outputstr", "输入参数格式不正确！");
				logger.debug("webService接口返回："+entityMapRetust);
				return entityMapRetust;
			}
			
			//String[] inputStrs = inputStr.split("\\^\\|");
			//去掉前后的“[”和“]”再进行分割组数
			String[] inputStrs = inputStr.substring(1, inputStr.length()-2).split("},{");
			int index = inputStrs.length;

			if (processingCodeResultSet.containsKey(processingCode)) {

				entityMap.put("prm_inputstr", inputStrs[0]);
				webServiceMapper.hrpCommGroupIOF(entityMap);

			} else {
				
				boolean is_rollback = false;
				
				for(int i = 0; i < index; i++){
					String str = inputStrs[i];
					entityMap.put("prm_inputstr", str);
					if(i == 0 && i == index - 1){
						if(i == index - 1){
							entityMap.put("prm_item_indicator", 0);
						}else{
							entityMap.put("prm_item_indicator", 1);
						}
					}else{
						if(i == index - 1){
							entityMap.put("prm_item_indicator", 9);
						}else{
							entityMap.put("prm_item_indicator", 2);
						}
					}

					webServiceMapper.hrpCommIOF(entityMap);
					if(entityMap.get("prm_responsecode") == null && !"00".equals(entityMap.get("prm_responsecode").toString())){
						is_rollback = true;
						break;
					}
				}

				if(!is_rollback){
					txManager.commit(status);
				}else{
					txManager.rollback(status);
					if (processingCodeDel.containsKey(processingCode)) {
						//当发生异常后删除医嘱信息
						Map<String, Object> deleteMap = new HashMap<String, Object>();
						deleteMap.putAll(entityMap);
						deleteMap.put("prm_transtype", "1004");
						deleteMap.put("processingcode", "100450");
						webServiceMapper.hrpCommIOF(deleteMap);
					}
				}
				
			}
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			txManager.rollback(status);
			try {
				if (processingCodeDel.containsKey(processingCode)) {
					//当发生异常后删除医嘱信息
					Map<String, Object> deleteMap = new HashMap<String, Object>();
					deleteMap.putAll(entityMap);
					deleteMap.put("prm_transtype", "1004");
					deleteMap.put("processingcode", "100450");
					webServiceMapper.hrpCommIOF(deleteMap);
				}
			} catch (Exception e1) {

				logger.debug(e1.getMessage());
			}
			
			entityMap.put("prm_responsecode", "17");
			entityMap.put("prm_outputstr", entityMap.get("prm_responsecode") == null ? e.getMessage() : entityMap.get("prm_responsecode").toString() + e.getMessage());
			
		}
		if(entityMap.get("prm_outputdata")!=null){
			List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			list=(List<Map<String, Object>>) entityMap.get("prm_outputdata");
			entityMapRetust.put("prm_outputdata", Json.toJson(list));
			
		}
		entityMapRetust.put("prm_outputstr", entityMap.get("prm_outputstr"));
		entityMapRetust.put("prm_responsecode", entityMap.get("prm_responsecode"));
		
		logger.debug("webService接口返回："+entityMapRetust);
		
		return entityMapRetust;
		
	}*/

}
