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

import com.chd.hrp.sys.entity.ProjLevel;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ProjLevelMapper extends SqlMapper{
	
	/**
	 * @Description 添加ProjLevel
	 * @param ProjLevel entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addProjLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加ProjLevel
	 * @param  ProjLevel entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchProjLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询ProjLevel分页
	 * @param  entityMap RowBounds
	 * @return List<ProjLevel>
	 * @throws DataAccessException
	*/
	public List<ProjLevel> queryProjLevel(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询ProjLevel所有数据
	 * @param  entityMap
	 * @return List<ProjLevel>
	 * @throws DataAccessException
	*/
	public List<ProjLevel> queryProjLevel(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjLevelByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public ProjLevel queryProjLevelByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除ProjLevel
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteProjLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除ProjLevel
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchProjLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新ProjLevel
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateProjLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新ProjLevel
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchProjLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
}
