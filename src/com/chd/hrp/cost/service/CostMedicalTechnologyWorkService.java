/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostMedicalTechnologyWork;


public interface CostMedicalTechnologyWorkService { 


	public String addCostMedicalTechnologyWork(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryCostMedicalTechnologyWork(Map<String, Object> mapVo) throws DataAccessException;
	
	public CostMedicalTechnologyWork queryCostMedicalTechnologyWorkByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	public String deleteCostMedicalTechnologyWork(Map<String, Object> mapVo) throws DataAccessException;
	
	public String deleteBatchCostMedicalTechnologyWork(List<Map<String, Object>> list) throws DataAccessException;
	
	public String updateCostMedicalTechnologyWork(Map<String, Object> mapVo) throws DataAccessException;
	
	public String ImpCostMedicalTechnologyWork(Map<String, Object> mapVo) throws DataAccessException;
	
	
}
