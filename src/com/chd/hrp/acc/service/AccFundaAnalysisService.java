/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface AccFundaAnalysisService {
	
	//更新方法
	public String updateAccFundaAnalysis(Map<String,Object> entityMap)throws DataAccessException;

	//保存方法
	public String addAccFundaAnalysis(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询方法
	public String queryAccFundaAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	//打印的查询方法
	public List<Map<String, Object>> queryAccFundaPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//根据bas_code查询
	public Map<String, Object> queryAccFundaByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	//删除方法
	public String deleteBatchAccFunda(List<Map<String, Object>> list) throws DataAccessException;
}
