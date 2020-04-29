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

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.toptodown.plan.BudgMedIncomeHmPlanMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeHmPlan;
import com.chd.hrp.budg.service.toptodown.plan.BudgMedIncomeHmPlanService;
import com.github.pagehelper.PageInfo;

/**
 * 
* @Description:
 * 医院年度至医院月份医疗收入预算分解方案
 * @Table:
 * BUDG_MED_INCOME_HM_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgMedIncomeHmPlanService")
public class BudgMedIncomeHmPlanServiceImpl implements BudgMedIncomeHmPlanService {

	private static Logger logger = Logger.getLogger(BudgMedIncomeHmPlanServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMedIncomeHmPlanMapper")
	private final BudgMedIncomeHmPlanMapper budgMedIncomeHmPlanMapper = null;
    
	/**
	 * @Description 
	 * 添加BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象BUDG_MED_INCOME_HM_PLAN
		BudgMedIncomeHmPlan budgMedIncomeHmPlan = queryByCode(entityMap);

		if (budgMedIncomeHmPlan != null) {

			return "{\"error\":\"相同年度存在【"+entityMap.get("subj_code")+"】相同的科目,请重新添加.\"}";

		}
		
		try {
			
			int state = budgMedIncomeHmPlanMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

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
			
			budgMedIncomeHmPlanMapper.addBatch(entityList);
			
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
			
		  int state = budgMedIncomeHmPlanMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

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
			
		  budgMedIncomeHmPlanMapper.updateBatch(entityList);
			
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
			
			int state = budgMedIncomeHmPlanMapper.delete(entityMap);
			
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
			
			budgMedIncomeHmPlanMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

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
		
		List<BudgMedIncomeHmPlan> list = (List<BudgMedIncomeHmPlan>)budgMedIncomeHmPlanMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgMedIncomeHmPlanMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgMedIncomeHmPlanMapper.add(entityMap);
			
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
			
			List<BudgMedIncomeHmPlan> list = (List<BudgMedIncomeHmPlan>)budgMedIncomeHmPlanMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgMedIncomeHmPlan> list = (List<BudgMedIncomeHmPlan>)budgMedIncomeHmPlanMapper.query(entityMap, rowBounds);
			
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
		return budgMedIncomeHmPlanMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgHosDeptYearIncomePlan
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeHmPlanMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取BUDG_MED_INCOME_EDIT_PLAN<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgHosDeptYearIncomePlan>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeHmPlanMapper.queryExists(entityMap);
	}
	
	/**
	 * 查询数据是否存在
	 */
	@Override
	public int queryDataExist(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeHmPlanMapper.queryDataExist(entityMap);
	}
	
	/**
	 * 校验 医疗收入科目 是否存在
	 */
	@Override
	public int querySubjExist(Map<String,Object> entityMap)throws DataAccessException{
		return budgMedIncomeHmPlanMapper.querySubjExist(entityMap);
	}
	@Override
	public String generate(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
	
		budgMedIncomeHmPlanMapper.addBatchMedIncomeHm(entityMap);
		
		return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
	}
	
}
