/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* <BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface CostCompareService  {
	
	
	/**
	 * @Description 
	 * <BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0502(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * <BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0506(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	
}
