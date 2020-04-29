/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Emp;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

public interface EmpService {

	/**
	 * @Description 添加Emp
	 * @param Emp entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Emp
	 * @param  Emp entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Emp分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryEmp(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询EmpByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Emp queryEmpByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Emp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Emp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Emp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新EmpName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateEmpName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新EmpCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateEmpCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Emp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Emp
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	public abstract String queryEmpChangeRemark(Map<String, Object> paramMap) throws DataAccessException;
}
