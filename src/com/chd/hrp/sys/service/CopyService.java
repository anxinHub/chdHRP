/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Copy;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CopyService {

	/**
	 * @Description 添加Copy
	 * @param Copy entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCopy(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Copy
	 * @param  Copy entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCopy(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Copy分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCopy(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询CopyByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Copy queryCopyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Copy
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCopy(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Copy
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCopy(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Copy
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCopy(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Copy
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCopy(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Copy
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importCopy(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
