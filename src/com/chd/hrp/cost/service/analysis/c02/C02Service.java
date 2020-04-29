/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service.analysis.c02;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 构成结余分析服务类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface C02Service {
	
	/**
	 * @Description 
	 * 全院成本构成比例查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC0200(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 构成分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC0201(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String addCostAnalysisC0201(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	public String queryAnalysisC0202(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0203(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0204(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0205(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0206(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0207(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0208(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0209(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0210(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0211(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0212(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0213(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0214(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0215(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0216(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0217(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0218(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0219(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0220(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0221(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0222(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0223(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0224(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0225(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0226(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0227(Map<String,Object> entityMap) throws DataAccessException;  
	
	public String queryAnalysisC0228(Map<String,Object> entityMap) throws DataAccessException; 
	
	public String queryAnalysisC0229(Map<String,Object> entityMap) throws DataAccessException; 
	
	public List<Map<String,Object>> queryC0201Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0202Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0203Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0204Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0205Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0206Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0207Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0208Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0209Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0210Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0211Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0212Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0213Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0214Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0215Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0220Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0221Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0222Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0223Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0224Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0225Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0226Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0227Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0228Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0229Print(Map<String,Object> entityMap) throws DataAccessException;
	
}
