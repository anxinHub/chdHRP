/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service.analysis.c03;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 分类分析服务类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface C03Service  {
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC0301(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String addCostAnalysisC0301(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0302(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0303(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0304(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0305(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0306(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0307(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0308(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0301Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0302Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0303Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0304Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0305Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0306Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0307Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0308Print(Map<String,Object> entityMap) throws DataAccessException;
	
}
