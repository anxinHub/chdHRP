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

import com.chd.hrp.sys.entity.Copy;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CopyMapper extends SqlMapper{
	
	/**
	 * @Description 添加Copy
	 * @param Copy entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCopy(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Copy
	 * @param  Copy entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCopy(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Copy分页
	 * @param  entityMap RowBounds
	 * @return List<Copy>
	 * @throws DataAccessException
	*/
	public List<Copy> queryCopy(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Copy所有数据
	 * @param  entityMap
	 * @return List<Copy>
	 * @throws DataAccessException
	*/
	public List<Copy> queryCopy(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 账套信息<BR> 查询Copy赋予权限所有数据
	 * @param  entityMap
	 * @return List<Copy>
	 * @throws DataAccessException
	 */
	public List<Copy> queryCopyByPerm(Map<String,Object> entityMap) throws DataAccessException;
	 
	/**
	 * @Description 查询CopyByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Copy queryCopyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Copy
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCopy(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Copy
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCopy(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Copy
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCopy(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Copy
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCopy(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryCopyModByPerm(Map<String,Object> entityMap)throws DataAccessException;
	
}
