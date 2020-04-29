package com.chd.hrp.mat.serviceImpl.outsum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.hip.dao.HrpHipSelectMapper;
import com.chd.hrp.mat.dao.outsum.MatOutSumMapper;
import com.chd.hrp.mat.service.outsum.MatOutSumService;

/**
 *@author Fan Yang
 *@date   2020年4月21日
 *@email  yangfan1105@dhcc.com.cn
 */
@Service("matOutSumService")
public class MatOutSumServiceImpl implements MatOutSumService {
	
	private static Logger logger = Logger.getLogger(MatOutSumServiceImpl.class);
	
	@Resource(name = "matOutSumMapper")
	private final MatOutSumMapper matOutSumMapper = null;
	
	@Override
	public String queryOutSumSingleSum(Map<String, Object> mapVo) throws DataAccessException{
		
		List<Map<String, Object>> resultMap = matOutSumMapper.queryOutSumSingleSum(mapVo);
		return ChdJson.toJson(resultMap);
	
	}
	
	@Override
	public List<Map<String, Object>> queryOutSumSingleSumPrint(Map<String, Object> mapVo) throws DataAccessException{
		List<Map<String, Object>> resultMap = matOutSumMapper.queryOutSumSingleSum(mapVo);
		return resultMap;
	}

	@Override
	public String queryDeptStoreCross(Map<String, Object> entityMap) throws DataAccessException {
		return ChdJson.toJson(queryDeptStoreCrossPrint(entityMap));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> queryDeptStoreCrossPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = matOutSumMapper.queryDeptStoreCross(entityMap);
		List<Map<String,Object>> overLoadingList = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap = new LinkedHashMap<String, Map<String,Object>>();
		List<Map<String, Object>> allMatType = matOutSumMapper.queryALLMatTypes(entityMap);
		
		
		for(int i = 0; i < list.size(); ++i){
			
			int a = 0;
			
			HashMap<String, Object> typeToMoneyMap = new HashMap<String,Object>();
			Map<String,Object> map = list.get(i);
			typeToMoneyMap.put((String)map.get("mat_type_code"), map.getOrDefault("money", "0"));
			if(returnDataMap.containsKey(map.get("dept_code"))) {
				
				if("1".equals(entityMap.get("type_level"))){
					
					if("1".equals(map.get("type_level").toString())){
						map.putAll(typeToMoneyMap);
						Map sumMoney = returnDataMap.get(map.get("dept_code"));
						Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
						Double count2 = Double.parseDouble(map.get("money").toString());
						sumMoney.put("amount_money", count + count2);
						sumMoney.putAll(typeToMoneyMap);
						returnDataMap.put((String)map.get("dept_code"), sumMoney);
					}
				}else {
					if("1".equals(map.get("is_last").toString())){
						map.putAll(typeToMoneyMap);
						Map sumMoney=returnDataMap.get(map.get("dept_code"));
						Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
						Double count2= Double.parseDouble(map.get("money").toString());
						sumMoney.put("amount_money", count+count2);
						sumMoney.putAll(typeToMoneyMap);
						returnDataMap.put((String) map.get("dept_code"), sumMoney);
					}
				}
			}else{
				 if("1".equals(entityMap.get("type_level"))){
					 if("1".equals(map.get("type_level").toString())){
						 map.putAll(typeToMoneyMap);
						 returnDataMap.put((String) map.get("dept_code"), map);
						 Map sumMoney= returnDataMap.get(map.get("dept_code"));
						 sumMoney.put("amount_money",map.get("money"));
						 returnDataMap.put((String) map.get("dept_code"), sumMoney);
					 }
				 }else {
					 if("1".equals(map.get("is_last").toString())){
						 map.putAll(typeToMoneyMap);
						 returnDataMap.put((String) map.get("dept_code"), map);
						 Map sumMoney= returnDataMap.get(map.get("dept_code"));
						 sumMoney.put("amount_money",map.get("money"));
						 returnDataMap.put((String) map.get("dept_code"), sumMoney);
					 }
				 }
			}
		}
		
		for (String  j : returnDataMap.keySet()) {
			overLoadingList.add(returnDataMap.get(j));
		}
		
		for(int listLength = 0; listLength < overLoadingList.size(); ++listLength){
			for(int typeNum = 0; typeNum < allMatType.size(); ++typeNum){
				if(overLoadingList.get(listLength).get(allMatType.get(typeNum).get("id")) == null)
					overLoadingList.get(listLength).put(allMatType.get(typeNum).get("id").toString(), 0);
			}
		}
		
		return overLoadingList;
	}

}
