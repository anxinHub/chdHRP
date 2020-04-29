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
import com.chd.hrp.cost.entity.CostDeptDriData;

/**
* @Title. @Description.
* 科室成本成本归集
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostCollectionMapper extends SqlMapper{
	
	

	/*
	 * 查询科室成本表
	 * */
	public List<CostDeptDriData> queryCostCollection(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;


	public List<Map<String, Object>> queryCostCollectionPrmHead(Map<String,Object> entityMap) throws DataAccessException;
	/*
	 * 查询科室成本表
	 * */
	public List<Map<String, Object>> queryCostCollectionPrm(Map<String,Object> entityMap) throws DataAccessException;
	/*
	 * 查询科室成本表
	 * */
	public List<Map<String, Object>> queryCostCollectionPrm(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/*
	 * 查询科室成本表
	 * */
	public List<CostDeptDriData> queryCostCollection(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/*
	 * 查询支出采集数据来源于财务
	 * */
	public List<Map<String,Object>> queryCostCollectionBusiAcc(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/*
	 * 查询支出采集数据来源于业务数据
	 * */
	public List<Map<String,Object>> queryCostCollectionBusiTran(Map<String,Object> entityMap) throws DataAccessException;
	
	/*
	 * 动态查询人员工资项
	 * */
	public java.lang.String queryCostCollectionWage();
	/*
	 * 动态查询人员奖金项
	 * */
	public java.lang.String queryCostCollectionBonus();


	public List<Map<String, Object>> queryCostCollectionPrint(
			Map<String, Object> entityMap)throws DataAccessException;
	
	
}
