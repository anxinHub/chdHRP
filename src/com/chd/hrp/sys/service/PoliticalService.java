/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Political;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface PoliticalService {

	/**
	 * @Description 添加Political
	 * @param Political entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addPolitical(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Political
	 * @param  Political entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchPolitical(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Political分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryPolitical(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询PoliticalByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Political queryPoliticalByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deletePolitical(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchPolitical(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updatePolitical(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchPolitical(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Political
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importPolitical(Map<String,Object> entityMap)throws DataAccessException;
	
}
