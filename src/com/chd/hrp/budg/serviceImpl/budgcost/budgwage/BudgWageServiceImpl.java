/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcost.budgwage;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.budgcost.budgwage.BudgWageMapper;
import com.chd.hrp.budg.entity.BudgWageChange;
import com.chd.hrp.budg.service.budgcost.budgwage.BudgWageService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_WAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgWageService")
public class BudgWageServiceImpl implements BudgWageService {

	private static Logger logger = Logger.getLogger(BudgWageServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgWageMapper")
	private final BudgWageMapper budgWageMapper = null;
    
	/**
	 * @Description 
	 * 添加工资变动<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		//定义addList 接收最终数据  添加进数据库
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		//定义countList 接收计算出的各科室  各职工类别  各工资项目 总支出 数据  
		List<Map<String,Object>> countList = new ArrayList<Map<String,Object>>();
		
		//定义list 接收重新封装数据的mapVo集合
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		//定义map  封装条件查询上年12月份各科室人数
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("group_id",entityMap.get("group_id") );
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("year", Integer.parseInt(entityMap.get("budg_year").toString())-1);
		//map.put("month", 12);
		
		try {
			//根据map中的条件   从科室职工工资总表中   查询上年最大月份 各科室 各工资类别 下  人数  作为人员数量 的 基数
			List<Map<String, Object>> baseList = budgWageMapper.queryLYEmpCountFromTotal(map);
			//遍历数据  结合增员计划 和减员计划  确定预算年度下  各科室各月份人数
			for (Map<String, Object> mapVo : baseList) {
				
				//添加参数 预算年度
				mapVo.put("budg_year", entityMap.get("budg_year"));
				
				//添加参数上年年度   用于查询上年各科室 各工资项目平均工资使用
				mapVo.put("last_year", map.get("year"));
				
				//获取每条数据中  该科室类别下  职工人数  作为基数
				int empCount = Integer.parseInt(mapVo.get("empCount").toString());
				//根据根据科室  职工类别   查询该科室职工类别 该月份下是否有减员计划  并查询出减员人员id
				List<Map<String, Object>> cutEmpIdList = budgWageMapper.queryCutPlan(mapVo);
				//根据科室  职工类别  查询该科室职工类别是否有增员计划  
				Map<String, Object> addPlanMap = budgWageMapper.queryAddPlan(mapVo);
				for (int i = 1; i <= 12; i++) {
					//定义newMap  封装条件
					Map<String,Object> newMap = new HashMap<String, Object>();
					
					newMap.putAll(mapVo);
					
					if(i<10){
						newMap.put("month", "0"+i);
					}else{
						newMap.put("month", String.valueOf(i));
					}
					
					if(addPlanMap != null){
						//获取增加人数
						int add = Integer.parseInt(addPlanMap.get("add_num").toString());
						//如果当前月份与增员计划到岗日期月份相同  则职工人数加上增员人数
						if(newMap.get("month").toString().equals(addPlanMap.get("in_month").toString())){
							empCount += add;
						}
					}
					//遍历查询出的减员计划  匹配月份 如果离职月份与当前月份相同  职工人数减去减员人员
					for (Map<String, Object> cutMap : cutEmpIdList) {
						
						//根据当前职工id  反查工资总表中各科室是否带有该员工 如果带有该员工   且当前月份与离职月份相同    减去
						map.put("emp_id", cutMap.get("emp_id"));
						map.put("dept_id", newMap.get("dept_id"));
						map.put("emp_type_code", newMap.get("emp_type_code"));
						
						if(newMap.get("month").toString().equals(cutMap.get("out_month").toString())){
							int count = budgWageMapper.queryEmpExists(map);
							if(count > 0 ){
								--empCount;
							}
						}
						
					}
					newMap.put("empCount", empCount);
					list.add(newMap);
				}
			}
			
			
			for (Map<String, Object> empCountMap : list) {
				//查询各科室各职工类别下各工资项目的平均工资
				List<Map<String, Object>> typeWageList = budgWageMapper.queryEmpTypeWage(empCountMap);
				
				//获取每条数据中  该科室 该职工类别下  职工人数
				int empCount = Integer.parseInt(empCountMap.get("empCount").toString());
				
				//遍历typeWageList  获取单独工资项目的平均工资
				for (Map<String, Object> typeWageMap : typeWageList) {
					typeWageMap.putAll(entityMap);
					
					//定义amountMap  封装条件    获取各科室 各职工类别  每月总支出
					Map<String,Object> amountMap = new HashMap<String, Object>();
					amountMap.putAll(empCountMap);
					
					//获取平均工资
					Double avgAmount = Double.parseDouble(typeWageMap.get("amount").toString());
					
					//根据 职工类别  工资项目   查询调整数据  
					Map<String, Object> typeWageAdjMap = budgWageMapper.queryTypeWageAdj(typeWageMap);
					//如果查询出的typeWageAdjMap 不为空  则匹配月份数据  计算计划平均工资
					if(typeWageAdjMap != null){
						//获取调整比例
						int adj_rate = Integer.parseInt(typeWageAdjMap.get("adj_rate").toString());
						//获取调整金额
						Double adj_amount = Double.parseDouble(typeWageAdjMap.get("adj_amount").toString());
						
						if(empCountMap.get("month").toString().equals(typeWageAdjMap.get("adj_month").toString()) || 
								Integer.parseInt(empCountMap.get("month").toString()) > Integer.parseInt((typeWageAdjMap.get("adj_month").toString()))){
							if(adj_rate>0){
								//计算 计划平均工资(计划平均工资为平均工资*(1+分解比例))
								avgAmount =Double.parseDouble(String.valueOf(avgAmount))*(100+adj_rate)/100;//除以100 按百分比计算
								
								BigDecimal countValue = new BigDecimal(String.valueOf(avgAmount));
								avgAmount = countValue.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
								
							}else if(adj_amount > 0){
								avgAmount = avgAmount + adj_amount;
							}
						}
					}
					
					//计算当前月份 该科室  该职工类别   该工资项目   总支出
					Double value = empCount * avgAmount;
					BigDecimal countValue = new BigDecimal(String.valueOf(value));
					double amount = countValue.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					//将当前工资项目  科室工资项目总支出   放入mapVo 并存入list
					amountMap.put("wage_item_code", typeWageMap.get("wage_item_code"));
					amountMap.put("amount", amount);
					
					countList.add(amountMap);
				}
			}
			
			//根据月份,科室,工资项目  汇总数据
			
			Map<String, Map<String,Object>> resultMap = new HashMap<String, Map<String,Object>>();
			
			for (Map<String, Object> countMap : countList) {
				String monthOne = countMap.get("month").toString();
				String deptId = countMap.get("dept_id").toString();
				String wageItemCode = countMap.get("wage_item_code").toString();
				
				Double count_value = Double.parseDouble(countMap.get("amount").toString());
				
				String key = monthOne + deptId + wageItemCode;
				
				if(resultMap.get(key) != null){
					//如果key存在  则更新计算值
					count_value += Double.parseDouble(resultMap.get(key).get("count_value").toString());
					
					resultMap.get(key).put("count_value", count_value);
					
				}else{
					//如果不存在  则将key 对应数据 放入resultMap 之中  并将计算值字段放入resultMap 中
					resultMap.put(key,countMap);
					resultMap.get(key).put("count_value", count_value);
					
				}
			}
			
			for(String dataKey : resultMap.keySet()){
				
				//表中其余字段处理
				resultMap.get(dataKey).put("cost_budg", resultMap.get(dataKey).get("count_value"));//支出预算 
				resultMap.get(dataKey).put("adj_rate", "");//调整比例
				resultMap.get(dataKey).put("remark", "");//说明
				
				addList.add(resultMap.get(dataKey));
			}
			
			budgWageMapper.delete(entityMap);
			addBatch(addList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
			//return "{\"error\":\"添加失败  方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加工资变动<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			for(int i= 0 ; i< entityList.size() ; i++){
				
				addList.add(entityList.get(i));
				
				if(i%1000 == 0 && i > 0){
					
					budgWageMapper.addBatch(addList);
					
					addList.clear();
					
				}else if ( i == (entityList.size()-1) && addList.size() > 0 ){
					
					budgWageMapper.addBatch(addList);
						
					addList.clear();
					
				}
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败!\"}") ;

		}
		
	}
	
		/**
	 * @Description 
	 * 更新工资变动<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgWageMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败  方法 update\"}") ;

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新工资变动<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgWageMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除工资变动<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgWageMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除工资变动<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgWageMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败! 方法 deleteBatch\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加工资变动<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象工资变动
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWageChange> list = (List<BudgWageChange>)budgWageMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgWageMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgWageMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集工资变动<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgWageChange> list = (List<BudgWageChange>)budgWageMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgWageChange> list = (List<BudgWageChange>)budgWageMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象工资变动<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgWageMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取工资变动<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWageChange
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgWageMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取工资变动<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWageChange>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgWageMapper.queryExists(entityMap);
	}
	
}
