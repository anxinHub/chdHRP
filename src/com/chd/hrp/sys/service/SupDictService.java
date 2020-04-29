/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.SupDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SupDictService {

	/**
	 * @Description 添加SupDict
	 * @param SupDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addSupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加SupDict
	 * @param  SupDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchSupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询SupDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String querySupDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> querySupDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SupDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public SupDict querySupDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteSupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchSupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateSupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchSupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入SupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importSupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String querySupDictBySelector(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 用于 集团化管理   供应商选择器 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryGroupSupDictBySelector(Map<String,Object> entityMap)throws DataAccessException;

	public String querySupDictList(Map<String, Object> paramMap) throws DataAccessException;
	
}
