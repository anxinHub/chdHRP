/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Group;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface GroupService {

	/**
	 * @Description 添加Group
	 * @param Group entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Group
	 * @param  Group entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchGroup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Group分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryGroup(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询Group菜单
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryGroupByMenu(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询GroupByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Group queryGroupByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchGroup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新GroupCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateGroupCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新GroupName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateGroupName(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @Description 批量更新Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchGroup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Group
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importGroup(Map<String,Object> entityMap)throws DataAccessException;
	
}
