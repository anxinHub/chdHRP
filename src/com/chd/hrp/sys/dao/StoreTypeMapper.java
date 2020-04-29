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

import com.chd.hrp.sys.entity.StoreType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface StoreTypeMapper extends SqlMapper{
	
	/**
	 * @Description 添加StoreType
	 * @param StoreType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加StoreType
	 * @param  StoreType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询StoreType分页
	 * @param  entityMap RowBounds
	 * @return List<StoreType>
	 * @throws DataAccessException
	*/
	public List<StoreType> queryStoreType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询StoreType所有数据
	 * @param  entityMap
	 * @return List<StoreType>
	 * @throws DataAccessException
	*/
	public List<StoreType> queryStoreType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询StoreTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public StoreType queryStoreTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除StoreType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除StoreType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新StoreType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新StoreType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * 用户集团化管理   集团库房选择器--集团库房类型
	 * @Description 查询GroupStoreType所有数据
	 * @param  entityMap
	 * @return List<StoreType>
	 * @throws DataAccessException
	*/
	public List<StoreType> queryGroupStoreType(Map<String,Object> entityMap) throws DataAccessException;

}
