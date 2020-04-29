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
import com.chd.hrp.cost.entity.CostIncome;

/**
* @Title. @Description.
* 收入归集
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostInComeCollectionMapper extends SqlMapper{
	
	

	public List<CostIncome> queryIncomeCollection(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
	public List<CostIncome> queryIncomeCollection(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<Map<String, Object>> queryIncomeCollectionPrmHead(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryIncomeCollectionPrm(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryIncomeCollectionPrm(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<Map<String, Object>> queryIncomeDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryIncomeMain(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostInComeCollectionPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostCollectionMainCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostCollectionMainCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostCollectionDetailCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostCollectionDetailCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
}
