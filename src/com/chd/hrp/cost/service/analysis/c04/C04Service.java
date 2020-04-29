/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service.analysis.c04;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 习性分析服务类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface C04Service  {
	/**
	 * @Description 
	 * 习性分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC0401(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String addAnalysisC0401(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 习性分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0402(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 习性分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0403(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String addAnalysisC0403(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 习性分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0404(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0401Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0403Print(Map<String,Object> entityMap) throws DataAccessException;
	
}
