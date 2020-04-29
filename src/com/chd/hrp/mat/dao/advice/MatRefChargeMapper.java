package com.chd.hrp.mat.dao.advice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatRefCharge;

public interface MatRefChargeMapper extends SqlMapper{
	public int addMatRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchMatRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int updateMatRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchMatRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteMatRefCharge(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteBatchMatRefCharge(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<MatRefCharge> queryMatRefCharge(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<MatRefCharge> queryMatRefCharge(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public MatRefCharge queryMatRefChargeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public MatRefCharge queryMatRefChargeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
