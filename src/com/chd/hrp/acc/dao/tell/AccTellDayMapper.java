/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.tell;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccTellDay;


/**
* @Title. @Description.
* <BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTellDayMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * <BR> 添加AccTellDay
	 * @param AccTell entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccTellDay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR> 批量添加AccTellDay
	 * @param  AccTell entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccTellDay(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * <BR> 查询AccTellDay所有数据
	 * @param  entityMap
	 * @return List<AccTellDay>
	 * @throws DataAccessException
	*/
	public List<AccTellDay> queryAccTellDay(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * <BR> 查询AccTellDayByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccTellDay queryAccTellDayByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR> 删除AccTellDay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccTellDay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR> 批量删除AccTellDay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccTellDay(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * <BR> 更新AccTellDay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccTellDay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * <BR> 批量更新AccTellDay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccTellDay(List<Map<String, Object>> entityMap)throws DataAccessException;

}
