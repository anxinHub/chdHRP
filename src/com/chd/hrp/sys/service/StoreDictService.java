/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.StoreDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface StoreDictService {

	/**
	 * @Description 添加StoreDict
	 * @param StoreDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addStoreDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加StoreDict
	 * @param  StoreDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchStoreDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询StoreDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryStoreDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryStoreDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询StoreDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public StoreDict queryStoreDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteStoreDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchStoreDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateStoreDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchStoreDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入StoreDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importStoreDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryStoreDictBySelector(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 用于集团化管理   集团库房选择器 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryGroupStoreDictBySelector(Map<String,Object> entityMap)throws DataAccessException;

	public String queryStoreDictList(Map<String, Object> page) throws DataAccessException;
	
}
