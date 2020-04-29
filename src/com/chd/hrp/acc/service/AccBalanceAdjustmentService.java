/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccBankCheck;




public interface AccBalanceAdjustmentService {

	public String addAccBalanceAdjustment(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchAccBalanceAdjustment(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryAccBalanceAdjustment(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccBankCheck queryAccBalanceAdjustmentByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryDailyAccBalanceAdjustment(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryUndailyAccBalanceAdjustment(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccBalanceAdjustmentByMonth(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryDailyAccBalanceAdjustmentByMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryUndailyAccBalanceAdjustmentByMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchAccBalanceAdjustment(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String updateAccBalanceAdjustment(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateBatchAccBalanceAdjustment(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
}
