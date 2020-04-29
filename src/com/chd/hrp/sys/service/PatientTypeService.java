/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.PatientType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface PatientTypeService {

	/**
	 * @Description 添加PatientType
	 * @param PatientType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addPatientType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加PatientType
	 * @param  PatientType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchPatientType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询PatientType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryPatientType(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryPatientTypePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询PatientTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public PatientType queryPatientTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deletePatientType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchPatientType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updatePatientType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchPatientType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入PatientType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importPatientType(Map<String,Object> entityMap)throws DataAccessException;
	
}
