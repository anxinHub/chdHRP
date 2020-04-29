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
import com.chd.hrp.sys.entity.PatientType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface PatientTypeMapper extends SqlMapper{
	
	/**
	 * @Description 添加PatientType
	 * @param PatientType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPatientType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加PatientType
	 * @param  PatientType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPatientType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询PatientType分页
	 * @param  entityMap RowBounds
	 * @return List<PatientType>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryPatientType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询PatientType所有数据
	 * @param  entityMap
	 * @return List<PatientType>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryPatientType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询PatientTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public PatientType queryPatientTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除PatientType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePatientType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除PatientType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPatientType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新PatientType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePatientType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新PatientType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchPatientType(List<Map<String, Object>> entityMap)throws DataAccessException;
}
