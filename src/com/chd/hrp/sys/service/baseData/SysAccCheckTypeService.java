/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service.baseData;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCheckType;

/**
* @Title. @Description.
* 核算类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SysAccCheckTypeService {

	/**
	 * @Description 
	 * 核算类<BR> 添加AccCheckType
	 * @param AccCheckType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCheckType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 批量添加AccCheckType
	 * @param  AccCheckType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCheckType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckType(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccCheckTypeBySelect(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCheckType queryAccCheckTypeByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCheckType queryAccCheckTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 核算类<BR> 删除AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCheckType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 批量删除AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCheckType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 更新AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCheckType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 批量更新AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCheckType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 导入AccCheckType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccCheckType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询AccCheckType菜单
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckTypeByMenu(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccCheckType queryCheckType(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccCheckType queryCheckColumn(Map<String,Object> entityMap)throws DataAccessException;
	
}
