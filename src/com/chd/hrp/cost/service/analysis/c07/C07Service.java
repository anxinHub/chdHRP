/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service.analysis.c07;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 单元分析服务类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface C07Service  {
	/**
	 * @Description 
	 * 单元分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC0701(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 单元分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0702(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 单元分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0703(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 单元分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0704(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0701Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0702Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0703Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0704Print(Map<String,Object> entityMap) throws DataAccessException;
	
}
