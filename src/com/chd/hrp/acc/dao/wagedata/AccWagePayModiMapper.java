/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWagePayModi;

/**
* @Title. @Description.
* 工资调整<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWagePayModiMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资调整<BR> 添加AccWagePayModi
	 * @param AccWagePayModi entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWagePayModi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 批量添加AccWagePayModi
	 * @param  AccWagePayModi entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWagePayModi(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 查询AccWagePayModi分页
	 * @param  entityMap RowBounds
	 * @return List<AccWagePayModi>
	 * @throws DataAccessException
	*/
	public List<AccWagePayModi> queryAccWagePayModi(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资调整<BR> 查询AccWagePayModi所有调整
	 * @param  entityMap
	 * @return List<AccWagePayModi>
	 * @throws DataAccessException
	*/
	public List<AccWagePayModi> queryAccWagePayModi(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 查询AccWagePayModiByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWagePayModi queryAccWagePayModiByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 删除AccWagePayModi
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWagePayModi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 批量删除AccWagePayModi
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWagePayModi(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资调整<BR> 更新AccWagePayModi
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWagePayModi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 批量更新AccWagePayModi
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWagePayModi(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
