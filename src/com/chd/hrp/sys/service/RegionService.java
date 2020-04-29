/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Region;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface RegionService {

	/**
	 * @Description 添加Region
	 * @param Region entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addRegion(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Region
	 * @param  Region entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchRegion(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Region分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryRegion(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询RegionByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Region queryRegionByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteRegion(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchRegion(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateRegion(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchRegion(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Region
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importRegion(Map<String,Object> entityMap)throws DataAccessException;
	
}
