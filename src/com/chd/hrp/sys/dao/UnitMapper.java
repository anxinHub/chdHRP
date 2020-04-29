/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.sys.entity.Unit;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface UnitMapper extends SqlMapper{
	
	/**
	 * @Description 添加Unit
	 * @param Unit entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addUnit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Unit
	 * @param  Unit entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchUnit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Unit分页
	 * @param  entityMap RowBounds
	 * @return List<Unit>
	 * @throws DataAccessException
	*/
	public List<Unit> queryUnit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Unit所有数据
	 * @param  entityMap
	 * @return List<Unit>
	 * @throws DataAccessException
	*/
	public List<Unit> queryUnit(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询UnitByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Unit queryUnitByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Unit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteUnit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Unit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchUnit(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Unit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateUnit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Unit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchUnit(List<Map<String, Object>> entityMap)throws DataAccessException;
}
