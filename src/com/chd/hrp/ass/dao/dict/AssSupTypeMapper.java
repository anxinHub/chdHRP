/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.ass.dao.dict;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.dict.AssSupType;
import com.chd.hrp.sys.entity.SupType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AssSupTypeMapper extends SqlMapper{
	
	/**
	 * @Description 添加SupType
	 * @param SupType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addSupType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加SupType
	 * @param  SupType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchSupType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询SupType分页
	 * @param  entityMap RowBounds
	 * @return List<SupType>
	 * @throws DataAccessException
	*/
	public List<SupType> querySupType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询SupType所有数据
	 * @param  entityMap
	 * @return List<SupType>
	 * @throws DataAccessException
	*/
	public List<AssSupType> querySupType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SupTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public SupType querySupTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除SupType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteSupType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除SupType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchSupType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新SupType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateSupType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新SupType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchSupType(List<Map<String, Object>> entityMap)throws DataAccessException;
}
