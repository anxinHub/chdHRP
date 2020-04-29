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

import com.chd.hrp.sys.entity.Perm;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface PermMapper extends SqlMapper{
	
	/**
	 * @Description 添加Perm
	 * @param Perm entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Perm
	 * @param  Perm entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Perm分页
	 * @param  entityMap RowBounds
	 * @return List<Perm>
	 * @throws DataAccessException
	*/
	public List<Perm> queryPerm(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Perm所有数据
	 * @param  entityMap
	 * @return List<Perm>
	 * @throws DataAccessException
	*/
	public List<Perm> queryPerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询PermByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Perm queryPermByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Perm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除PermByGroupId
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePermByGroupId(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Perm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Perm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Perm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
}
