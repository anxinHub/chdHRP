/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.paper; 
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

 



public interface AccPaperTypeService {

	public String addAccPaperType(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchAccPaperType(List<Map<String, Object>> list)throws DataAccessException;
	
    public String updateAccPaperType(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchAccPaperType(List<Map<String, Object>> list)throws DataAccessException;
	
	public String queryAccPaperType(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperTypeSearch(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperTypePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryAccPaperTypeByCode(Map<String,Object> entityMap) throws DataAccessException; 
	
	
	

	
	
	
	
}
