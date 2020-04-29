/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccDeptAttr;

/**
* @Title. @Description.
* 部门字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccDeptAttrService {

	/**
	 * @Description 
	 * 部门字典属性表<BR> 添加AccDeptAttr
	 * @param AccDeptAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccDeptAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 批量添加AccDeptAttr
	 * @param  AccDeptAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccDeptAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 查询AccDeptAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccDeptAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 查询AccDeptAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccDeptAttr queryAccDeptAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccDeptAttr queryAccOutDeptByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改部门时<BR> 查询DeptByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccDeptAttr queryDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加部门时<BR> 查询HosDeptByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccDeptAttr queryHosDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 删除AccDeptAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccDeptAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 批量删除AccDeptAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccDeptAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 更新AccDeptAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccDeptAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 *部门字典属性表(批量修改时部门数据可以为空)<BR> 更新AccDeptAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchManageAccDeptAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 批量更新AccDeptAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccDeptAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 导入AccDeptAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccDeptAttr(Map<String,Object> entityMap)throws DataAccessException;
	
}
