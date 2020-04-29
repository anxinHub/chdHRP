/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Store;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface StoreService {

	/**
	 * @Description 添加Store
	 * @param Store entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addStore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Store
	 * @param  Store entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchStore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Store分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryStore(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询StoreByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Store queryStoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Store
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteStore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Store
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchStore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Store
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateStore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Store
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchStore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Store
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importStore(Map<String,Object> entityMap)throws DataAccessException;
	
}
