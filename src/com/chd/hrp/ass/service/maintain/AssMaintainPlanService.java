/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.maintain;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.maintain.AssMaintainPlan;
/**
 * 
 * @Description:
 * 051202 保养计划
 * @Table:
 * ASS_MAINTAIN_PLAN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMaintainPlanService { 

	/**
	 * @Description 
	 * 添加051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException; 
	
	/**
	 * @Description 
	 * 批量添加051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssMaintainPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051202 保养计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051202 保养计划<BR> (审核)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMaintainPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051202 保养计划<BR> (消审)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMaintainPlanBack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051202 保养计划<BR> (终止计划)
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMaintainPlanStop(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 删除051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssMaintainPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新051202 保养计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssMaintainPlan(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象051202 保养计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssMaintainPlan queryAssMaintainPlanByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	*/
	public AssMaintainPlan queryAssMaintainPlanByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainPlan>
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlan> queryAssMaintainPlanExists(Map<String,Object> entityMap)throws DataAccessException;

	public String queryAssMainRemindPlan(Map<String, Object> page)throws DataAccessException;

	Map<String, Object> queryAssMainTainPlanPrint(Map<String, Object> entityMap)
			throws DataAccessException;

	public List<String> queryAssMainTainState(Map<String, Object> mapVo);

	public String queryAssMaintainItem(Map<String, Object> page);

	public String buildAssMaintainItem(Map<String, Object> mapVo);

	public String saveAssMaintainItem(List<Map<String, Object>> listVo);

	public String deleteAssMaintainItem(List<Map<String, Object>> listVo);

	public String addOrUpdateMain(Map<String, Object> mapVo);

	Long queryCurrentSequence() throws DataAccessException;

	public String queryDetails(Map<String, Object> page);
}
