package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostDoctorWork;

public interface CostDoctorWorkService {

	public String addCostDoctorWork(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryCostDoctorWork(Map<String,Object> entityMap)throws DataAccessException;
	
	public CostDoctorWork queryCostDoctorWorkByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchCostDoctorWork(List<Map<String,Object>> map)throws DataAccessException;
	
	public String updateCostDoctorWork(Map<String,Object> entityMap)throws DataAccessException;
	
	public String impCostDoctorWork(Map<String,Object> entityMap)throws DataAccessException;
}
