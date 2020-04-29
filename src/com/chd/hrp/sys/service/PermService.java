/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Perm;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface PermService {

	/**
	 * @Description 添加Perm
	 * @param Perm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Perm
	 * @param  Perm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Perm分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryPerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询Perm所有数据
	 * @param  entityMap
	 * @return List<Perm>
	 * @throws DataAccessException
	*/
	public String queryAllPerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询PermByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Perm queryPermByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deletePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updatePerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Perm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
