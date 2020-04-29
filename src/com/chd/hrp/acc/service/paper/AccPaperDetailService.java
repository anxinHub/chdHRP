/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.paper;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;





public interface AccPaperDetailService {

    
	public String queryAccPaperDetail(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperDetailUseOnePrint(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperDetailUseTwoPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryexistAccPaperDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public String updateBatchAccPaperDetail(List<Map<String, Object>> list,String getMessage) throws DataAccessException;
	
	public String updateBatchAccPaperDetail(List<Map<String, Object>> list) throws DataAccessException;
	
	
	public String deleteBatchAccPaperDetail(List<Map<String, Object>> list) throws DataAccessException;
	
	
	//票据管理查询
	public String queryAccPaperDetailManage(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperDetailManagePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//单张票据核销查询
	public String queryAccPaperDetailSola(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccPaperDetailSolaPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	
	
}
