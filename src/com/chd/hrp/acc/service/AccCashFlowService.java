/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccCashFlow;

/**
* @Title. @Description.
* 现金流量标注<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCashFlowService {

	/**
	 * @Description 
	 * 现金流量标注<BR> 添加AccCashFlow
	 * @param AccCashFlow entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCashFlow(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 批量添加AccCashFlow
	 * @param  AccCashFlow entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCashFlow(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 查询AccCashFlow分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCashFlow(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 查询AccCashFlowByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCashFlow queryAccCashFlowByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 删除AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCashFlow(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 批量删除AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCashFlow(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 更新AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCashFlow(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 批量更新AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCashFlow(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量标注<BR> 导入AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccCashFlow(Map<String,Object> entityMap)throws DataAccessException;
	
}
