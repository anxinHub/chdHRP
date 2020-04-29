package com.chd.hrp.budg.serviceImpl.common;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.payable.base.BudgNoManagerMapper;
import com.chd.hrp.budg.dao.common.BudgNoRulesMapper;

@Service("budgNoRulesService")
public class BudgNoRulesServiceImpl {

	private static Logger logger = Logger.getLogger(BudgComTypeServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "budgNoRulesMapper")
	private final BudgNoRulesMapper budgNoRulesMapper = null;
	
	@Resource(name = "budgNoManagerMapper")
	private final BudgNoManagerMapper budgNoManagerMapper = null;
	
	/**
	 * 生成table_code表的下一个单号（使用于出入库业务）
	 * @param entityMap
	 * @return
	 */	
	public Map<String, Object> getBudgNextNo(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("table_code", entityMap.get("table_code"));
		
		//获取当前年份
		String yearMonth=DateUtil.dateToString(DateUtil.getCurrenDate(), "yyyy-MM");
		
		//获取单号生成规则
		Map<String, Object> ruleMap = budgNoRulesMapper.queryRule(map);
		
		if(ruleMap == null){
			return JSONObject.parseObject("{\"error\":\"没有该数据表的单号生成规则!\",\"state\":\"false\"}");
		}
		
		//判断规则是否包含年度
		if("1".equals(ruleMap.get("is_year").toString())){
			
			if("1".equals(ruleMap.get("is_budg_year").toString())){
				//是预算年度  则需手动传入年度值
				if(null == entityMap.get("budg_year")){
					return JSONObject.parseObject("{\"error\":\"预算年度未传值,请检查所传参数后操作!\",\"state\":\"false\"}");
				}else{
					//传入预算年度值  封装进map中  用作查询条件
					map.put("year", entityMap.get("budg_year"));
				}
			}else if("0".equals(ruleMap.get("is_budg_year").toString())){
				//不是预算年度   则年度取当前年度
				map.put("year", yearMonth.split("-")[0]);
			}
			
		}else{
			//不包含年度  则年度为空串
			map.put("year", "");
		}
		
		//包含月份  则将当前月份封装进map中  用作查询条件
		if("1".equals(ruleMap.get("is_month").toString())){
			map.put("month", yearMonth.split("-")[1]);
		}else{
			//不包含月份  则月份为空串
			//"" 在oracle默认认为null
			map.put("month", "");
		}
		
		//封装单号表中所需其他字段
		map.put("table_name", ruleMap.get("table_name"));
		map.put("prefixe", ruleMap.get("prefixe"));
		map.put("seq_no", ruleMap.get("seq_no"));
		
		//判断是否存在该业务流水号
		int flag = budgNoManagerMapper.queryIsExists(map);
		String max_no = "";
		if(flag == 0){
			//如不存在则流水码为1，并插入流水码表中
			max_no = "1";
			map.put("max_no", 1);
			budgNoManagerMapper.addBudgNoManager(map) ;
		}else{
			//更新该业务流水码+1
			budgNoManagerMapper.updateBudgNoManagerMaxNo(map);
			//取出该业务更新后的流水码
			max_no = budgNoManagerMapper.queryMaxCode(map);
		}
		
		int seq_no = Integer.parseInt(String.valueOf(ruleMap.get("seq_no")));
		//补流水码前缀0
		for (int i = max_no.length() ; i < seq_no; i++) {
			max_no = "0" + max_no;
		}
		//组装流水码
		String next_no = ruleMap.get("prefixe").toString() + map.get("year") + map.get("month") + max_no;	
		return JSONObject.parseObject("{\"noCode\":\""+next_no+"\",\"state\":\"true\"}");

	}

}
