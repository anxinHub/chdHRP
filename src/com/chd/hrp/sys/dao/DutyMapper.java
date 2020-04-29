/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.sys.entity.Duty;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface DutyMapper extends SqlMapper{
	
	/**
	 * @Description 添加Duty
	 * @param Duty entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Duty	
	 * @param  Duty entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchDuty(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Duty分页
	 * @param  entityMap RowBounds
	 * @return List<Duty>
	 * @throws DataAccessException
	*/
	public List<Duty> queryDuty(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Duty所有数据
	 * @param  entityMap
	 * @return List<Duty>
	 * @throws DataAccessException
	*/
	public List<Duty> queryDuty(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DutyByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Duty queryDutyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Duty
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Duty
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchDuty(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Duty
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Duty
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchDuty(List<Map<String, Object>> entityMap)throws DataAccessException;
}
