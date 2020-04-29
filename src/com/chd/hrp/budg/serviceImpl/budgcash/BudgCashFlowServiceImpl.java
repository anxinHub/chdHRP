/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgcash;

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
import com.chd.hrp.budg.dao.budgcash.BudgCashFlowMapper;
import com.chd.hrp.budg.dao.budgcash.BudgCashMapper;
import com.chd.hrp.budg.entity.BudgCashFlow;
import com.chd.hrp.budg.service.budgcash.BudgCashFlowService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 现金流量预算
 * @Table:
 * BUDG_CASH_FLOW
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgCashFlowService")
public class BudgCashFlowServiceImpl implements BudgCashFlowService {

	private static Logger logger = Logger.getLogger(BudgCashFlowServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgCashFlowMapper")
	private final BudgCashFlowMapper budgCashFlowMapper = null;
	
	@Resource(name = "budgCashMapper")
	private final BudgCashMapper budgCashMapper = null;
    
	/**
	 * @Description 
	 * 添加现金流量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象现金流量预算
		BudgCashFlow budgCashFlow = queryByCode(entityMap);

		if (budgCashFlow != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgCashFlowMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加现金流量预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgCashFlowMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新现金流量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgCashFlowMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新现金流量预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgCashFlowMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除现金流量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgCashFlowMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除现金流量预算<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgCashFlowMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加现金流量预算<BR> 
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
		//判断是否存在对象现金流量预算
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgCashFlow> list = (List<BudgCashFlow>)budgCashFlowMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgCashFlowMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgCashFlowMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集现金流量预算<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgCashFlow> list = (List<BudgCashFlow>)budgCashFlowMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgCashFlow> list = (List<BudgCashFlow>)budgCashFlowMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象现金流量预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashFlowMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取现金流量预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgCashFlow
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashFlowMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取现金流量预算<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgCashFlow>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgCashFlowMapper.queryExists(entityMap);
	}
	
	/**
	 * 1 后台查询资金计划审核数据中未记账的数据   按年度  月份  项目 汇总 之后  插入或更新入现金流量预算表   budg_cash_flow
	 * 2 对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出、现金净增加额。计算后插入现金存量预算表  budg_cash
	 */
	@Override
	public String collectBudgCashFlow(Map<String,Object> entityMap)throws DataAccessException{
		
		//定义月份集合  用于封装月份参数  初始化
		List<Map<String, Object>> initList = new ArrayList<Map<String,Object>>();
		
		try {
			//查询结账表中已结账数据中  最大月份  
			List<Map<String, Object>> carryList = budgCashFlowMapper.queryMaxMonthFromBudgCarry(entityMap);
			
			int minMonth;
			//如果存在已结账的数据   则判断结账月份是否是12月
			if(carryList.size() > 0){
				minMonth = Integer.parseInt(carryList.get(0).get("month").toString());//已结账最大月份
				if(12 == minMonth){
					return "{\"error\":\"当前年度数据已全部结账,不可计算！\"}";
				}
				
				String mon = "";//结账月份的下一月  即 未结账月份的第一个月
				if(minMonth < 10){
					mon = "0" + (minMonth+1) ;
				}else{
					mon = "" + (minMonth+1);
				}
				//不是12月份  则封装参数  用于删除未结账月份数据
				entityMap.put("month",mon);
				//查询资金计划审核数据中未记账的数据   按年度  月份  项目 汇总
				List<Map<String, Object>> planDetList = budgCashFlowMapper.queryCashPlanDet(entityMap);
				
				if(planDetList == null){
					return "{\"error\":\"当前年度无资金计划数据或无已审核数据,请添加计划数据或审核已添加数据后操作！\"}";
				}
				//先删除流量表中当前年度未结账数据   然后添加
				budgCashFlowMapper.delete(entityMap);
				budgCashFlowMapper.addBatch(planDetList);
				
				//查询流量预算表中 数据最大月份和最小月份
				Map<String, Object> monthMap = budgCashFlowMapper.queryMaxMonthFromCashPlan(entityMap);
				int maxMonth = Integer.parseInt(String.valueOf(monthMap.get("maxMonth"))) ;
				
				//清除最大月 最小月键值对 
				monthMap.remove("minMonth");
				monthMap.remove("maxMonth");
				
				String month = "";
				for (int i = minMonth+1 ; i <= maxMonth; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.putAll(monthMap);
					if(i<10){
						month = "0" + i;
					}else{
						month = "" + i ;
					}
					map.put("month",month);
					initList.add(map);
				}
				budgCashFlowMapper.delectCash(entityMap);
				budgCashFlowMapper.addInitBudgCashBatch(initList);
			
			}else {
				
				//查询系统启用年月
				Map<String , Object> sysMap = budgCashFlowMapper.querySysYearMonth(entityMap);
				
				int sysYear = Integer.parseInt(sysMap.get("start_year").toString());
				
				int budg_year = Integer.parseInt(entityMap.get("budg_year").toString());
				
				if(budg_year != sysYear){
					return "{\"error\":\"系统启用后从未结账且当前年度不是系统启用年度,请检查本年度之前各年度结账情况后操作！\"}";
				}
				
				entityMap.put("month", sysMap.get("start_month"));
				
				//查询资金计划审核数据中未记账的数据   按年度  月份  项目 汇总
				List<Map<String, Object>> planDetList = budgCashFlowMapper.queryCashPlanDet(entityMap);
				
				if(planDetList == null){
					return "{\"error\":\"当前年度无资金计划数据或无已审核数据,请添加计划数据或审核已添加数据后操作！\"}";
				}
				//先删除流量表中当前年度未结账数据   然后添加
				budgCashFlowMapper.delete(entityMap);
				budgCashFlowMapper.addBatch(planDetList);
				
				//当前最小月份为系统启用月份
				minMonth = Integer.parseInt(sysMap.get("start_month").toString());
				//查询流量预算表中 数据最大月份和最小月份
				Map<String, Object> monthMap = budgCashFlowMapper.queryMaxMonthFromCashPlan(entityMap);
				int maxMonth = Integer.parseInt(String.valueOf(monthMap.get("maxMonth"))) ;
				
				//清除最大月 最小月键值对 
				monthMap.remove("minMonth");
				monthMap.remove("maxMonth");
				
				String month = "";
				for (int i = minMonth ; i <= maxMonth; i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.putAll(monthMap);
					if(i<10){
						month = "0" + i;
					}else{
						month = "" + i ;
					}
					map.put("month",month);
					initList.add(map);
				}
				budgCashFlowMapper.delectCash(entityMap);
				budgCashFlowMapper.addInitBudgCashBatch(initList);
				
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败！\"}");

		}
		
	}
	
}
