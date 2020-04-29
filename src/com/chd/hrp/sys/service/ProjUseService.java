/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.ProjUse;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ProjUseService {

	/**
	 * @Description 添加ProjUse
	 * @param ProjUse entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addProjUse(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加ProjUse
	 * @param  ProjUse entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchProjUse(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询ProjUse分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryProjUse(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjUseByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public ProjUse queryProjUseByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteProjUse(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchProjUse(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateProjUse(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchProjUse(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入ProjUse
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importProjUse(Map<String,Object> entityMap)throws DataAccessException;
	
}
