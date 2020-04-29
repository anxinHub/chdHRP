package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostDoctorWork;

public interface CostDoctorWorkMapper extends SqlMapper{

	public int addCostDoctorWork(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchCostDoctorWork(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<CostDoctorWork> queryCostDoctorWork(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<CostDoctorWork> queryCostDoctorWork(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public CostDoctorWork queryCostDoctorWorkByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchCostDoctorWork(List<Map<String,Object>> map)throws DataAccessException;
	
	public int updateCostDoctorWork(Map<String,Object> entityMap)throws DataAccessException;
}
