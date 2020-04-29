/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccWagePayModi;

/**
* @Title. @Description.
* 工资调整<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWagePayModiService {

	/**
	 * @Description 
	 * 工资调整<BR> 添加AccWagePayModi
	 * @param AccWagePayModi entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWagePayModi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 批量添加AccWagePayModi
	 * @param  AccWagePayModi entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWagePayModi(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 查询AccWagePayModi分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWagePayModi(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 查询AccWagePayModiByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWagePayModi queryAccWagePayModiByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 删除AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWagePayModi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 批量删除AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWagePayModi(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 更新AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWagePayModi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 批量更新AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWagePayModi(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资调整<BR> 导入AccWagePayModi
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWagePayModi(Map<String,Object> entityMap)throws DataAccessException;

}
