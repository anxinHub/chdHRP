/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.GroupDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface GroupDictService {

	/**
	 * @Description 添加GroupDict
	 * @param GroupDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addGroupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加GroupDict
	 * @param  GroupDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchGroupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询GroupDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryGroupDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询GroupDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public GroupDict queryGroupDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteGroupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchGroupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateGroupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchGroupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入GroupDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importGroupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询GroupDict菜单
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryGroupDictByMenu(Map<String,Object> entityMap)throws DataAccessException;
	
}
