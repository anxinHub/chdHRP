/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.FacDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface FacDictService {

	/**
	 * @Description 添加FacDict
	 * @param FacDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addFacDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加FacDict
	 * @param  FacDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchFacDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询FacDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryFacDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询FacDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public FacDict queryFacDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除FacDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteFacDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除FacDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchFacDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新FacDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateFacDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新FacDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchFacDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入FacDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importFacDict(Map<String,Object> entityMap)throws DataAccessException;

	public String queryFacDictList(Map<String, Object> paramMap) throws DataAccessException;
	
}
