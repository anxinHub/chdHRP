
package com.chd.hrp.acc.dao.commonbuilder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCashierType;

/**
* 出纳类型<BR>
*/


public interface AccCashierTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 出纳类型<BR> 添加AccCashierType
	 * @param AccVouchType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCashierType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 批量添加AccCashierType
	 * @param  AccVouchType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCashierType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 查询AccCashierType分页
	 * @param  entityMap RowBounds
	 * @return List<AccVouchType>
	 * @throws DataAccessException
	*/
	public List<AccCashierType> queryAccCashierType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 出纳类型<BR> 查询AccCashierType所有数据
	 * @param  entityMap
	 * @return List<AccVouchType>
	 * @throws DataAccessException
	*/
	public List<AccCashierType> queryAccCashierType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 查询AccCashierTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCashierType queryAccCashierTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 删除AccCashierType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCashierType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 批量删除AccCashierType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCashierType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 出纳类型<BR> 更新AccCashierType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCashierType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 批量更新AccCashierType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCashierType(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int queryAccCashierTypeExitsByCode(Map<String, Object> entityMap);

	public int queryAccCashierTypeExitsByName(Map<String, Object> entityMap);
	
	public List<AccCashierType> queryAccCashierTypeByKindCode(Map<String,Object> entityMap) throws DataAccessException;
}
