/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgcash.BudgCarryMapper;
import com.chd.hrp.budg.entity.BudgCarry;
import com.chd.hrp.budg.entity.BudgWorkCarry;
import com.chd.hrp.budg.service.budgcash.BudgCarryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 资金预算结转
 * @Table:
 * BUDG_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgCarryService")
public class BudgCarryServiceImpl implements BudgCarryService,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BudgCarryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgCarryMapper")
	private final BudgCarryMapper budgCarryMapper = null;
    
 	/**
	 * 待结转月份查询
	 */
	@Override
	public List<Map<String, Object>> queryYearMonth(Map<String, Object> entityMap) throws DataAccessException {
	  return  budgCarryMapper.queryYearMonth(entityMap);
	}
	
	/**
	 * 已结转月份查询
	 */
	@Override
	public List<Map<String,Object>> queryYearMonthBefore(Map<String, Object> mapVo) throws DataAccessException {
		return budgCarryMapper.queryYearMonthBefore(mapVo);
	}
	
	/**
	 * 已结转月份查询
	 */
	@Override
	public String queryYearMonthStart(Map<String, Object> mapVo) throws DataAccessException {
		return budgCarryMapper.queryYearMonthStart(mapVo);
	}
	
	/**
	 * 结转
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String chargeBudgWork(Map<String,Object> mapVo) throws DataAccessException {
		
		try {
			//定义list  用于 更新重新计算后的存量预算数据
			List<Map<String,Object>> updatelist = new ArrayList<Map<String,Object>>();
			
			//拼装预算表中所需年份字段
			mapVo.put("budg_year", mapVo.get("year"));
			
			//定义map  用于更新存量预算  结转值 
			Map<String,Object> map  = new HashMap<String, Object>();
			//封装参数集合    主键 
			map.putAll(mapVo);
			
			//根据年度   月份  查询现金存量预算表 及 现金存量执行表中预算累计流入 和 累计流出数据  和 执行累计流入 和 累计流出  期末现金存量数据  
			Map<String,Object> cashMap = budgCarryMapper.queryBudgCash(mapVo);
			Map<String,Object> cashExeMap = budgCarryMapper.queryBudgCashExe(mapVo);
			
			if(cashMap ==null || cashExeMap==null ){
				return "{\"error\" : \"无该月的预算数据/执行数据不可结账!\"}";
			}
			
			//根据年度  月份 资金项目流向 查询 汇总现金流量预算表 及 现金流量执行表中 累计流入 和 累计流出数据   
			Map<String,Object> cashFlowMap = budgCarryMapper.queryBudgCashFlow(mapVo);
			Map<String,Object> cashFlowExeMap = budgCarryMapper.queryBudgCashFlowExe(mapVo);
			
			//比较预算数据  与执行数据中   各自流量与存量的累计流入和累计流出   不一致 则返回提示信息
		    Double cash_in = Double.valueOf(cashMap.get("cash_in").toString());
		    Double cash_out = Double.valueOf(cashMap.get("cash_out").toString());
		    
		    if (!cash_in.equals(Double.valueOf(cashFlowMap.get("cash_in").toString()))  
		    		|| !cash_out.equals(Double.valueOf(cashFlowMap.get("cash_out").toString()))){
		    	return "{\"error\" : \"该月现金存量预算与该月现金流量预算数据不匹配,请先计算该月现金存量预算!\"}";
		    }
		    
		    cash_in = Double.valueOf(cashExeMap.get("cash_in").toString());
		    cash_out = Double.valueOf(cashExeMap.get("cash_out").toString());
		    
		    if (!cash_in.equals(Double.valueOf(cashFlowExeMap.get("cash_in").toString())) 
		    		|| !cash_out.equals(Double.valueOf(cashFlowExeMap.get("cash_out").toString()))){
		    	return "{\"error\" : \"该月现金存量执行与该月现金流量执行数据不匹配,请先计算该月现金存量执行!\"}";
		    }
		    
		    //如果  数据全部 一致  则更新现金存量预算表中 该月结转值 为  该月现金存量执行表的期末值 
		    Double cash_carry = Double.valueOf(cashExeMap.get("cash_end").toString());
		    //数据封装入map  更新结转值
		    map.put("cash_carry", cash_carry);
		    budgCarryMapper.updateCashCarry(map);
		    
		    //判断结转值与 现金存量预算表中期末值是否一致  不一致 则重新计算现金存量预算表中后续月份预算数据
		    if(!cash_carry.equals(Double.valueOf(cashMap.get("cash_end").toString()))){
		    	
		    	//如果该结转月份是12月  则只需更新结转表中 结账状态   否则查询 数据  并重新计算后  更新存量预算表  和  该月结账状态 与下月结账状态
		    	if(!"12".equals(mapVo.get("month").toString())){
		    		updatelist = budgCarryMapper.queryMonthCashAddFromBudgCash(mapVo);
		    		
		    		Double cash_begain = 0.0;
		    		Double cash_add = 0.0;
		    		Double cash_end = cash_carry; //将结转值 赋给 期末值  便于滚动计算
		    		
		    		for (Map<String, Object> item : updatelist) {
		    			//将上月期末 赋给本月期初 
		    			cash_begain = cash_end;
		    			
						cash_add = Double.valueOf(item.get("cash_add").toString());
						
						cash_end = cash_begain + cash_add; 
						
						item.put("cash_begain", cash_begain);
						item.put("cash_end", cash_end);
		    			
					}
		    		//将重新计算后的 各月期初和期末值  更新入存量预算表中
		    		budgCarryMapper.updateBudgCash(updatelist);
		    	}
		    }
		    //更改结账表中  结账状态
		    mapVo.put("cash_flag", 1);
		    
		    //查询数据是否存在  存在更新 结账标识   不存在 插入数据
		    int count = budgCarryMapper.queryDataExists(mapVo);
		    
		    if(count > 0 ){
		    	
		    	budgCarryMapper.update(mapVo);
		    }else{
		    	
		    	budgCarryMapper.add(mapVo);
		    }
		    
		    return "{\"msg\":\"结转成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			 
			throw new SysException("{\"error\":\"结转失败.\"}");
		}
	}

	/**
	 * 反结
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String reChargeBudgWork(Map<String, Object> mapVo) throws DataAccessException {
		
		try{
			//定义list  用于 更新重新计算后的存量预算数据
			List<Map<String,Object>> updatelist = new ArrayList<Map<String,Object>>();
			
			//拼装预算表中所需年份字段
			mapVo.put("budg_year", mapVo.get("year"));
			
			//定义map  用于更新存量预算  结转值 
			Map<String,Object> map = new HashMap<String, Object>();
			
			//封装参数集合    主键 
			map.putAll(mapVo);
			
			 //数据封装入map  清空结转值
		    map.put("cash_carry", "");
		    budgCarryMapper.updateCashCarry(map);
		    
			//根据年度   月份  查询现金存量预算表 及 现金存量执行表中预算累计流入 和 累计流出数据  和 执行累计流入 和 累计流出  期末现金存量数据  
			Map<String,Object> cashMap = budgCarryMapper.queryBudgCash(mapVo);
			
			Double cashEnd = Double.valueOf(cashMap.get("cash_end").toString());
			
			//如果该结转月份是12月  则只需更新结转表中 结账状态   否则查询 数据  并重新计算后  更新存量预算表  和  该月结账状态 与下月结账状态
			if(!"12".equals(mapVo.get("month").toString())){
				updatelist = budgCarryMapper.queryMonthCashAddFromBudgCash(mapVo);
				
				Double cash_begain = 0.0;
				Double cash_add = 0.0;
				Double cash_end = cashEnd; //将结转值 赋给 期末值  便于滚动计算
				
				for (Map<String, Object> item : updatelist) {
					//将上月期末 赋给本月期初 
					cash_begain = cash_end;
					
					cash_add = Double.valueOf(item.get("cash_add").toString());
					
					cash_end = cash_begain + cash_add; 
					
					item.put("cash_begain", cash_begain);
					item.put("cash_end", cash_end);
					
				}
				//将重新计算后的 各月期初和期末值  更新入存量预算表中
				budgCarryMapper.updateBudgCash(updatelist);
			}
			
			//更改结账表中  结账状态
			mapVo.put("cash_flag", 0);
			
			budgCarryMapper.update(mapVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
				
		} catch (Exception e) {
		
			logger.error(e.getMessage(), e);
			 
			throw new SysException("{\"error\":\"操作失败.\"}");
		}
	}

}
	

