/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service.analysis.c09;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 盘亏服务类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface C09Service  {
	/**
	 * @Description 
	 * 盘亏分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC0901(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 盘亏分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String addCostAnalysisC0901(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
	public String queryAnalysisC0902(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0902(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0903(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0903(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0904(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0904(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0905(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0905(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0906(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0906(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryC0901Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0902Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0903Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0904Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0905Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0906Print(Map<String,Object> entityMap) throws DataAccessException;
	
}
