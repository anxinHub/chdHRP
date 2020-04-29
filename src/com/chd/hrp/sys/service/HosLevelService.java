/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.HosLevel;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HosLevelService {

	/**
	 * @Description 添加HosLevel
	 * @param HosLevel entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addHosLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加HosLevel
	 * @param  HosLevel entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchHosLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询HosLevel分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryHosLevel(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询HosLevelByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public HosLevel queryHosLevelByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteHosLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchHosLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateHosLevel(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchHosLevel(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入HosLevel
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importHosLevel(Map<String,Object> entityMap)throws DataAccessException;
	
}
