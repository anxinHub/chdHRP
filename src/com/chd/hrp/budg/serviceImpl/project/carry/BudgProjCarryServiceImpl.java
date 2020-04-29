/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.carry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.project.begin.BudgProjYearMapper;
import com.chd.hrp.budg.dao.project.carry.BudgProjCarryMapper;
import com.chd.hrp.budg.entity.BudgProjCarry;
import com.chd.hrp.budg.service.project.carry.BudgProjCarryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 项目预算年末结转
 * @Table:
 * BUDG_PROJ_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgProjCarryService")
public class BudgProjCarryServiceImpl implements BudgProjCarryService {

	private static Logger logger = Logger.getLogger(BudgProjCarryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgProjCarryMapper")
	private final BudgProjCarryMapper budgProjCarryMapper = null;
	//引入DAO操作
	@Resource(name = "budgProjYearMapper")
	private final BudgProjYearMapper budgProjYearMapper = null;
    

	@Override
	public String queryCarryYear(Map<String, Object> entityMap) {
		// TODO Auto-generated method stub
		return budgProjCarryMapper.queryCarryYear(entityMap);
	}
	/**
	 * 年度项目结转，结转时对BUDG_PROJ_YEAR和BUDG_PROJ_DETAIL_YEAR 表进行同步操作。
	 * 同时更新BUDG_PROJ_CARRY 中是否结转状态
	 */
	@Override
	public String carryBudgProjCarry(Map<String, Object> entityMap) {
		
		
		try {
			//查询出带结转年度的所有项目  依次作数据结转操作
			List<Map<String,Object>>awaitCarryData=budgProjCarryMapper.queryBudgProjCarryByYear(entityMap);
			for (int i = 0; i < awaitCarryData.size(); i++) {
				
				Map<String,Object> mapVo =awaitCarryData.get(i);
				int budg_year=Integer.parseInt(mapVo.get("budg_year").toString())+1;
				mapVo.put("budg_year",String.valueOf(budg_year));
				//根据年度，资金来源，查询出该项目的数据
				//查询出带结转年度的下一年是否有数据，如果存在数据，则 对期初数据，累计数据，余额数据进行更新操作
				Map<String,Object> budgProjCarry =budgProjCarryMapper.queryByCode(mapVo);
				
				if(budgProjCarry==null){
					Map<String, Object> saveMap= new HashMap<String, Object>();
					
					saveMap.putAll(mapVo);
					saveMap.put("b_budg_amount", mapVo.get("t_budg_amount"));//期初预算金额
					
					saveMap.put("b_in_amount", mapVo.get("t_in_amount"));//期初到账金额
					
					saveMap.put("b_cost_amount", mapVo.get("t_cost_amount"));//期初支出金额
					
					saveMap.put("b_remain_amoun", mapVo.get("remain_amount"));//期初预算余额
					
					saveMap.put("b_usable_amount", mapVo.get("usable_amount"));//期初可用余额,这五条数据是继承上年度的年尾数据
					
					saveMap.put("budg_amount", 0);//本期预算金额
					
					saveMap.put("cost_amount",0);//本期支出金额
					
					saveMap.put("in_amount", 0);//本期到账金额
					//预算金额累计 = 期初预算金额+本期预算金额
					saveMap.put("t_budg_amount", Double.parseDouble(saveMap.get("b_budg_amount").toString())+Double.parseDouble(saveMap.get("budg_amount").toString()));
					//到账金额累计 = 期初到账金额+本期到账金额
					saveMap.put("t_in_amount", Double.parseDouble(saveMap.get("b_in_amount").toString())+Double.parseDouble(saveMap.get("in_amount").toString()));
					//支出金额累计 = 期初支出金额 +本期支出金额
					saveMap.put("t_cost_amount", Double.parseDouble(saveMap.get("b_cost_amount").toString())+Double.parseDouble(saveMap.get("cost_amount").toString()));
					//预算余额 =预算金额累计-支出金额累计
					saveMap.put("remain_amount", Double.parseDouble(saveMap.get("t_budg_amount").toString())-Double.parseDouble(saveMap.get("t_cost_amount").toString()));
					//可用余额=到账金额累计-支出金额累计
					saveMap.put("usable_amount", Double.parseDouble(saveMap.get("t_in_amount").toString())-Double.parseDouble(saveMap.get("t_cost_amount").toString()));
					if(Double.parseDouble(saveMap.get("budg_amount").toString())+Double.parseDouble(saveMap.get("b_remain_amoun").toString())>0){
					//执行进度=本期支出金额 / （本期预算金额+期初预算余额）
					saveMap.put("rate", Double.parseDouble(saveMap.get("cost_amount").toString())/(Double.parseDouble(saveMap.get("budg_amount").toString())+Double.parseDouble(saveMap.get("b_remain_amoun").toString())));
					}else{
					saveMap.put("rate",0.0);
					}
					if(Double.parseDouble(String.valueOf(saveMap.get("t_budg_amount")))>0){
					//执行进度累计=支出金额累计 / 预算金额累计
					saveMap.put("t_rate", Double.parseDouble(saveMap.get("t_cost_amount").toString())/Double.parseDouble(saveMap.get("t_budg_amount").toString()));
					}else{
						saveMap.put("t_rate",0.0);
					}
					budgProjCarryMapper.addBudgProjYear(saveMap);
				}else{
					Map<String, Object> saveMap= new HashMap<String, Object>();
					
					saveMap.putAll(mapVo);
					
					saveMap.put("b_budg_amount", mapVo.get("t_budg_amount"));//期初预算金额
					
					saveMap.put("b_in_amount", mapVo.get("t_in_amount"));//期初到账金额
					
					saveMap.put("b_cost_amount", mapVo.get("t_cost_amount"));//期初支出金额
					
					saveMap.put("b_remain_amoun", mapVo.get("remain_amount"));//期初预算余额
					
					saveMap.put("b_usable_amount", mapVo.get("usable_amount"));//期初可用余额,这五条数据是继承上年度的年尾数据
					//预算金额累计 = 期初预算金额+本期预算金额
					saveMap.put("t_budg_amount", Double.parseDouble(saveMap.get("b_budg_amount").toString())+Double.parseDouble(saveMap.get("budg_amount").toString()));
					//到账金额累计 = 期初到账金额+本期到账金额
					saveMap.put("t_in_amount", Double.parseDouble(saveMap.get("b_in_amount").toString())+Double.parseDouble(saveMap.get("in_amount").toString()));
					//支出金额累计 = 期初支出金额 +本期支出金额
					saveMap.put("t_cost_amount", Double.parseDouble(saveMap.get("b_cost_amount").toString())+Double.parseDouble(saveMap.get("cost_amount").toString()));
					//预算余额 =预算金额累计-支出金额累计
					saveMap.put("remain_amount", Double.parseDouble(saveMap.get("t_budg_amount").toString())-Double.parseDouble(saveMap.get("t_cost_amount").toString()));
					//可用余额=到账金额累计-支出金额累计
					saveMap.put("usable_amount", Double.parseDouble(saveMap.get("t_in_amount").toString())-Double.parseDouble(saveMap.get("t_cost_amount").toString()));
					if(Double.parseDouble(saveMap.get("budg_amount").toString())+Double.parseDouble(saveMap.get("b_remain_amoun").toString())>0){
					//执行进度=本期支出金额 / （本期预算金额+期初预算余额）
					saveMap.put("rate", Double.parseDouble(saveMap.get("cost_amount").toString())/(Double.parseDouble(saveMap.get("budg_amount").toString())+Double.parseDouble(saveMap.get("b_remain_amoun").toString())));
					}else{
					saveMap.put("rate",0.0);
					}
					if(Double.parseDouble(String.valueOf(saveMap.get("t_budg_amount")))>0){
					//执行进度累计=支出金额累计 / 预算金额累计
					saveMap.put("t_rate", Double.parseDouble(saveMap.get("t_cost_amount").toString())/Double.parseDouble(saveMap.get("t_budg_amount").toString()));
					}else{
						saveMap.put("t_rate",0.0);
					}					
					budgProjCarryMapper.updateBudgProjYear(saveMap);
				}
			}
			List<Map<String,Object>>budgProjDetailYearData=budgProjCarryMapper.querybudgProjDetailYearByYear(entityMap);
			for (Map<String, Object> Detailmap : budgProjDetailYearData) {
				int budg_year=Integer.parseInt(Detailmap.get("budg_year").toString())+1;
				Detailmap.put("budg_year",String.valueOf(budg_year));
				Map<String,Object> budgProjCarryDict =budgProjCarryMapper.queryDetaiByCode(Detailmap);
				if(budgProjCarryDict==null){
					Map<String, Object> saveMap= new HashMap<String, Object>();
					saveMap.putAll(Detailmap);
					saveMap.put("b_budg_amount", Detailmap.get("y_budg_amount"));//期初预算金额
					
					saveMap.put("b_cost_amount", Detailmap.get("y_cost_amount"));//期初支出金额
					
					saveMap.put("b_remain_amoun", Detailmap.get("remain_amoun"));//期初预算余额
					
					saveMap.put("budg_amount", 0);//本期预算金额
					
					saveMap.put("cost_amount",0);//本期支出金额
					
					//预算金额累计 = 期初预算金额+本期预算金额
					saveMap.put("y_budg_amount", Double.parseDouble(saveMap.get("b_budg_amount").toString())+Double.parseDouble(saveMap.get("budg_amount").toString()));
					//支出金额累计 = 期初支出金额 +本期支出金额
					saveMap.put("y_cost_amount", Double.parseDouble(saveMap.get("b_cost_amount").toString())+Double.parseDouble(saveMap.get("cost_amount").toString()));
					//预算余额 =预算金额累计-支出金额累计
					saveMap.put("remain_amount", Double.parseDouble(saveMap.get("y_budg_amount").toString())-Double.parseDouble(saveMap.get("y_cost_amount").toString()));
					if(Double.parseDouble(saveMap.get("budg_amount").toString())+Double.parseDouble(saveMap.get("b_remain_amoun").toString())>0){
					// 执行进度=本期支出金额 / （本期预算金额+期初预算余额）
					saveMap.put("rate", Double.parseDouble(saveMap.get("cost_amount").toString())/(Double.parseDouble(saveMap.get("budg_amount").toString())+Double.parseDouble(saveMap.get("b_remain_amoun").toString())));
					}else{
						saveMap.put("rate",0.0);
					}
					if(Double.parseDouble(String.valueOf(saveMap.get("y_budg_amount")))>0){
					// 执行进度累计=支出金额累计 / 预算金额累计
					saveMap.put("t_rate", Double.parseDouble(saveMap.get("y_cost_amount").toString())/Double.parseDouble(saveMap.get("y_budg_amount").toString()));
					}else{
						saveMap.put("t_rate",0.0);
					}

					budgProjCarryMapper.addBudgProjDetaiYear(saveMap);
				}else{
					Map<String, Object> saveMap= new HashMap<String, Object>();
					
					saveMap.putAll(Detailmap);
					
					saveMap.put("b_budg_amount", Detailmap.get("y_budg_amount"));//期初预算金额
					
					
					saveMap.put("b_cost_amount", Detailmap.get("y_cost_amount"));//期初支出金额
					
					saveMap.put("b_remain_amoun", Detailmap.get("remain_amoun"));//期初预算余额
					
					saveMap.put("b_usable_amount", Detailmap.get("usable_amount"));//期初可用余额,这五条数据是继承上年度的年尾数据
					//预算金额累计 = 期初预算金额+本期预算金额
					saveMap.put("y_budg_amount", Double.parseDouble(saveMap.get("b_budg_amount").toString())+Double.parseDouble(saveMap.get("budg_amount").toString()));
					//支出金额累计 = 期初支出金额 +本期支出金额
					saveMap.put("y_cost_amount", Double.parseDouble(saveMap.get("b_cost_amount").toString())+Double.parseDouble(saveMap.get("cost_amount").toString()));
					//预算余额 =预算金额累计-支出金额累计
					saveMap.put("remain_amount", Double.parseDouble(saveMap.get("y_budg_amount").toString())-Double.parseDouble(saveMap.get("y_cost_amount").toString()));
					if(Double.parseDouble(saveMap.get("budg_amount").toString())+Double.parseDouble(saveMap.get("b_remain_amoun").toString())>0){

					//执行进度=可用余额 / 预算余额
					saveMap.put("rate", Double.parseDouble(saveMap.get("cost_amount").toString())/(Double.parseDouble(saveMap.get("budg_amount").toString())+Double.parseDouble(saveMap.get("b_remain_amoun").toString())));
					}else{
						saveMap.put("rate",0.0);
					}
					if(Double.parseDouble(String.valueOf(saveMap.get("y_budg_amount")))>0){
					//执行进度累计=支出金额累计 / 预算余额
					saveMap.put("t_rate", Double.parseDouble(saveMap.get("y_cost_amount").toString())/Double.parseDouble(saveMap.get("y_budg_amount").toString()));
					}else{
						saveMap.put("t_rate",0.0);
					}
					budgProjCarryMapper.updateBudgProjDetaiYear(saveMap);
				}
			}
			
			String year =budgProjCarryMapper.queryCarryYearByYear(entityMap);
			if(year==null){
				budgProjCarryMapper.addcarryBudgProj(entityMap);
			}else{
				budgProjCarryMapper.updatecarryBudgProj(entityMap);
			}
			return  "{\"msg\":\"结转成功.\",\"state\":\"true\",\"date\":\""+entityMap.get("year")+"\"}";
			
		} catch (NumberFormatException e) {
			throw   e;
		}  
		
	}

	/**
	 * 反结：将所选年度的期初数据置零，重新计算累计与余额
	 */
	@Override
	public String reCarryBudgProjCarry(Map<String, Object> mapVo) {
		mapVo.put("budg_year", Integer.parseInt( mapVo.get("year").toString())+1);
		
		List<Map<String,Object>>alreadyCarryData=budgProjCarryMapper.queryBudgProjCarryByYear(mapVo);
		for (Map<String, Object> map : alreadyCarryData) {
			mapVo.put("proj_id", map.get("proj_id"));
			mapVo.put("source_id",map.get("source_id"));
			Map<String,Object> budgProjCarry =budgProjCarryMapper.queryByCode(mapVo);
			map.putAll(budgProjCarry);
			map.put("b_budg_amount", 0);//期初预算金额
			
			map.put("b_in_amount",0);//期初到账金额
			
			map.put("b_cost_amount", 0);//期初支出金额
			
			map.put("b_remain_amoun",0);//期初预算余额
			
			map.put("b_usable_amount",0);//期初可用余额,这五条数据是继承上年度的年尾数据
			//预算金额累计 = 期初预算金额+本期预算金额
			map.put("t_budg_amount", map.get("budg_amount"));
			//到账金额累计 = 期初到账金额+本期到账金额
			map.put("t_in_amount", map.get("in_amount"));
			//支出金额累计 = 期初支出金额 +本期支出金额
			map.put("t_cost_amount", map.get("cost_amount"));
			//预算余额 =预算金额累计-支出金额累计
			map.put("remain_amount", Double.parseDouble(map.get("t_budg_amount").toString())-Double.parseDouble(map.get("t_cost_amount").toString()));
			//可用余额=到账金额累计-支出金额累计
			map.put("usable_amount", Double.parseDouble(map.get("t_in_amount").toString())-Double.parseDouble(map.get("t_cost_amount").toString()));
			if(Double.parseDouble(map.get("budg_amount").toString())+Double.parseDouble(map.get("b_remain_amoun").toString())>0){
			//执行进度=可用余额 / 预算余额
			map.put("rate", Double.parseDouble(map.get("cost_amount").toString())/(Double.parseDouble(map.get("budg_amount").toString())+Double.parseDouble(map.get("b_remain_amoun").toString())));
			}else{
				map.put("rate",0.0);
			}
			if(Double.parseDouble(String.valueOf(map.get("t_budg_amount")))>0){
			//执行进度累计=可用余额 / 预算余额
			map.put("t_rate", Double.parseDouble(map.get("t_cost_amount").toString())/Double.parseDouble(map.get("t_budg_amount").toString()));
			}else{
				map.put("t_rate",0.0);
			}
			budgProjCarryMapper.updateBudgProjYear(map);
		}
		List<Map<String,Object>>budgProjDetailYearData=budgProjCarryMapper.querybudgProjDetailYearByYear(mapVo);
		for (Map<String, Object> Detailmap : budgProjDetailYearData) {
			int budg_year=Integer.parseInt(Detailmap.get("budg_year").toString())+1;
			Detailmap.put("budg_year",String.valueOf(budg_year));
			Detailmap.put("source_id",Detailmap.get("source_id"));
			Map<String,Object> budgProjCarryDict =budgProjCarryMapper.queryDetaiByCode(Detailmap);
			Detailmap.putAll(budgProjCarryDict);
			Detailmap.put("b_budg_amount", 0);//期初预算金额
			
			Detailmap.put("b_cost_amount", 0);//期初支出金额
			
			Detailmap.put("b_remain_amoun",0);//期初预算余额
			//预算金额累计 = 期初预算金额+本期预算金额
			Detailmap.put("y_budg_amount", Double.parseDouble(Detailmap.get("b_budg_amount").toString())+Double.parseDouble(Detailmap.get("budg_amount").toString()));
			//支出金额累计 = 期初支出金额 +本期支出金额
			Detailmap.put("y_cost_amount", Double.parseDouble(Detailmap.get("b_cost_amount").toString())+Double.parseDouble(Detailmap.get("cost_amount").toString()));
			//预算余额 =预算金额累计-支出金额累计
			Detailmap.put("remain_amount", Double.parseDouble(Detailmap.get("y_budg_amount").toString())-Double.parseDouble(Detailmap.get("y_cost_amount").toString()));
			if(Double.parseDouble(Detailmap.get("budg_amount").toString())+Double.parseDouble(Detailmap.get("b_remain_amoun").toString())>0){

			//执行进度=可用余额 / 预算余额
			Detailmap.put("rate", Double.parseDouble(Detailmap.get("cost_amount").toString())/(Double.parseDouble(Detailmap.get("budg_amount").toString())+Double.parseDouble(Detailmap.get("b_remain_amoun").toString())));
			}else{
				Detailmap.put("rate",0.0);
			}
			if(Double.parseDouble(String.valueOf(Detailmap.get("y_budg_amount")))>0){
			//执行进度累计=支出金额累计 / 预算余额
			Detailmap.put("t_rate", Double.parseDouble(Detailmap.get("y_cost_amount").toString())/Double.parseDouble(Detailmap.get("y_budg_amount").toString()));
			}else{
				Detailmap.put("t_rate",0.0);
			}
			budgProjCarryMapper.updateBudgProjDetaiYear(Detailmap);
		}
		
		
		//更新结转年度状态，是否已结转
		budgProjCarryMapper.updatecarryBudgProj(mapVo);
		String budg_year=mapVo.get("year").toString();
		mapVo.put("year", Integer.parseInt( mapVo.get("year").toString())-1);
		String year =budgProjCarryMapper.queryCarryYearByYear(mapVo);
			if(year==null){
				year="";
			}
			
		return  "{\"msg\":\"结转成功.\",\"state\":\"true\",\"date\":\""+year+","+budg_year+"\"}";
	}
	
}
