/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.DeptKind;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface DeptKindService {

	/**
	 * @Description 添加DeptKind
	 * @param DeptKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addDeptKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加DeptKind
	 * @param  DeptKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchDeptKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询DeptKind分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryDeptKind(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptKindByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public DeptKind queryDeptKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteDeptKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchDeptKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateDeptKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchDeptKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importDeptKind(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 同步平台分类
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String quneryPlatformKind(Map<String, Object> entityMap)throws DataAccessException;
	
}
