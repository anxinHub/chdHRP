/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.storage.query.MatSupInDetailStoreMapper;
import com.chd.hrp.mat.dao.storage.query.MatSupInStoreMapper;
import com.chd.hrp.mat.service.storage.query.MatSupInDetailStoreService;
import com.chd.hrp.mat.service.storage.query.MatSupInStoreService;

/**
 * 
 * @Description:
 * 入库明细查询
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matSupInDetailStoreService")
public class MatSupInDetailStoreServiceImpl implements MatSupInDetailStoreService {

	private static Logger logger = Logger.getLogger(MatInDetailServiceImpl.class);
	
	@Resource(name = "matSupInDetailStoreMapper")
	private final MatSupInDetailStoreMapper matSupInDetailStoreMapper = null;
	

	@Override
	public String queryMatSupInDetailStore(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
    
		List<Map<String, Object>> list = matSupInDetailStoreMapper.queryMatSupInDetailStore(entityMap);
		List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
			Map<String,Object>map=list.get(i);
			typeToMoneymap.put((String)map.get("sup_code")+map.get("subj_code")+map.get("store_id"), map.get("money"));
			
			if(returnDataMap.containsKey((String)map.get("sup_code")+map.get("subj_code")+map.get("store_id")) )
			{
				map.putAll(typeToMoneymap);
				//根据部门編碼取出取出之前存入（或运算后）的总金额
				Map sumMoney=returnDataMap.get((String)map.get("sup_code")+map.get("subj_code")+map.get("store_id"));
				//总金额
				Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
				//最新一条数据的金额
				Double count2= Double.parseDouble(map.get("money").toString());
				//两条数据相加修改amount_money
				sumMoney.put("amount_money", count+count2);
				sumMoney.putAll(typeToMoneymap);
				//将运算后的数据从新封装
				returnDataMap.put((String)map.get("sup_code")+map.get("subj_code")+map.get("store_id"), sumMoney);
					
				 
			}else{
				//将转换后的数据填充到通过sql语句查询出来的map中
				 map.putAll(typeToMoneymap);
				 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
				 returnDataMap.put((String)map.get("sup_code")+map.get("subj_code")+map.get("store_id"), map);
				 Map sumMoney= returnDataMap.get((String)map.get("sup_code")+map.get("subj_code")+map.get("store_id"));
				//取出当前部门的金额填入到amount_money中
				 sumMoney.put("amount_money",map.get("money"));
				 returnDataMap.put((String)map.get("sup_code")+map.get("subj_code")+map.get("store_id"), sumMoney);
			}
		}
		for (String  j : returnDataMap.keySet()) {
			
			overloadinglist.add(returnDataMap.get(j));
		}
		return ChdJson.toJson(overloadinglist);
			
			/*List<Map<String, Object>> list = matSupInDetailStoreMapper.queryMatSupInDetailStore(entityMap);
			List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
			LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
			
			for(int i=0;i<list.size();i++){
				int a=0;
				
				HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
				Map<String,Object>map=list.get(i);
				    //name  data
				typeToMoneymap.put((String)map.get("mat_type_code"), map.get("money"));
				if(returnDataMap.containsKey((String)map.get("sup_code")+map.get("store_id"))))
				{
					
				
					//如果前台传的级次为非末级，只取物资类别为首级的金额进行合计，
					if("1".equals(entityMap.get("type_level"))){
					//总金额
						if("1".equals(map.get("type_level").toString())){
							map.putAll(typeToMoneymap);
							//根据部门編碼取出取出之前存入（或运算后）的总金额
							Map sumMoney=returnDataMap.get((String)map.get("sup_code")+map.get("store_id"));
							Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
							//最新一条数据的金额
							Double count2= Double.parseDouble(map.get("money").toString());
							//两条数据相加修改amount_money
							sumMoney.put("amount_money", count+count2);
							sumMoney.putAll(typeToMoneymap);
							//将运算后的数据从新封装
							returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), sumMoney);
						}
					}else {
						if("1".equals(map.get("is_last").toString())){
							map.putAll(typeToMoneymap);
							//根据部门編碼取出取出之前存入（或运算后）的总金额
							Map sumMoney=returnDataMap.get((String)map.get("sup_code")+map.get("store_id"));
							Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
							//最新一条数据的金额
							Double count2= Double.parseDouble(map.get("money").toString());
							//两条数据相加修改amount_money
							sumMoney.put("amount_money", count+count2);
							sumMoney.putAll(typeToMoneymap);
							//将运算后的数据从新封装
							returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), sumMoney);
								}
					}
					
						
					 
				}else{
					
				
					 //如果前台传的级次为非末级，只取物资类别为首级的金额进行合计，
					 if("1".equals(entityMap.get("type_level"))){
						 if("1".equals(map.get("type_level").toString())){
								//将转换后的数据填充到通过sql语句查询出来的map中
							 map.putAll(typeToMoneymap);
							 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
							 returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), map);
							 Map sumMoney= returnDataMap.get((String)map.get("sup_code")+map.get("store_id"));
							//取出当前部门的金额填入到amount_money中
							 sumMoney.put("amount_money",map.get("money"));
							 returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), sumMoney);
						 }
					 }else {
						 if("1".equals(map.get("is_last").toString())){
								//将转换后的数据填充到通过sql语句查询出来的map中
							 map.putAll(typeToMoneymap);
							 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
							 returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), map);
							 Map sumMoney= returnDataMap.get((String)map.get("sup_code")+map.get("store_id"));
							 sumMoney.put("amount_money",map.get("money"));
							 returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), sumMoney);
						 }
					 }
					 
					
				}
			}
			for (String  j : returnDataMap.keySet()) {
				overloadinglist.add(returnDataMap.get(j));
			}
			return ChdJson.toJson(overloadinglist );*/
		
	}




	@Override
	public List<Map<String, Object>> queryMatSupInDetailStorePrint(Map<String, Object> entityMap) {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = matSupInDetailStoreMapper.queryMatSupInDetailStore(entityMap);
		List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
			Map<String,Object>map=list.get(i);
			typeToMoneymap.put((String)map.get("sup_code")+map.get("subj_code")+map.get("store_id"), map.get("money"));
			
			if(returnDataMap.containsKey((String)map.get("sup_code")+map.get("subj_code")+map.get("store_id")) )
			{
				 map.putAll(typeToMoneymap);
				//根据部门編碼取出取出之前存入（或运算后）的总金额
				Map sumMoney=returnDataMap.get((String) map.get("sup_code")+map.get("subj_code")+map.get("store_id"));
				//总金额
				Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
				//最新一条数据的金额
				Double count2= Double.parseDouble(map.get("money").toString());
				//两条数据相加修改amount_money
				sumMoney.put("amount_money", count+count2);
				sumMoney.putAll(typeToMoneymap);
				//将运算后的数据从新封装
				returnDataMap.put((String) map.get("sup_code")+map.get("subj_code")+map.get("store_id"), sumMoney);
					
				 
			}else{
				//将转换后的数据填充到通过sql语句查询出来的map中
				 map.putAll(typeToMoneymap);
				 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
				 returnDataMap.put((String) map.get("sup_code")+map.get("subj_code")+map.get("store_id"), map);
				 Map sumMoney= returnDataMap.get((String) map.get("sup_code")+map.get("subj_code")+map.get("store_id"));
				//取出当前部门的金额填入到amount_money中
				 sumMoney.put("amount_money",map.get("money"));
				 returnDataMap.put((String) map.get("sup_code")+map.get("subj_code")+map.get("store_id"), sumMoney);
			}
		}
		for (String  j : returnDataMap.keySet()) { 
			
			overloadinglist.add(returnDataMap.get(j));
		}
		return overloadinglist;
			
			/*List<Map<String, Object>> list = matSupInDetailStoreMapper.queryMatSupInDetailStore(entityMap);
			List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
			LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
			
			for(int i=0;i<list.size();i++){
				int a=0;
				
				HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
				Map<String,Object>map=list.get(i);
				    //name  data
				typeToMoneymap.put((String)map.get("mat_type_code"), map.get("money"));
				if(returnDataMap.containsKey((String)map.get("sup_code")+map.get("store_id"))))
				{
					
				
					//如果前台传的级次为非末级，只取物资类别为首级的金额进行合计，
					if("1".equals(entityMap.get("type_level"))){
					//总金额
						if("1".equals(map.get("type_level").toString())){
							map.putAll(typeToMoneymap);
							//根据部门編碼取出取出之前存入（或运算后）的总金额
							Map sumMoney=returnDataMap.get((String) map.get("sup_code")+map.get("store_id"));
							Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
							//最新一条数据的金额
							Double count2= Double.parseDouble(map.get("money").toString());
							//两条数据相加修改amount_money
							sumMoney.put("amount_money", count+count2);
							sumMoney.putAll(typeToMoneymap);
							//将运算后的数据从新封装
							returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), sumMoney);
						}
					}else {
						if("1".equals(map.get("is_last").toString())){
							map.putAll(typeToMoneymap);
							//根据部门編碼取出取出之前存入（或运算后）的总金额
							Map sumMoney=returnDataMap.get((String) map.get("sup_code")+map.get("store_id"));
							Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
							//最新一条数据的金额
							Double count2= Double.parseDouble(map.get("money").toString());
							//两条数据相加修改amount_money
							sumMoney.put("amount_money", count+count2);
							sumMoney.putAll(typeToMoneymap);
							//将运算后的数据从新封装
							returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), sumMoney);
								}
					}
					
						
					 
				}else{
					
				
					 //如果前台传的级次为非末级，只取物资类别为首级的金额进行合计，
					 if("1".equals(entityMap.get("type_level"))){
						 if("1".equals(map.get("type_level").toString())){
								//将转换后的数据填充到通过sql语句查询出来的map中
							 map.putAll(typeToMoneymap);
							 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
							 returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), map);
							 Map sumMoney= returnDataMap.get((String) map.get("sup_code")+map.get("store_id"));
							//取出当前部门的金额填入到amount_money中
							 sumMoney.put("amount_money",map.get("money"));
							 returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), sumMoney);
						 }
					 }else {
						 if("1".equals(map.get("is_last").toString())){
								//将转换后的数据填充到通过sql语句查询出来的map中
							 map.putAll(typeToMoneymap);
							 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
							 returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), map);
							 Map sumMoney= returnDataMap.get((String) map.get("sup_code")+map.get("store_id"));
							 sumMoney.put("amount_money",map.get("money"));
							 returnDataMap.put((String) map.get("sup_code")+map.get("store_id"), sumMoney);
						 }
					 }
					 
					
				}
			}
			for (String  j : returnDataMap.keySet()) {
				overloadinglist.add(returnDataMap.get(j));
			}
		
		return overloadinglist;*/
	}
		
	
}
