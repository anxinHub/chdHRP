package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostApplDeptService {

	/**
	 * 
	* @Title: queryCostApplDeptMain
	* @Description: 开单收入统计查询-收入采集(类别)
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月14日   
	* @author sjy
	 */
	public String queryCostApplDeptMain(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	* @Title: queryCostApplDeptDetail
	* @Description: 开单收入统计查询-收入采集(项目)
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月14日   
	* @author sjy
	 */
	public String queryCostApplDeptDetail(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 
	* @Title: queryCostApplDeptPrint
	* @Description: 开单收入统计查询-打印
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月14日   
	* @author sjy
	 */
	public List<Map<String,Object>> queryCostApplDeptPrint(Map<String,Object> entityMap) throws DataAccessException;

}
