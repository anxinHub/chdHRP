/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service;

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
public interface CostEarningsService {

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
	
	public String queryAnalysisC0102(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结余分析服务类<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public String queryAnalysisC0103(Map<String,Object> entityMap) throws DataAccessException;
	public String queryAnalysisC0104(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * 医疗收入各级成本收益表
	 * */
	public List<Map<String, Object>>  queryAnalysisC0101print(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/*
	 * 科室医疗收益明细表
	 * */
	public List<Map<String, Object>>  queryAnalysisC0104print(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/*
	 * 临床服务类科室医疗收益明细表
	 * */
	public List<Map<String, Object>>  queryAnalysisC0103print(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/*
	 * 医院收入各级成本收益表
	 * */
	public List<Map<String, Object>>  queryAnalysisC0102print(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
