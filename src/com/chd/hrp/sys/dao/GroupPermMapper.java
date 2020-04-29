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

import com.chd.hrp.sys.entity.GroupPerm;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface GroupPermMapper extends SqlMapper{
	
	/**
	 * @Description 添加GroupPerm
	 * @param GroupPerm entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addGroupPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加GroupPerm
	 * @param  GroupPerm entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchGroupPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询GroupPerm分页
	 * @param  entityMap RowBounds
	 * @return List<GroupPerm>
	 * @throws DataAccessException
	*/
	public List<GroupPerm> queryGroupPerm(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询GroupPerm所有数据
	 * @param  entityMap
	 * @return List<GroupPerm>
	 * @throws DataAccessException
	*/
	public List<GroupPerm> queryGroupPerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询GroupPermByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public GroupPerm queryGroupPermByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除GroupPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteGroupPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 根据GroupId删除GroupPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteGroupPermByGroupId(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除GroupPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchGroupPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新GroupPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateGroupPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新GroupPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchGroupPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
}
