package com.chd.hrp.mat.service.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.MatRefCharge;

public interface MatRefChargeService {
	public String addMatRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addBatchMatRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateMatRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String updateBatchMatRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String deleteMatRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String deleteBatchMatRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String queryMatRefCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public MatRefCharge queryMatRefChargeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatRefCharge queryMatRefChargeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
