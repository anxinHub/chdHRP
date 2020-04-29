/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.ModStart;

/**
* @Title. @Description.
* 系统启用<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ModStartService {

	/**
	 * @Description 
	 * 系统启用<BR> 添加ModStart
	 * @param ModStart entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addModStart(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量添加ModStart
	 * @param  ModStart entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchModStart(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStart分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryModStart(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStartByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public ModStart queryModStartByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 删除ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteModStart(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量删除ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchModStart(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 更新ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateModStart(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量更新ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchModStart(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统启用<BR> 导入ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importModStart(Map<String,Object> entityMap)throws DataAccessException;

	String querySysModStart(Map<String, Object> entityMap)
			throws DataAccessException;

}
