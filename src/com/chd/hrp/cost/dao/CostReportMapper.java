/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Title. @Description. 科室成本明细数据表_医辅分摊<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
public interface CostReportMapper extends SqlMapper { 

   /**
    * 
	* @Title: queryCostTypeDictThead
	* @Description: 动态查询成本类型表头
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年11月11日 上午10:05:06
	* @author sjy
	 */
	public List<Map<String, Object>> queryCostTypeDictThead(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 科室直接成本表<BR>
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_1(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 科室直接成本表<BR>
	 * @param entityMap
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_1(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 科室直接成本表<BR>
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_1_old(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 科室直接成本表<BR>
	 * @param entityMap
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_1_old(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 临床服务类科室全成本<BR>
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_2(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 临床服务类科室全成本<BR>
	 * @param entityMap
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_2(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 临床服务类科室全成本<BR>
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_2_old(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 临床服务类科室全成本<BR>
	 * @param entityMap
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_2_old(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 临床服务类科室全成本构成分析表本<BR>
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_3(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 临床服务类科室全成本构成分析表<BR>
	 * @param entityMap
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_3(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 临床服务类科室全成本构成分析表本<BR>
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_3_old(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 临床服务类科室全成本构成分析表<BR>
	 * @param entityMap
	 * @return List<CostAssDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostDeptReport_3_old(Map<String, Object> entityMap) throws DataAccessException;



}
