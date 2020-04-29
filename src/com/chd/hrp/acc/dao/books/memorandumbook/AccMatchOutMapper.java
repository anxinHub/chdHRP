/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccMatchOut;

/**
* @Title. @Description.
* 配套资金支出<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccMatchOutMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 配套资金支出<BR> 添加AccMatchOut
	 * @param AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccMatchOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出<BR> 批量添加AccMatchOut
	 * @param  AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccMatchOut(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出<BR> 查询AccMatchOut分页
	 * @param  entityMap RowBounds
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public List<AccMatchOut> queryAccMatchOut(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 配套资金支出<BR> 查询AccMatchOut所有数据
	 * @param  entityMap
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public List<AccMatchOut> queryAccMatchOut(Map<String,Object> entityMap) throws DataAccessException;

	//配套资金支出   打印
	public List<Map<String, Object>> queryAccMatchOutPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出<BR> 查询AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMatchOut queryAccMatchOutByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出<BR> 删除AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccMatchOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出<BR> 批量删除AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccMatchOut(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 配套资金支出<BR> 更新AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccMatchOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金支出<BR> 批量更新AccMatchOut
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccMatchOut(List<Map<String, Object>> entityMap)throws DataAccessException;
}
