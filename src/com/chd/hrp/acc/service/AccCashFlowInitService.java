/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCashFlowInit;

/**
* @Title. @Description.
* 现金流量初始帐<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCashFlowInitService {

	/**
	 * @Description 
	 * 现金流量初始帐<BR> 添加AccCashFlow
	 * @param AccCashFlow entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 批量添加AccCashFlow
	 * @param  AccCashFlow entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCashFlowInit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 查询AccCashFlow分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCashFlowInit(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccCashFlowInitPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 查询AccCashFlowByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCashFlowInit queryAccCashFlowInitByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 删除AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 批量删除AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCashFlowInit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 更新AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 批量更新AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCashFlowInit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量初始帐<BR> 导入AccCashFlow
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccCashFlowInit(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * grid 会计科目下拉框查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccSubj(Map<String, Object> entityMap)throws DataAccessException;
	
}
