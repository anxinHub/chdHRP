/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.books.subjaccount;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccMultiPlan;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.entity.HrpAccSelect;

/**
* @Title. @Description.
* 多栏方案<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccMultiPlanMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 多栏方案<BR> 添加AccMultiPlan
	 * @param AccMultiPlan entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccMultiPlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案<BR> 批量添加AccMultiPlan
	 * @param  AccMultiPlan entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccMultiPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案<BR> 查询AccMultiPlan分页
	 * @param  entityMap RowBounds
	 * @return List<AccMultiPlan>
	 * @throws DataAccessException
	*/
	public List<AccMultiPlan> queryAccMultiPlan(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 多栏方案<BR> 查询AccMultiPlan所有数据
	 * @param  entityMap
	 * @return List<AccMultiPlan>
	 * @throws DataAccessException
	*/
	public List<AccMultiPlan> queryAccMultiPlan(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案<BR> 查询AccMultiPlanByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMultiPlan queryAccMultiPlanByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案<BR> 删除AccMultiPlan
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccMultiPlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案<BR> 批量删除AccMultiPlan
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccMultiPlan(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 多栏方案<BR> 更新AccMultiPlan
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccMultiPlan(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 多栏方案<BR> 批量更新AccMultiPlan
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccMultiPlan(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<HrpAccSelect> queryAccSubjList(Map<String,Object> entityMap) throws DataAccessException;

	public String quertMaxMulti(Map<String, Object> entityMap);
	
}
