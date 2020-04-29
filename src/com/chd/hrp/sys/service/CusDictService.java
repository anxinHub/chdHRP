/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.CusDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CusDictService {

	/**
	 * @Description 添加CusDict
	 * @param CusDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCusDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加CusDict
	 * @param  CusDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCusDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询CusDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCusDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryCusDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询CusDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CusDict queryCusDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCusDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCusDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCusDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCusDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入CusDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importCusDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryCusDictBySelector(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 用于集团化管理   集团化客户选择器
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryGroupCusDictBySelector(Map<String,Object> entityMap)throws DataAccessException;

	public String queryCusDictList(Map<String, Object> paramMap) throws DataAccessException;
	
}
