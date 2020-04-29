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

import com.chd.hrp.sys.entity.Region;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RegionMapper extends SqlMapper{
	
	/**
	 * @Description 添加Region
	 * @param Region entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addRegion(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Region
	 * @param  Region entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchRegion(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Region分页
	 * @param  entityMap RowBounds
	 * @return List<Region>
	 * @throws DataAccessException
	*/
	public List<Region> queryRegion(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Region所有数据
	 * @param  entityMap
	 * @return List<Region>
	 * @throws DataAccessException
	*/
	public List<Region> queryRegion(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询RegionByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Region queryRegionByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Region
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteRegion(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Region
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchRegion(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Region
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateRegion(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Region
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchRegion(List<Map<String, Object>> entityMap)throws DataAccessException;
}
