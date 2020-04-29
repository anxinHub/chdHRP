/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.ProjDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ProjDictService {

	/**
	 * @Description 添加ProjDict
	 * @param ProjDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addProjDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加ProjDict
	 * @param  ProjDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchProjDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询ProjDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryProjDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryProjDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public ProjDict queryProjDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteProjDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchProjDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateProjDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchProjDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入ProjDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importProjDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryProjDictBySelector(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 
	 * 用于 集团化项目选择器
	 */
	public String queryGroupProjDictBySelector(Map<String,Object> entityMap)throws DataAccessException;

	public String queryProjDictList(Map<String, Object> paramMap) throws DataAccessException;
	
}
