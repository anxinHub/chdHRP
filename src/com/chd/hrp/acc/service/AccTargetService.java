/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface AccTargetService {

	public String addAccTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchAccTarget(List<Map<String, Object>> list)throws DataAccessException;
	
    public String updateAccTarget(Map<String,Object> entityMap)throws DataAccessException;
    
    public String saveAccTargetData(Map<String,Object> entityMap)throws DataAccessException;
    
    public String saveBatchAccTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchAccTarget(List<Map<String, Object>> list)throws DataAccessException;
	
	public String deleteBatchAccTargetData(List<Map<String, Object>> list)throws DataAccessException;
	
	public String queryAccTarget(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryAccTargetByCode(Map<String,Object> entityMap) throws DataAccessException; 
	
	public Map<String, Object> queryAccTargetDataByCode(Map<String,Object> entityMap) throws DataAccessException; 
	
    public String inheritAccTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addImportAccTargetData(List<Map<String, Object>> list)throws DataAccessException;
	
	
	
	
	
}
