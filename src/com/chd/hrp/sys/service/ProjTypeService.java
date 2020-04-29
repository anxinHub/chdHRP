/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.ProjType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ProjTypeService {

	/**
	 * @Description 添加ProjType
	 * @param ProjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addProjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加ProjType
	 * @param  ProjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchProjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询ProjType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryProjType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public ProjType queryProjTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteProjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchProjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateProjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchProjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入ProjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importProjType(Map<String,Object> entityMap)throws DataAccessException;
	
}
