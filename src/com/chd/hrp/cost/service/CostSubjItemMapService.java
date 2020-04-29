/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostSubjItemMap;
 

public interface CostSubjItemMapService {

	public String addCostSubjItemMap(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchCostSubjItemMap(List<Map<String, Object>> list)throws DataAccessException;

	public String deleteCostSubjItemMap(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchCostSubjItemMap(List<Map<String, Object>> list)throws DataAccessException;

	public String updateCostSubjItemMap(Map<String,Object> entityMap)throws DataAccessException;

	public String updateBatchCostSubjItemMap(List<Map<String, Object>> list)throws DataAccessException;
	
    public CostSubjItemMap queryCostSubjItemMapByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostSubjItemMap(Map<String,Object> entityMap) throws DataAccessException;

	
}
