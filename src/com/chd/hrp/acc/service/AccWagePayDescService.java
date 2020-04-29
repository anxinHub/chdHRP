/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWagePayDesc;
/**
 * 
 * @Description:
 * 
 * @Table:
 * ACC_WAGE_PAY_DESC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AccWagePayDescService{
	
	public String addAccWagePayDesc(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchAccWagePayDesc(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryAccWagePayDesc(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccWagePayDesc queryAccWagePayDescByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteAccWagePayDesc(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchAccWagePayDesc(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String updateAccWagePayDesc(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateBatchAccWagePayDesc(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
