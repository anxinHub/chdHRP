package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostIncomeDetail;

public interface CostApplDeptMapper extends SqlMapper{

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
	List<CostIncomeDetail> queryCostApplDeptMain(Map<String, Object> entityMap) throws DataAccessException;

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
	List<CostIncomeDetail> queryCostApplDeptMain(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
    
	
	/**
	 * 
	* @Title: queryCostApplDeptMainPrint
	* @Description: 开单收入统计-收入采集(类别)打印
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月14日   
	* @author sjy
	 */
	List<Map<String, Object>> queryCostApplDeptMainPrint(Map<String, Object> entityMap) throws DataAccessException; 
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
	List<CostIncomeDetail> queryCostApplDeptDetail(Map<String, Object> entityMap) throws DataAccessException;
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
	List<CostIncomeDetail> queryCostApplDeptDetail(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
   /**
	* 
	* @Title: queryCostApplDeptMainPrint
	* @Description: 开单收入统计-收入采集(项目)打印
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月14日   
	* @author sjy
	 */
	List<Map<String, Object>> queryCostApplDeptDetailPrint(Map<String, Object> entityMap) throws DataAccessException; 
}
