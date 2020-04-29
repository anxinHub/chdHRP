/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service.analysis.c08;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 指标分析服务类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface C08Service  {
	
	
	/**
	 * @Description 
	 * 医院工作量情况<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC0800(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 指标分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC0801(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 指标分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0802(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0801Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0802Print(Map<String,Object> entityMap) throws DataAccessException;
}
