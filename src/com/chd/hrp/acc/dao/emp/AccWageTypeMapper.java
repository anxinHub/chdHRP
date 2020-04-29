/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.emp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageType;

/**
* @Title. @Description.
* 账户类别<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 账户类别<BR> 添加AccWageType
	 * @param AccWageType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 批量添加AccWageType
	 * @param  AccWageType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 查询AccWageType分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageType>
	 * @throws DataAccessException
	*/
	public List<AccWageType> queryAccWageType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 账户类别<BR> 查询AccWageType所有数据
	 * @param  entityMap
	 * @return List<AccWageType>
	 * @throws DataAccessException
	*/
	public List<AccWageType> queryAccWageType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 查询AccWageTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageType queryAccWageTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 删除AccWageType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 批量删除AccWageType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 账户类别<BR> 更新AccWageType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 批量更新AccWageType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageType(List<Map<String, Object>> entityMap)throws DataAccessException;

	public AccWageType queryAccWageTypeByUpdate(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 账户类别打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccWageTypePrint(Map<String, Object> entityMap) throws DataAccessException;
	
}
