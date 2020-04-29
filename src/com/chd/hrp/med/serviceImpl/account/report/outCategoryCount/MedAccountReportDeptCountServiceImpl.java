package com.chd.hrp.med.serviceImpl.account.report.outCategoryCount;

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

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.med.dao.account.report.outCategoryCount.MedAccountReportDeptCountMapper;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportDeptCountService;
import com.chd.hrp.med.serviceImpl.account.report.MedAccountReportDeptOutServiceImpl;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 出库分类统计:科室统计-查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("medAccountReportDeptCountService")
public class MedAccountReportDeptCountServiceImpl implements MedAccountReportDeptCountService {
	private static Logger logger = Logger.getLogger(MedAccountReportDeptCountServiceImpl.class);
	
	@Resource(name = "medAccountReportDeptCountMapper")
	private final MedAccountReportDeptCountMapper medAccountReportDeptCountMapper = null;
	
	@Override
	public String queryMedAccountReportDeptCount(Map<String, Object> entityMap) throws DataAccessException {
		
		//String[] column=entityMap.get("column_name").toString().split(",");
		
		if(entityMap.get("type_level") == null || "".equals(entityMap.get("type_level"))){
			return "{\"error\":\"类别级次不能为空\"}";
		}
	
		List<Map<String, Object>> list = medAccountReportDeptCountMapper.queryMedAccountReportDeptCount(entityMap);
		
		List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
				
		for(int i=0;i<list.size();i++){
			int a=0;
			
			HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
			Map<String,Object>map=list.get(i);
			    //name  data
			typeToMoneymap.put((String)map.get("med_type_code"), map.get("money"));
			if(returnDataMap.containsKey(map.get("dept_code")))
			{
				
			
				//如果前台传的级次为非末级，只取药品类别为首级的金额进行合计，
				if("1".equals(entityMap.get("type_level"))){
				//总金额
					if("1".equals(map.get("type_level").toString())){
						map.putAll(typeToMoneymap);
						//根据部门編碼取出取出之前存入（或运算后）的总金额
						Map sumMoney=returnDataMap.get(map.get("dept_code"));
						Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
						//最新一条数据的金额
						Double count2= Double.parseDouble(map.get("money").toString());
						//两条数据相加修改amount_money
						sumMoney.put("amount_money", count+count2);
						sumMoney.putAll(typeToMoneymap);
						//将运算后的数据从新封装
						returnDataMap.put((String) map.get("dept_code"), sumMoney);
					}
				}else {
					if("1".equals(map.get("is_last").toString())){
						map.putAll(typeToMoneymap);
						//根据部门編碼取出取出之前存入（或运算后）的总金额
						Map sumMoney=returnDataMap.get(map.get("dept_code"));
						Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
						//最新一条数据的金额
						Double count2= Double.parseDouble(map.get("money").toString());
						//两条数据相加修改amount_money
						sumMoney.put("amount_money", count+count2);
						sumMoney.putAll(typeToMoneymap);
						//将运算后的数据从新封装
						returnDataMap.put((String) map.get("dept_code"), sumMoney);
							}
				}
				
					
				 
			}else{
				
			
				 //如果前台传的级次为非末级，只取药品类别为首级的金额进行合计，
				 if("1".equals(entityMap.get("type_level"))){
					 if("1".equals(map.get("type_level").toString())){
							//将转换后的数据填充到通过sql语句查询出来的map中
						 map.putAll(typeToMoneymap);
						 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
						 returnDataMap.put((String) map.get("dept_code"), map);
						 Map sumMoney= returnDataMap.get(map.get("dept_code"));
						//取出当前部门的金额填入到amount_money中
						 sumMoney.put("amount_money",map.get("money"));
						 returnDataMap.put((String) map.get("dept_code"), sumMoney);
					 }
				 }else {
					 if("1".equals(map.get("is_last").toString())){
							//将转换后的数据填充到通过sql语句查询出来的map中
						 map.putAll(typeToMoneymap);
						 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
						 returnDataMap.put((String) map.get("dept_code"), map);
						 Map sumMoney= returnDataMap.get(map.get("dept_code"));
						 sumMoney.put("amount_money",map.get("money"));
						 returnDataMap.put((String) map.get("dept_code"), sumMoney);
					 }
				 }
				 
				
			}
		}
		for (String  j : returnDataMap.keySet()) {
			overloadinglist.add(returnDataMap.get(j));
		}
		return ChdJson.toJson(overloadinglist );
	}

	@Override
	public String queryDeptCountHead(Map<String, Object> entityMap)throws DataAccessException {
		try{
			
			return ChdJson.toJsonLower(medAccountReportDeptCountMapper.queryDeptCountHead(entityMap));
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}
	}
	
	/**
	 * 移库汇总表
	 */
	@Override
	public String queryMedTransferCount(Map<String, Object> entityMap) throws DataAccessException {
		if(entityMap.get("type_level") == null || "".equals(entityMap.get("type_level"))){
			return "{\"error\":\"类别级次不能为空\"}";
		}
			
		List<Map<String, Object>> list = JsonListMapUtil.toListMapLower(medAccountReportDeptCountMapper.queryMedTransferCount(entityMap));
				
		List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
					
		for(int i=0;i<list.size();i++){
				int a=0;
				HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
				Map<String,Object>map=list.get(i);
				//name  data
				typeToMoneymap.put((String)map.get("med_type_code"), map.get("money"));
				if(returnDataMap.containsKey(map.get("store_code"))){
					//如果前台传的级次为非末级，只取药品类别为首级的金额进行合计，
					if("1".equals(entityMap.get("type_level"))){
					//总金额
					if("1".equals(map.get("type_level").toString())){
						map.putAll(typeToMoneymap);
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
					}
				}else {
					if("1".equals(map.get("is_last").toString())){
						map.putAll(typeToMoneymap);
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
					}
				}
			}else{
				//如果前台传的级次为非末级，只取药品类别为首级的金额进行合计，
				if("1".equals(entityMap.get("type_level"))){
					if("1".equals(map.get("type_level").toString())){
						//将转换后的数据填充到通过sql语句查询出来的map中
						map.putAll(typeToMoneymap);
						//重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
						returnDataMap.put((String) map.get("store_code"), map);
						Map sumMoney= returnDataMap.get(map.get("store_code"));
						//取出当前部门的金额填入到amount_money中
						sumMoney.put("amount_money",map.get("money"));
						returnDataMap.put((String) map.get("store_code"), sumMoney);
					}
				}else {
					if("1".equals(map.get("is_last").toString())){
						//将转换后的数据填充到通过sql语句查询出来的map中
						map.putAll(typeToMoneymap);
						//重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
						returnDataMap.put((String) map.get("store_code"), map);
						Map sumMoney= returnDataMap.get(map.get("store_code"));
						sumMoney.put("amount_money",map.get("money"));
						returnDataMap.put((String) map.get("store_code"), sumMoney);
					}
				}
			}
		}
		for (String  j : returnDataMap.keySet()) {
			overloadinglist.add(returnDataMap.get(j));
		}
		return ChdJson.toJson(overloadinglist );
	}
}
