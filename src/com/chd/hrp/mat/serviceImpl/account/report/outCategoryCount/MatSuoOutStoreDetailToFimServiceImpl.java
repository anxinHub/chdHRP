package com.chd.hrp.mat.serviceImpl.account.report.outCategoryCount;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import oracle.net.aso.p;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.dao.account.report.outCategoryCount.MatAccountReportDeptCountToFimMapper;
import com.chd.hrp.mat.dao.account.report.outCategoryCount.MatSuoOutStoreDetailToFimMapper;
import com.chd.hrp.mat.dao.storage.query.MatSupInStoreDetailToFimMapper;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportDeptCountService;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportDeptCountToFimService;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatSuoOutStoreDetailToFimService;
import com.chd.hrp.mat.serviceImpl.account.report.MatAccountReportDeptOutServiceImpl;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 出库分类统计:科室统计-查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("matSuoOutStoreDetailToFimService")
public class MatSuoOutStoreDetailToFimServiceImpl implements MatSuoOutStoreDetailToFimService {
	private static Logger logger = Logger.getLogger(MatSuoOutStoreDetailToFimServiceImpl.class);
	
	@Resource(name = "matSuoOutStoreDetailToFimMapper")
	private final MatSuoOutStoreDetailToFimMapper matSuoOutStoreDetailToFimMapper = null;
	
	@Resource(name = "matSupInStoreDetailToFimMapper")
	private final MatSupInStoreDetailToFimMapper matSupInStoreDetailToFimMapper = null;
	
	@Override
	public String queryMatSuoOutStoreDetailToFim(Map<String, Object> entityMap) throws DataAccessException, ParseException {
				
				
				//是否按库房结账
			    Object isAccByStore = MyConfig.getSysPara("04045");

				if(isAccByStore==null || "0".equals(isAccByStore.toString())){
					//整体结账
					Map<String,Object> ClosingYearMonth = matSuoOutStoreDetailToFimMapper.queryClosingYearMonth(entityMap);
					if(!"1".equals(ClosingYearMonth.get("mat_flag").toString())){
						return "{\"error\":\"当月未结账\"}";
					}else{
						entityMap.put("begin_date",ClosingYearMonth.get("begin_date").toString().substring(0,10));
						entityMap.put("end_date",ClosingYearMonth.get("end_date").toString().substring(0,10));
					}
					 
				}else{
					//按仓库结账 
					int isSetAcc = matSupInStoreDetailToFimMapper.hasSetAccount(entityMap);//判断结账表中是否有结账记录
					if(isSetAcc!=0){
						String year=entityMap.get("begin_year").toString();
						String month=entityMap.get("begin_month").toString();
						String firstDay=DateUtil.getMinMonthDate(year,month);
						String lastDay=DateUtil.getMaxMonthDate(year,month);

						entityMap.put("begin_date",year+"-"+month+"-"+firstDay);
						entityMap.put("end_date",year+"-"+month+"-"+lastDay);
					}else{
						return "{\"error\":\"当月未结账\"}";
					}
				}
				
				List<Map<String, Object>> list = matSuoOutStoreDetailToFimMapper.queryMatSuoOutStoreDetailToFim(entityMap);
				List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
				LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
				for(int i=0;i<list.size();i++){  
					 
					HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
					//迭代list获取所有数据，
					Map<String,Object>map=list.get(i);
					//将fim_type_code的值转换为匹配页面display属性，将科室的钱数放在value上
					typeToMoneymap.put((String) map.get("fim_type_code"),map.get("money"));
					/**
					 * 判断返回数据的科室是否为单条数据，如果为单条数据时，
					 * 将总价直接放入返回的map中，如果为多条数据时对查询出金额进行运算，
					 * 第一条数据会走else 将当前金额赋值给amount_money 如果有多条数据再取出最新的money与总金额相加
					 */
					if(returnDataMap.containsKey(map.get("dept_code")+","+map.get("bus_type_code")))
					{
						map.putAll(typeToMoneymap);
						//根据部门編碼取出取出之前存入（或运算后）的总金额
						Map sumMoney=returnDataMap.get(map.get("dept_code")+","+map.get("bus_type_code"));
						//总金额
						Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
						//最新一条数据的金额
						Double count2= Double.parseDouble(map.get("money").toString());
						//两条数据相加修改amount_money
						sumMoney.put("amount_money", count+count2);
						//sumMoney.putAll(typeToMoneymap);
						if(map.get("fim_type_code").equals(sumMoney.get("fim_type_code"))){
							//取出当前数据物资类别所对应的钱
							Double typeMoney=Double.parseDouble(map.get(map.get("fim_type_code")).toString());
							//再取出之前存好的钱，进行运算
							Double oldtypeMoney=Double.parseDouble(sumMoney.get(sumMoney.get("fim_type_code")).toString());
							sumMoney.put(map.get("fim_type_code").toString(),typeMoney+oldtypeMoney);
							}else{
								sumMoney.put(map.get("fim_type_code").toString(),map.get("money"));
								sumMoney.put("fim_type_code", map.get("fim_type_code"));
							}
						//将运算后的数据从新封装
						returnDataMap.put((String) map.get("dept_code")+","+map.get("bus_type_code"), sumMoney);
					}else{
						//将转换后的数据填充到通过sql语句查询出来的map中
						 map.putAll(typeToMoneymap);
						 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
						 returnDataMap.put((String) map.get("dept_code")+","+map.get("bus_type_code"), map);
						 
						 Map sumMoney= returnDataMap.get(map.get("dept_code")+","+map.get("bus_type_code"));
						//取出当前部门的金额填入到amount_money中
						 sumMoney.put("amount_money",map.get("money"));
						 sumMoney.put(map.get("fim_type_code"),map.get(map.get("fim_type_code")));
						 sumMoney.put("fim_type_code", map.get("fim_type_code"));
						 returnDataMap.put((String) map.get("dept_code")+","+map.get("bus_type_code"), sumMoney);
					}
					//

					
				}
				for (String  j : returnDataMap.keySet()) {
					
					overloadinglist.add(returnDataMap.get(j));
				}
				return ChdJson.toJsonUpper(overloadinglist);
	}

	@Override
	public List<Map<String, Object>> queryMatSuoOutStoreDetailToFimPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
		
		//是否按库房结账
	    Object isAccByStore = MyConfig.getSysPara("04045");

		if(isAccByStore==null || "0".equals(isAccByStore.toString())){
			//整体结账
			Map<String,Object> ClosingYearMonth = matSuoOutStoreDetailToFimMapper.queryClosingYearMonth(entityMap);
			if(!"1".equals(ClosingYearMonth.get("mat_flag").toString())){
				return null;
			}else{
				entityMap.put("begin_date",ClosingYearMonth.get("begin_date").toString().substring(0,10));
				entityMap.put("end_date",ClosingYearMonth.get("end_date").toString().substring(0,10));
			}
			 
		}else{
			//按仓库结账
			int isSetAcc = matSupInStoreDetailToFimMapper.hasSetAccount(entityMap);//判断结账表中是否有结账记录
			if(isSetAcc!=0){
				String year=entityMap.get("begin_year").toString();
				String month=entityMap.get("begin_month").toString();
				try {
					String firstDay = DateUtil.getMinMonthDate(year,month);
					String lastDay=DateUtil.getMaxMonthDate(year,month);
					
					entityMap.put("begin_date",year+"-"+month+"-"+firstDay);
					entityMap.put("end_date",year+"-"+month+"-"+lastDay);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				return null;
			}
		}
		
		 
			
			
	    entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		List<Map<String, Object>> list = matSuoOutStoreDetailToFimMapper.queryMatSuoOutStoreDetailToFim(entityMap);
		List<Map<String,Object>>overloadinglist = new  ArrayList<Map<String,Object>>();
		LinkedHashMap<String,Map<String,Object>> returnDataMap=new LinkedHashMap<String, Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			
			HashMap<String, Object> typeToMoneymap = new HashMap<String,Object>();
			//迭代list获取所有数据，
			Map<String,Object>map=list.get(i);
			//将fim_type_code的值转换为匹配页面display属性，将科室的钱数放在value上
			typeToMoneymap.put((String) map.get("fim_type_code"),map.get("money"));
			/**
			 * 判断返回数据的科室是否为单条数据，如果为单条数据时，
			 * 将总价直接放入返回的map中，如果为多条数据时对查询出金额进行运算，
			 * 第一条数据会走else 将当前金额赋值给amount_money 如果有多条数据再取出最新的money与总金额相加
			 */
			if(returnDataMap.containsKey(map.get("dept_code")+","+map.get("bus_type_code")))
			{
				map.putAll(typeToMoneymap);
				//根据部门編碼取出取出之前存入（或运算后）的总金额
				Map sumMoney=returnDataMap.get(map.get("dept_code")+","+map.get("bus_type_code"));
				//总金额
				Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
				//最新一条数据的金额
				Double count2= Double.parseDouble(map.get("money").toString());
				//两条数据相加修改amount_money
				sumMoney.put("amount_money", count+count2);
				//sumMoney.putAll(typeToMoneymap);
				if(map.get("fim_type_code").equals(sumMoney.get("fim_type_code"))){
					//取出当前数据物资类别所对应的钱
					Double typeMoney=Double.parseDouble(map.get(map.get("fim_type_code")).toString());
					//再取出之前存好的钱，进行运算
					Double oldtypeMoney=Double.parseDouble(sumMoney.get(sumMoney.get("fim_type_code")).toString());
					sumMoney.put(map.get("fim_type_code").toString(),typeMoney+oldtypeMoney);
					}else{
						sumMoney.put(map.get("fim_type_code").toString(),map.get("money"));
						sumMoney.put("fim_type_code", map.get("fim_type_code"));
					}
				//将运算后的数据从新封装
				returnDataMap.put((String) map.get("dept_code")+","+map.get("bus_type_code"), sumMoney);
			}else{
				//将转换后的数据填充到通过sql语句查询出来的map中
				 map.putAll(typeToMoneymap);
				 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
				 returnDataMap.put((String) map.get("dept_code")+","+map.get("bus_type_code"), map);
				 
				 Map sumMoney= returnDataMap.get(map.get("dept_code")+","+map.get("bus_type_code"));
				//取出当前部门的金额填入到amount_money中
				 sumMoney.put("amount_money",map.get("money"));
				 sumMoney.put(map.get("fim_type_code"),map.get(map.get("fim_type_code")));
				 sumMoney.put("fim_type_code", map.get("fim_type_code"));
				 returnDataMap.put((String) map.get("dept_code")+","+map.get("bus_type_code"), sumMoney);
			}
			//

			
		}
		for (String  j : returnDataMap.keySet()) {
			
			overloadinglist.add(returnDataMap.get(j));
		}
		return overloadinglist;
}

	/**
	 * 查询发生了出库业务的物资材料编码,名称
	 * @param mapVo
	 * @return
	 * @throws ParseException 
	 */
	@Override
	public String queryOccurOutMatFimTypeDictForHead(Map<String, Object> entityMap) throws ParseException {
		//是否按库房结账
	    Object isAccByStore = MyConfig.getSysPara("04045");

		if(isAccByStore==null || "0".equals(isAccByStore.toString())){
			//整体结账
			Map<String,Object> ClosingYearMonth = matSuoOutStoreDetailToFimMapper.queryClosingYearMonth(entityMap);
			if(!"1".equals(ClosingYearMonth.get("mat_flag").toString())){
				return "{\"error\":\"当月未结账\"}";
			}else{
				entityMap.put("begin_date",ClosingYearMonth.get("begin_date").toString().substring(0,10));
				entityMap.put("end_date",ClosingYearMonth.get("end_date").toString().substring(0,10));
			}
			 
		}else{
			//按仓库结账
			int isSetAcc = matSupInStoreDetailToFimMapper.hasSetAccount(entityMap);//判断结账表中是否有结账记录
			if(isSetAcc!=0){
				String year=entityMap.get("begin_year").toString();
				String month=entityMap.get("begin_month").toString();
				String firstDay=DateUtil.getMinMonthDate(year,month);
				String lastDay=DateUtil.getMaxMonthDate(year,month);

				entityMap.put("begin_date",year+"-"+month+"-"+firstDay);
				entityMap.put("end_date",year+"-"+month+"-"+lastDay);
			}else{
				return "{\"error\":\"当月未结账\"}";
			}
		}
		return JSON.toJSONString(matSuoOutStoreDetailToFimMapper.queryOccurOutMatFimTypeDictForHead(entityMap));
	}

 
}
