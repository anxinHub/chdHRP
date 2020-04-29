/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.sys.entity.Group;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface GroupMapper extends SqlMapper{
	
	/**
	 * @Description 添加Group
	 * @param Group entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Group
	 * @param  Group entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchGroup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询最大的Group排序号
	 * @param  entityMap RowBounds
	 * @return List<Group>
	 * @throws DataAccessException
	*/
	public String queryGroupSortMax(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询Group分页
	 * @param  entityMap RowBounds
	 * @return List<Group>
	 * @throws DataAccessException
	*/
	public List<Group> queryGroup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 查询当前插入group_id
	 * @param  
	 * @return int
	 * @throws DataAccessException
	*/
	public long queryCurrentSequence() throws DataAccessException;

	/**
	 * @Description 查询Group所有数据
	 * @param  entityMap
	 * @return List<Group>
	 * @throws DataAccessException
	*/
	public List<Group> queryGroup(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询GroupByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Group queryGroupByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Group
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Group
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchGroup(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Group
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新GroupCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateGroupCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新GroupName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateGroupName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Group
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchGroup(List<Map<String, Object>> entityMap)throws DataAccessException;
}
