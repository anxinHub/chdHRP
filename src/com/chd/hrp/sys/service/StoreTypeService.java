/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.StoreType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface StoreTypeService {

	/**
	 * @Description 添加StoreType
	 * @param StoreType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加StoreType
	 * @param  StoreType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询StoreType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryStoreType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询StoreTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public StoreType queryStoreTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入StoreType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
}
