package com.chd.hrp.ass.dao.adjustMan;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssAdjustMapper extends SqlMapper{

	public List<?> queryAssList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<?> queryAssList(Map<String, Object> entityMap) throws DataAccessException;

	public Long queryCurrentSequence() throws DataAccessException;

	public void addBatchAssAdjustDetail(List<Map<String, Object>> allDataList) throws DataAccessException;

	public List<?> queryAssAdjustDetailByCode(Map<String, Object> entityMap) throws DataAccessException;

	public List<?> queryAssAdjustDetailByCode(Map<String, Object> entityMap,
			RowBounds rowBounds) throws DataAccessException;

	public void deleteBatchAssAdjustDetail(List<Map<String, Object>> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryBatchAssAdjustDetailByCode(  
			List<Map<String, Object>> entityMap) throws DataAccessException;

	public void updateAssAdjustState(List<Map<String, Object>> entityMap)  throws DataAccessException;

	public void updateBatchAssPrice(List<Map<String, Object>> list) throws DataAccessException;

	public void updateBatchAssDictPlanPrice(List<Map<String, Object>> list) throws DataAccessException;

	public void updateBatchAssIsStop(Map<String, Object> vMap) throws DataAccessException;

}
