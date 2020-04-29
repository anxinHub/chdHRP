/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Title. @Description. 科室成本明细数据表_医辅分摊<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
public interface CostReportService { 

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
	public String  queryCostTypeDictThead(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 科室直接成本报表
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryCostDeptReport_1(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 临床服务类全成本
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryCostDeptReport_2(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 临床服务类全成本构成分析表
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryCostDeptReport_3(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostReportPrint1(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostReportPrint2(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostReportPrint3(Map<String,Object> entityMap) throws DataAccessException;
	
}
