/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.FacType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface FacTypeService {

	/**
	 * @Description 添加FacType
	 * @param FacType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addFacType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加FacType
	 * @param  FacType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchFacType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询FacType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryFacType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询FacTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public FacType queryFacTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除FacType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteFacType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除FacType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchFacType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新FacType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateFacType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新FacType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchFacType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入FacType  --老版导入更换新版使用
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importFacType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String importReadFacTypeFiles(Map<String,Object> entityMap)throws DataAccessException;
}
