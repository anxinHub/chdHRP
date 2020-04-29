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

import com.chd.hrp.sys.entity.Station;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface StationMapper extends SqlMapper{
	
	/**
	 * @Description 添加Station
	 * @param Station entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addStation(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Station
	 * @param  Station entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchStation(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Station分页
	 * @param  entityMap RowBounds
	 * @return List<Station>
	 * @throws DataAccessException
	*/
	public List<Station> queryStation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Station所有数据
	 * @param  entityMap
	 * @return List<Station>
	 * @throws DataAccessException
	*/
	public List<Station> queryStation(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询StationByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Station queryStationByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Station
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteStation(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Station
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchStation(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Station
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateStation(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Station
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchStation(List<Map<String, Object>> entityMap)throws DataAccessException;
}
