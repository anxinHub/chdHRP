/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Unit;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface UnitService {

	/**
	 * @Description 添加Unit
	 * @param Unit entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addUnit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Unit
	 * @param  Unit entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchUnit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Unit分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryUnit(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询UnitByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Unit queryUnitByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteUnit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchUnit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateUnit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchUnit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Unit
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importUnit(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Unit> queryUnitList(Map<String,Object> entityMap) throws DataAccessException;
}
