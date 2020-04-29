/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.CusType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CusTypeService {

	/**
	 * @Description 添加CusType
	 * @param CusType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCusType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加CusType
	 * @param  CusType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCusType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询CusType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCusType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询CusTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CusType queryCusTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCusType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCusType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCusType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCusType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入CusType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importCusType(Map<String,Object> entityMap)throws DataAccessException;
	
}
