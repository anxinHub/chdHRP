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
import com.chd.hrp.cost.entity.CostServeItemDict;


/**
* @Title. @Description.
* <BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostServeItemDictMapper extends SqlMapper{
	
	public int addCostServeItemDict(Map<String,Object> entityMap)throws DataAccessException;

	public List<CostServeItemDict> queryCostServeItemDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<CostServeItemDict> queryCostServeItemDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public CostServeItemDict queryCostServeItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchCostServeItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int updateCostServeItemDict(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostServeItemDictPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
