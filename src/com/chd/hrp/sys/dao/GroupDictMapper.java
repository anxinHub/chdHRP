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

import com.chd.hrp.sys.entity.GroupDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface GroupDictMapper extends SqlMapper{
	
	/**
	 * @Description 添加GroupDict
	 * @param GroupDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addGroupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加GroupDict
	 * @param  GroupDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchGroupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询GroupDict分页
	 * @param  entityMap RowBounds
	 * @return List<GroupDict>
	 * @throws DataAccessException
	*/
	public List<GroupDict> queryGroupDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询GroupDict所有数据
	 * @param  entityMap
	 * @return List<GroupDict>
	 * @throws DataAccessException
	*/
	public List<GroupDict> queryGroupDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询GroupDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public GroupDict queryGroupDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除GroupDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteGroupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 根据GroupId删除GroupDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteGroupDictByGroupId(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 批量删除GroupDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchGroupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新GroupDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateGroupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新GroupDict的状态
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateGroupDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新GroupDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchGroupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
}
