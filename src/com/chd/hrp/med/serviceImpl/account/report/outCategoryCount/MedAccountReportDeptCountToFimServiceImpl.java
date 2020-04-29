package com.chd.hrp.med.serviceImpl.account.report.outCategoryCount;

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

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.report.outCategoryCount.MedAccountReportDeptCountToFimMapper;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportDeptCountService;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportDeptCountToFimService;
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

@Service("medAccountReportDeptCountToFimService")
public class MedAccountReportDeptCountToFimServiceImpl implements MedAccountReportDeptCountToFimService {
	private static Logger logger = Logger.getLogger(MedAccountReportDeptCountToFimServiceImpl.class);
	
	@Resource(name = "medAccountReportDeptCountToFimMapper")
	private final MedAccountReportDeptCountToFimMapper medAccountReportDeptCountToFimMapper = null;
	
	@Override
	public String queryMedAccountReportDeptCount(Map<String, Object> entityMap) throws DataAccessException {
		
				List<Map<String, Object>> list = medAccountReportDeptCountToFimMapper.queryMedAccountReportDeptCount(entityMap);
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
					if(returnDataMap.containsKey(map.get("dept_code")))
					{
						//根据部门編碼取出取出之前存入（或运算后）的总金额
						Map sumMoney=returnDataMap.get(map.get("dept_code"));
						//总金额
						Double count = Double.parseDouble(sumMoney.get("amount_money").toString());
						//最新一条数据的金额
						Double count2= Double.parseDouble(map.get("money").toString());
						//两条数据相加修改amount_money
						sumMoney.put("amount_money", count+count2);
						sumMoney.putAll(typeToMoneymap);
						//将运算后的数据从新封装
						returnDataMap.put((String) map.get("dept_code"), sumMoney);
							
						 
					}else{
						//将转换后的数据填充到通过sql语句查询出来的map中
						 map.putAll(typeToMoneymap);
						 //重构map  将当前的部门编码取出，存入一个新的map中，在进入下次循环时判断下一条数据是否存在，如存在就对amount_money做运算
						 returnDataMap.put((String) map.get("dept_code"), map);
						 
						 Map sumMoney= returnDataMap.get(map.get("dept_code"));
						//取出当前部门的金额填入到amount_money中
						 sumMoney.put("amount_money",map.get("money"));
						 returnDataMap.put((String) map.get("dept_code"), sumMoney);
					}
					//

					
				}
				for (String  j : returnDataMap.keySet()) {
					
					overloadinglist.add(returnDataMap.get(j));
				}
				return ChdJson.toJsonUpper(overloadinglist);
	}

 
}
