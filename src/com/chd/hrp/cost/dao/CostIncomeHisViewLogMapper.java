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
* @Title. @Description.
* 奖金项与成本项目的对应关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com 
* @Version: 1.0
*/


public interface CostIncomeHisViewLogMapper extends SqlMapper{
	
	public int addCostHisViewLog(Map<String, Object> entityMap) throws DataAccessException;
	
	public int updateCostHisViewLog(Map<String, Object> entityMap) throws DataAccessException;
	
	public int addBatchDetailCostHisViewLog(List<Map<String, Object>> list) throws DataAccessException;
	
	public int deleteBatchDetailCostHisViewLog(List<Map<String, Object>> list) throws DataAccessException;
	
	public int deleteBatchDetailCostHisViewLogMain(Map<String, Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostHisViewLog(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostHisViewLog(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	 public List<Map<String, Object>> queryCostIncomeHisViewSetting(Map<String, Object> entityMap) throws DataAccessException;
		
	public List<Map<String, Object>> queryCostIncomeHisViewSetting(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int queryCostHisViewLogById(Map<String, Object> entityMap) throws DataAccessException;
	
	public int queryCostHisViewdetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryCostHisViewInitByCode(Map<String, Object> entityMap) throws DataAccessException;
}
