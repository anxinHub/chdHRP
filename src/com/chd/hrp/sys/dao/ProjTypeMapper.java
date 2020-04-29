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

import com.chd.hrp.sys.entity.ProjType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ProjTypeMapper extends SqlMapper{
	
	/**
	 * @Description 添加ProjType
	 * @param ProjType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addProjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加ProjType
	 * @param  ProjType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchProjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询ProjType分页
	 * @param  entityMap RowBounds
	 * @return List<ProjType>
	 * @throws DataAccessException
	*/
	public List<ProjType> queryProjType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询ProjType所有数据
	 * @param  entityMap
	 * @return List<ProjType>
	 * @throws DataAccessException
	*/
	public List<ProjType> queryProjType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public ProjType queryProjTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除ProjType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteProjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除ProjType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchProjType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新ProjType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateProjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新ProjType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchProjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 用于集团化   项目选择器中  项目类型
	 * @Description 查询GroupProjType分页
	 * @param  entityMap RowBounds
	 * @return List<ProjType>
	 * @throws DataAccessException
	*/
	public List<ProjType> queryGroupProjType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 用于集团化   项目选择器中  项目类型
	 * @Description 查询GroupProjType所有数据
	 * @param  entityMap
	 * @return List<ProjType>
	 * @throws DataAccessException
	*/
	public List<ProjType> queryGroupProjType(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
