/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.budgincome.toptodown.plan;

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
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.budg.dao.common.BudgIncomeStackMapper;
import com.chd.hrp.budg.dao.toptodown.plan.BudgHosYearIncomePlanMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeEditPlan;
import com.chd.hrp.budg.service.toptodown.plan.BudgHosYearIncomePlanService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Table:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgHosYearIncomePlanService")
public class BudgHosYearIncomePlanServiceImpl implements BudgHosYearIncomePlanService {

	private static Logger logger = Logger.getLogger(BudgHosYearIncomePlanServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgHosYearIncomePlanMapper")
	private final BudgHosYearIncomePlanMapper budgHosYearIncomePlanMapper = null;
	
	//引入DAO操作
	@Resource(name = "budgIncomeStackMapper")
	private final BudgIncomeStackMapper budgIncomeStackMapper = null;
	
	
    
	/**
	 * @Description 
	 * 添加BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象BUDG_MED_INCOME_EDIT_PLAN
		BudgMedIncomeEditPlan budgHosYearIncomePlan = queryByCode(entityMap);

		if (budgHosYearIncomePlan != null) {

			return "{\"error\":\"数据重复,请重新添加.\",\"state\":\"false\"}";

		}
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		
		if(entityMap.get("dataStack") != null ){
			
			 listVo= JsonListMapUtil.getListMap(entityMap.get("dataStack").toString());
			
			for ( Map<String,Object> map: listVo) {
				
				//表的主键
				map.put("group_id", SessionManager.getGroupId())   ;
				map.put("hos_id", SessionManager.getHosId())   ;
				map.put("copy_code", SessionManager.getCopyCode())   ;
		    }
		}
		
		try {
			
			int state = budgHosYearIncomePlanMapper.add(entityMap);
			
			// 保存 函数参数栈数据
			if(listVo.size() > 0){
				
				budgIncomeStackMapper.deleteBatch(listVo);
				
				budgIncomeStackMapper.addBatch(listVo);
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败 \",\"state\":\"false\"}");
			
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgHosYearIncomePlanMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
			
			if(entityMap.get("dataStack") != null ){
				
				 listVo= JsonListMapUtil.getListMap(entityMap.get("dataStack").toString());
				
				for ( Map<String,Object> map: listVo) {
					
					//表的主键
					map.put("group_id", SessionManager.getGroupId())   ;
					map.put("hos_id", SessionManager.getHosId())   ;
					map.put("copy_code", SessionManager.getCopyCode())   ;
					
			    }
			}
			
			int state = budgHosYearIncomePlanMapper.update(entityMap);
			
			budgIncomeStackMapper.delete(entityMap);
			
			// 保存 函数参数栈数据
			if(listVo.size() > 0){
				
				budgIncomeStackMapper.addBatch(listVo);
				
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			
			throw new SysException("{\"error\":\"更新失败.\",\"state\":\"false\"}") ;
			
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgHosYearIncomePlanMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = budgHosYearIncomePlanMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgIncomeStackMapper.deleteBatch(entityList);
			
			budgHosYearIncomePlanMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败.\",\"state\":\"false\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加BUDG_MED_INCOME_EDIT_PLAN<BR> 
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
		//判断是否存在对象BUDG_MED_INCOME_EDIT_PLAN
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMedIncomeEditPlan> list = (List<BudgMedIncomeEditPlan>)budgHosYearIncomePlanMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgHosYearIncomePlanMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgHosYearIncomePlanMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgMedIncomeEditPlan> list = (List<BudgMedIncomeEditPlan>)budgHosYearIncomePlanMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedIncomeEditPlan> list = (List<BudgMedIncomeEditPlan>)budgHosYearIncomePlanMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgHosYearIncomePlanMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgHosYearIncomePlan
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgHosYearIncomePlanMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgHosYearIncomePlan>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgHosYearIncomePlanMapper.queryExists(entityMap);
	}
	
	//根据科目编码查询改科目是否已经存在
	@Override
	public Integer queryBudgMedIncomeEditPlanByCode(Map<String, Object> entityMap) throws DataAccessException{
		
		return budgHosYearIncomePlanMapper.queryBudgMedIncomeEditPlanByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 批量添加BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String generateBudgHosYearIncomePlan(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			//删除数据   后生成数据
			budgHosYearIncomePlanMapper.delete(entityMap);
			
			budgHosYearIncomePlanMapper.addGenerateBatch(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
}
