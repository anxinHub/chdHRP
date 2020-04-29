/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.EmpDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface EmpDictService {

	/**
	 * @Description 添加EmpDict
	 * @param EmpDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addEmpDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加EmpDict
	 * @param  EmpDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchEmpDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询EmpDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryEmpDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryEmpDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询EmpDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public EmpDict queryEmpDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除EmpDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteEmpDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除EmpDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchEmpDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新EmpDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateEmpDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新EmpDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchEmpDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入EmpDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importEmpDict(Map<String,Object> entityMap)throws DataAccessException;

	public String queryEmpDictList(Map<String, Object> paramMap) throws DataAccessException;
	
}
