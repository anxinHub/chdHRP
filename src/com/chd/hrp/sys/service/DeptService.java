/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Dept;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface DeptService {

	/**
	 * @Description 添加Dept
	 * @param Dept entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Dept
	 * @param  Dept entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Dept分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryDept(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Dept queryDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Dept
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Dept
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Dept
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Dept
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新DeptCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateDeptCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新DeptName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateDeptName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Dept
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询DeptByMenu
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryDeptByMenu(Map<String,Object> entityMap)throws DataAccessException;
	public Map<String, Object> queryDeptByCodeName(Map<String, Object> entityMap)throws DataAccessException;
	public String queryDeptOrg(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> querySuper(Map<String, Object> mapVo) throws DataAccessException;
	public void updateIsLast(Map<String, Object> isLast) throws DataAccessException;
}
