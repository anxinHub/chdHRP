/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.HosType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HosTypeService {

	/**
	 * @Description 添加HosType
	 * @param HosType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addHosType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加HosType
	 * @param  HosType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchHosType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询HosType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryHosType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询HosTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public HosType queryHosTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteHosType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchHosType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateHosType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchHosType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入HosType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importHosType(Map<String,Object> entityMap)throws DataAccessException;
	
}
