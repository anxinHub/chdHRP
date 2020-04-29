/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service.analysis.c01;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 结余分析服务类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface C01Service {

	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC0101(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 * 
	 * 
	 * 
	 */
	
	/**
	 * @Description 
	 * 分类析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String addCostAnalysisC0101(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String queryAnalysisC0102(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0102(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0103(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0103(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0104(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0104(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0105(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0105(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0106(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0106(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0107(Map<String,Object> entityMap) throws DataAccessException;
	public String addCostAnalysisC0107(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0108(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0109(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0110(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0111(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0112(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0113(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0114(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0115(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0116(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0117(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0101Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0102Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0103Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0104Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0105Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0106Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0107Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0108Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0109Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0110Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0111Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0112Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0113Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0114Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0115Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0116Print(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryC0117Print(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
