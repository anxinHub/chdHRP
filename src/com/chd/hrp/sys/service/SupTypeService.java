/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.SupType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SupTypeService {

	/**
	 * @Description 添加SupType
	 * @param SupType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addSupType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加SupType
	 * @param  SupType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchSupType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询SupType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String querySupType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SupTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public SupType querySupTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteSupType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchSupType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateSupType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchSupType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入SupType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importSupType(Map<String,Object> entityMap)throws DataAccessException;
	
}
