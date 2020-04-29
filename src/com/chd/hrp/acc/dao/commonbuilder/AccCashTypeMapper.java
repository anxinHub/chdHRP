/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.commonbuilder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCashType;

/**
* @Title. @Description.
* 现金流量类别<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCashTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 添加AccCashType
	 * @param AccCashType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCashType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 批量添加AccCashType
	 * @param  AccCashType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCashType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 查询AccCashType分页
	 * @param  entityMap RowBounds
	 * @return List<AccCashType>
	 * @throws DataAccessException
	*/
	public List<AccCashType> queryAccCashType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 现金流量类别<BR> 查询AccCashType所有数据
	 * @param  entityMap
	 * @return List<AccCashType>
	 * @throws DataAccessException
	*/
	public List<AccCashType> queryAccCashType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 查询AccCashTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCashType queryAccCashTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 删除AccCashType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCashType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 批量删除AccCashType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCashType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 现金流量类别<BR> 更新AccCashType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCashType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 批量更新AccCashType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCashType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR>  继承数据  查询AccCashType数据
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<AccCashType> queryAccCashTypeByExtend(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询 现金流类别表 最大Id
	 * @return
	 */
	public AccCashType queryMaxId() throws DataAccessException;
	/**
	 * 现金流量类 继承数据批量添加
	 * @param mapList
	 */
	public void insertBatchAccCashType(List<Map<String, Object>> mapList);

	public AccCashType queryAccCashTypeByCodeName(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccCashType queryAccCashTypeByName(Map<String,Object> entityMap)throws DataAccessException;
	
}
