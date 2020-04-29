package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostExecDeptService {

	 /**
	* 
	* @Title: queryCostExecSum
	* @Description: 执行收入统计查询-收费类别
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月14日   
	* @author sjy
	 */
	public String queryCostExecDeptMain(Map<String,Object> entityMap) throws DataAccessException;
	
   /**
	* 
	* @Title: queryCostExecSum
	* @Description: 执行收入统计查询-收费项目
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月14日   
	* @author sjy
	 */
	public String queryCostExecDeptDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	
   /**
	* 
	* @Title: queryCostExecSum
	* @Description: 执行收入统计打印
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月14日   
	* @author sjy
	 */
	public List<Map<String,Object>> queryCostExecDeptPrint(Map<String,Object> entityMap) throws DataAccessException;
}
