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

import com.chd.hrp.sys.entity.HosType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HosTypeMapper extends SqlMapper{
	
	/**
	 * @Description 添加HosType
	 * @param HosType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addHosType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加HosType
	 * @param  HosType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchHosType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询HosType分页
	 * @param  entityMap RowBounds
	 * @return List<HosType>
	 * @throws DataAccessException
	*/
	public List<HosType> queryHosType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询HosType所有数据
	 * @param  entityMap
	 * @return List<HosType>
	 * @throws DataAccessException
	*/
	public List<HosType> queryHosType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询HosTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HosType queryHosTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除HosType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteHosType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除HosType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchHosType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新HosType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateHosType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新HosType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchHosType(List<Map<String, Object>> entityMap)throws DataAccessException;
}
