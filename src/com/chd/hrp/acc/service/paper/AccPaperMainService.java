/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.paper;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;





public interface AccPaperMainService {

	public String addAccPaperMain(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchAccPaperMain(List<Map<String, Object>> list)throws DataAccessException;
	
    public String updateAccPaperMain(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchAccPaperMain(List<Map<String, Object>> list)throws DataAccessException;
	
	public String queryAccPaperMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperMainPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryAccPaperMainByCode(Map<String,Object> entityMap) throws DataAccessException; 
	
	
	

	
	
	
	
}
