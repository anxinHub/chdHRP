/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.emp;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWageType;

/**
* @Title. @Description.
* 账户类别<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageTypeService {

	/**
	 * @Description 
	 * 账户类别<BR> 添加AccWageType
	 * @param AccWageType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 批量添加AccWageType
	 * @param  AccWageType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 查询AccWageType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 查询AccWageTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageType queryAccWageTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 删除AccWageType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 批量删除AccWageType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 更新AccWageType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 账户类别<BR> 批量更新AccWageType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public AccWageType queryAccWageTypeByUpdate(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 账户类别打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccWageTypePrint(Map<String, Object> entityMap) throws DataAccessException;
	
}
