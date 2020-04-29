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

import com.chd.hrp.sys.entity.HosLevel;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HosLevelMapper extends SqlMapper{
	
	/**
	 * @Description 添加HosLevel
	 * @param HosLevel entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addHosLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加HosLevel
	 * @param  HosLevel entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchHosLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询HosLevel分页
	 * @param  entityMap RowBounds
	 * @return List<HosLevel>
	 * @throws DataAccessException
	*/
	public List<HosLevel> queryHosLevel(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询HosLevel所有数据
	 * @param  entityMap
	 * @return List<HosLevel>
	 * @throws DataAccessException
	*/
	public List<HosLevel> queryHosLevel(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询HosLevelByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HosLevel queryHosLevelByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除HosLevel
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteHosLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除HosLevel
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchHosLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新HosLevel
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateHosLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新HosLevel
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchHosLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
}
