/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.School;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SchoolService {

	/**
	 * @Description 添加School
	 * @param School entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addSchool(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加School
	 * @param  School entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchSchool(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询School分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String querySchool(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SchoolByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public School querySchoolByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteSchool(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchSchool(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateSchool(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchSchool(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入School
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importSchool(Map<String,Object> entityMap)throws DataAccessException;
	
}
