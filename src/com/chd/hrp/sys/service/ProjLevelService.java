/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.ProjLevel;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ProjLevelService {

	/**
	 * @Description 添加ProjLevel
	 * @param ProjLevel entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addProjLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加ProjLevel
	 * @param  ProjLevel entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchProjLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询ProjLevel分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryProjLevel(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjLevelByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public ProjLevel queryProjLevelByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteProjLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchProjLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateProjLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchProjLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入ProjLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importProjLevel(Map<String,Object> entityMap)throws DataAccessException;
	
}
