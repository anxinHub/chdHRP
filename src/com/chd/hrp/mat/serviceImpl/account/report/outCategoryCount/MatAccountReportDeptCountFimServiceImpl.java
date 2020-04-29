package com.chd.hrp.mat.serviceImpl.account.report.outCategoryCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.account.report.outCategoryCount.MatAccountReportDeptCountFimMapper;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportDeptCountFimService;

/**
 * @Description:
 * 出库分类统计:科室统计-查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("matAccountReportDeptCountFimService")
public class MatAccountReportDeptCountFimServiceImpl implements MatAccountReportDeptCountFimService {
	private static Logger logger = Logger.getLogger(MatAccountReportDeptCountFimServiceImpl.class);
	
	@Resource(name = "matAccountReportDeptCountFimMapper")
	private final MatAccountReportDeptCountFimMapper matAccountReportDeptCountFimMapper = null;
	
	@Override
	public String queryMatAccountReportDeptCountFim(Map<String, Object> entityMap) throws DataAccessException {
		
		//String[] column=entityMap.get("column_name").toString().split(",");
		
		List<Map<String, Object>> list = matAccountReportDeptCountFimMapper.queryMatAccountReportDeptCountFim(entityMap);
		
		List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
				
		for(int i=0;i<list.size();i++){
			int a=0;
			
			HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
			Map<String,Object>map=list.get(i);
			    //name  data
			typeToMoneymap.put((String)map.get("fim_type_code"), map.get("money"));
			if(returnDataMap.containsKey(map.get("store_code"))){
					
				//map.putAll(typeToMoneymap);
				//根据部门編碼取出取出之前存入（或运算后）的总金额
				Map sumMoney=returnDataMap.get(map.get("store_code"));
				Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
				//最新一条数据的金额
				Double count2= Double.parseDouble(map.get("money").toString());
				//两条数据相加修改amount_money
				sumMoney.put("amount_money", count+count2);
				sumMoney.putAll(typeToMoneymap);
				//将运算后的数据从新封装
				returnDataMap.put((String) map.get("store_code"), sumMoney);
				
			}else{
				//将转换后的数据填充到通过sql语句查询出来的map中
				map.putAll(typeToMoneymap);
				//重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
				returnDataMap.put((String) map.get("store_code"), map);
				Map sumMoney= returnDataMap.get(map.get("store_code"));
				//取出当前部门的金额填入到amount_money中
				sumMoney.put("amount_money",map.get("money"));
				returnDataMap.put((String) map.get("store_code"), sumMoney);
			}
		}
		for (String  j : returnDataMap.keySet()) {
			overloadinglist.add(returnDataMap.get(j));
		}
		return ChdJson.toJson(overloadinglist );
	}

	@Override
	public String queryDeptCountFimHead(Map<String, Object> entityMap)throws DataAccessException {
		try{
			
			return ChdJson.toJsonLower(matAccountReportDeptCountFimMapper.queryDeptCountFimHead(entityMap));
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}
	}
	
	/**
	 * 移库汇总表
	 */
	@Override
	public String queryMatTransferCountFim(Map<String, Object> entityMap) throws DataAccessException {
		 
		List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(matAccountReportDeptCountFimMapper.queryMatTransferCountFim(entityMap));
				
		List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
					
		for(int i=0;i<list.size();i++){
			int a=0;
			HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
			Map<String,Object>map=list.get(i);
			//name  data
			typeToMoneymap.put((String)map.get("store_code")+map.get("fim_type_code"), map.get("money"));
			if(returnDataMap.containsKey((String)map.get("store_code")+map.get("fim_type_code"))){
					
				//map.putAll(typeToMoneymap);
				//根据部门編碼取出取出之前存入（或运算后）的总金额
				Map sumMoney=returnDataMap.get((String)map.get("store_code")+map.get("fim_type_code"));
				Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
				//最新一条数据的金额
				Double count2= Double.parseDouble(map.get("money").toString());
				//两条数据相加修改amount_money
				sumMoney.put("amount_money", count+count2);
				sumMoney.putAll(typeToMoneymap);
				//将运算后的数据从新封装
				returnDataMap.put((String)map.get("store_code")+map.get("fim_type_code"), sumMoney);
				
			}else{
				//将转换后的数据填充到通过sql语句查询出来的map中
				map.putAll(typeToMoneymap);
				//重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
				returnDataMap.put((String)map.get("store_code")+map.get("fim_type_code"), map);
				Map sumMoney= returnDataMap.get((String)map.get("store_code")+map.get("fim_type_code"));
				//取出当前部门的金额填入到amount_money中
				sumMoney.put("amount_money",map.get("money"));
				returnDataMap.put((String)map.get("store_code")+map.get("fim_type_code"), sumMoney);
			}
		}
		for (String  j : returnDataMap.keySet()) {
			overloadinglist.add(returnDataMap.get(j));
		}
		return ChdJson.toJson(overloadinglist );
	}

	@Override
	public List<Map<String, Object>> queryMatAccountReportDeptCountFimPrint(Map<String, Object> entityMap) throws DataAccessException {
		//String[] column=entityMap.get("column_name").toString().split(",");
					entityMap.put("group_id", SessionManager.getGroupId());
					entityMap.put("hos_id", SessionManager.getHosId());
					entityMap.put("copy_code", SessionManager.getCopyCode());
					entityMap.put("user_id", SessionManager.getUserId());
					entityMap.put("show_history", MyConfig.getSysPara("03001"));
				List<Map<String, Object>> list = matAccountReportDeptCountFimMapper.queryMatAccountReportDeptCountFim(entityMap);
				
				List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
				LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
						
				for(int i=0;i<list.size();i++){
					int a=0;
					
					HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
					Map<String,Object>map=list.get(i);
					    //name  data
					typeToMoneymap.put((String)map.get("fim_type_code"), map.get("money"));
					if(returnDataMap.containsKey(map.get("store_code"))){
							
						//map.putAll(typeToMoneymap);
						//根据部门編碼取出取出之前存入（或运算后）的总金额
						Map sumMoney=returnDataMap.get(map.get("store_code"));
						Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
						//最新一条数据的金额
						Double count2= Double.parseDouble(map.get("money").toString());
						//两条数据相加修改amount_money
						sumMoney.put("amount_money", count+count2);
						sumMoney.putAll(typeToMoneymap);
						//将运算后的数据从新封装
						returnDataMap.put((String) map.get("store_code"), sumMoney);
						
					}else{
						//将转换后的数据填充到通过sql语句查询出来的map中
						map.putAll(typeToMoneymap);
						//重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
						returnDataMap.put((String) map.get("store_code"), map);
						Map sumMoney= returnDataMap.get(map.get("store_code"));
						//取出当前部门的金额填入到amount_money中
						sumMoney.put("amount_money",map.get("money"));
						returnDataMap.put((String) map.get("store_code"), sumMoney);
					}
				}
				for (String  j : returnDataMap.keySet()) {
					overloadinglist.add(returnDataMap.get(j));
				}
				return overloadinglist;
	}

	@Override
	public List<Map<String, Object>> queryMatTransferCountFimprint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(matAccountReportDeptCountFimMapper.queryMatTransferCountFim(entityMap));
				
		List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
					
		for(int i=0;i<list.size();i++){
				int a=0;
				HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
				Map<String,Object>map=list.get(i);
				//name  data
				typeToMoneymap.put((String)map.get("store_code")+map.get("fim_type_code"), map.get("money"));
				if(returnDataMap.containsKey((String)map.get("store_code")+map.get("fim_type_code"))){
						
					//map.putAll(typeToMoneymap);
					//根据部门編碼取出取出之前存入（或运算后）的总金额
					Map sumMoney=returnDataMap.get((String)map.get("store_code")+map.get("fim_type_code"));
					Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
					//最新一条数据的金额
					Double count2= Double.parseDouble(map.get("money").toString());
					//两条数据相加修改amount_money
					sumMoney.put("amount_money", count+count2);
					sumMoney.putAll(typeToMoneymap);
					//将运算后的数据从新封装
					returnDataMap.put((String)map.get("store_code")+map.get("fim_type_code"), sumMoney);
					
				}else{
					//将转换后的数据填充到通过sql语句查询出来的map中
					map.putAll(typeToMoneymap);
					//重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
					returnDataMap.put((String)map.get("store_code")+map.get("fim_type_code"), map);
					Map sumMoney= returnDataMap.get((String)map.get("store_code")+map.get("fim_type_code"));
					//取出当前部门的金额填入到amount_money中
					sumMoney.put("amount_money",map.get("money"));
					returnDataMap.put((String)map.get("store_code")+map.get("fim_type_code"), sumMoney);
				}
		}
		for (String  j : returnDataMap.keySet()) {
			overloadinglist.add(returnDataMap.get(j));
		}
		return overloadinglist;
	}
}
