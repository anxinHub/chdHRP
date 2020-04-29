/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccWageLog;

/**
* @Title. @Description.
* 工资套合并日志<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageLogService {

	/**
	 * @Description 
	 * 工资套合并日志<BR> 添加AccWageLog
	 * @param AccWageLog entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageLog(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量添加AccWageLog
	 * @param  AccWageLog entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageLog(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageLog分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageLog(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 查询AccWageLogByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageLog queryAccWageLogByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 删除AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageLog(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量删除AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageLog(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 更新AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageLog(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 批量更新AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageLog(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并日志<BR> 导入AccWageLog
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageLog(Map<String,Object> entityMap)throws DataAccessException;

}
