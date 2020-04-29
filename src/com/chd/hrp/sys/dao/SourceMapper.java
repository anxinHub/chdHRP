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
import com.chd.hrp.sys.entity.Source;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SourceMapper extends SqlMapper{
	
	/**
	 * @Description 添加Source
	 * @param Source entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addSource(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Source
	 * @param  Source entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchSource(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Source分页
	 * @param  entityMap RowBounds
	 * @return List<Source>
	 * @throws DataAccessException
	*/
	public List<Source> querySource(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Source所有数据
	 * @param  entityMap
	 * @return List<Source>
	 * @throws DataAccessException
	*/
	public List<Source> querySource(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> querySourcePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SourceByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Source querySourceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询SourceByMaxId
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Long querySourceByMaxId(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Source
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteSource(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Source
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchSource(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Source
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateSource(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Source
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchSource(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<Source> querySourceByAssPay(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Source> querySourceByAssPay(Map<String,Object> entityMap) throws DataAccessException;

	public Source querySourceByCodeByName(Map<String, Object> entityMap) throws DataAccessException;
}
