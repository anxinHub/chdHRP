/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Info;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface InfoService {

	/**
	 * @Description 添加Info
	 * @param Info entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Info
	 * @param  Info entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchInfo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Info分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryInfo(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询InfoByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Info queryInfoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteInfo(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @Description 更新InfoCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateInfoCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新InfoName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateInfoName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchInfo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchInfo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Info
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询InfoByMenu
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryInfoByMenu(Map<String,Object> entityMap)throws DataAccessException;
	
}
