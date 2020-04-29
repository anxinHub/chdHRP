/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.maintain;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 

public interface AssMaintainPlanMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加051202 保养计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException;
	 
	/**
	 * @Description 
	 * 批量添加051202 保养计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssMaintainPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051202 保养计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051202 保养计划<BR> (审核)
	 * @param  entityMap
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	*/
	public int updateBatchAssMaintainPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 051202 保养计划<BR>  (销审)
	 * @param  entityMap
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	*/
	public int updateBatchAssMaintainPlanBack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 051202 保养计划<BR>  (终止计划)
	 * @param  entityMap
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	*/
	public int updateBatchAssMaintainPlanStop(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 删除051202 保养计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除051202 保养计划<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssMaintainPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlan> queryAssMaintainPlan(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202 保养计划<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlan> queryAssMaintainPlan(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssMaintainPlan
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
	
	public Long queryCurrentSequence()throws DataAccessException;

	public List<AssMaintainPlan> queryAssMainRemindPlan(
			Map<String, Object> entityMap)throws DataAccessException;

	public List<AssMaintainPlan> queryAssMainRemindPlan(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryAssMainTainPlanByMainBatch(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAssMainTainPlanByDetail(
			Map<String, Object> entityMap);

	public Map<String, Object> queryAssMainTainPlanByMain(
			Map<String, Object> entityMap);

	public List<String> queryAssMainTainState(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDetails(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
