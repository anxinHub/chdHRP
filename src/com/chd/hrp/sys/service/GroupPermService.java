/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.GroupPerm;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface GroupPermService {

	/**
	 * @Description 添加GroupPerm
	 * @param GroupPerm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addGroupPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加GroupPerm
	 * @param  GroupPerm entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchGroupPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询GroupPerm分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryGroupPerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询GroupPerm
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAllGroupPerm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询GroupPermByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public GroupPerm queryGroupPermByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteGroupPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 根据GroupId删除GroupPerm
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String deleteGroupPermByGroupId(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchGroupPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateGroupPerm(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchGroupPerm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入GroupPerm
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importGroupPerm(Map<String,Object> entityMap)throws DataAccessException;
	
}
