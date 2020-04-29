/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service.baseData;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCheckItemType;
import com.chd.hrp.acc.entity.AccCheckType;

/**
* @Title. @Description.
* 核算分类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SysAccCheckItemTypeService {

	/**
	 * @Description 
	 * 核算分类<BR> 添加AccCheckItemType
	 * @param AccCheckItemType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCheckItemType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 批量添加AccCheckItemType
	 * @param  AccCheckItemType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCheckItemType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCheckItemType(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccCheckItemTypePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccCheckItemTypeBySelect(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public int queryAccCheckItemTypeByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCheckItemType queryAccCheckItemTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 核算分类<BR> 删除AccCheckItemType
	 * @param  listVo 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCheckItemType(List<Map<String, Object>> listVo)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 批量删除AccCheckItemType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCheckItemType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 更新AccCheckItemType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCheckItemType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 批量更新AccCheckItemType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCheckItemType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 导入AccCheckItemType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccCheckItemType(Map<String,Object> entityMap)throws DataAccessException;
	
}
