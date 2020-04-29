/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Station;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface StationService {

	/**
	 * @Description 添加Station
	 * @param Station entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addStation(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Station
	 * @param  Station entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchStation(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Station分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryStation(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询StationByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Station queryStationByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteStation(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchStation(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateStation(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchStation(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Station
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importStation(Map<String,Object> entityMap)throws DataAccessException;
	
}
