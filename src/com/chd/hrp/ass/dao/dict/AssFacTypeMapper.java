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
import com.chd.hrp.ass.entity.dict.AssFacType;



/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AssFacTypeMapper extends SqlMapper{
	
	/**
	 * @Description 添加FacType
	 * @param FacType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addFacType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加FacType
	 * @param  FacType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchFacType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询FacType分页
	 * @param  entityMap RowBounds
	 * @return List<FacType>
	 * @throws DataAccessException
	*/
	public List<AssFacType> queryFacType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询FacType所有数据
	 * @param  entityMap
	 * @return List<FacType>
	 * @throws DataAccessException
	*/
	public List<AssFacType> queryFacType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询FacTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AssFacType queryFacTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除FacType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteFacType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除FacType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchFacType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新FacType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateFacType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新FacType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchFacType(List<Map<String, Object>> entityMap)throws DataAccessException;
}
