/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Source;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SourceService {

	/**
	 * @Description 添加Source
	 * @param Source entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addSource(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Source
	 * @param  Source entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchSource(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Source分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String querySource(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> querySourcePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SourceByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Source querySourceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteSource(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchSource(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateSource(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchSource(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Source
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importSource(Map<String,Object> entityMap)throws DataAccessException;
	
	public String querySourceByAssPay(Map<String,Object> entityMap)throws DataAccessException;
	
}
