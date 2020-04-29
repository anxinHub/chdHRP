/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCashierType;

/**
* 出纳类型<BR>
* @Version: 1.0
*/


public interface AccCashierTypeService {

	/**
	 * @Description 
	 * 出纳类型<BR> 添加AccCashierType
	 * @param AccCashierType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCashierType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 批量添加AccCashierType
	 * @param  AccCashierType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCashierType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 查询AccCashierType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCashierType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 查询AccCashierTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCashierType queryAccCashierTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 删除AccCashierType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCashierType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 批量删除AccCashierType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCashierType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 更新AccCashierType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCashierType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 批量更新AccCashierType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCashierType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 出纳类型<BR> 导入AccCashierType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccCashierType(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AccCashierType> queryAccCashierTypeByKindCode(Map<String,Object> entityMap) throws DataAccessException;
	
}
