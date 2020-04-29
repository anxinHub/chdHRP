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
import com.chd.hrp.cost.entity.CostSubjItemMap;
import com.chd.hrp.cost.entity.CostBonusSchemeSet;
import com.chd.hrp.cost.entity.HrpCostSelect;



public interface CostSubjItemMapMapper extends SqlMapper{ 
	
	public int addCostSubjItemMap(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchCostSubjItemMap(List<Map<String, Object>> list)throws DataAccessException;

	public int deleteCostSubjItemMap(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchCostSubjItemMap(List<Map<String, Object>> list)throws DataAccessException;

	public int updateCostSubjItemMap(Map<String,Object> entityMap)throws DataAccessException;

	public int updateBatchCostSubjItemMap(List<Map<String, Object>> list)throws DataAccessException;
	
    public CostSubjItemMap queryCostSubjItemMapByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<CostSubjItemMap> queryCostSubjItemMap(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<CostSubjItemMap> queryCostSubjItemMap(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
