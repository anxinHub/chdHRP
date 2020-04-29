/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageLog;

/**
* @Title. @Description.
* 工资套合并日志<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageLogMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 添加AccWageLog
	 * @param AccWageLog entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageLog(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量添加AccWageLog
	 * @param  AccWageLog entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageLog(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageLog分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageLog>
	 * @throws DataAccessException
	*/
	public List<AccWageLog> queryAccWageLog(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageLog所有数据
	 * @param  entityMap
	 * @return List<AccWageLog>
	 * @throws DataAccessException
	*/
	public List<AccWageLog> queryAccWageLog(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageLogByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageLog queryAccWageLogByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 删除AccWageLog
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageLog(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量删除AccWageLog
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageLog(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资套合并日志<BR> 更新AccWageLog
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageLog(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量更新AccWageLog
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageLog(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
