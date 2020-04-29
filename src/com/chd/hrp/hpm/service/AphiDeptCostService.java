package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiDeptCostService {

	public String queryDeptCostMain(Map<String, Object> page) throws DataAccessException;

	public String deleteBatchDeptCostMaping(
			List<Map<String, Object>> dataAddedBatch) throws DataAccessException;

	public String addBatchDeptCostMaping(
			List<Map<String, Object>> dataAddedBatch) throws DataAccessException;

	public String deleteHpmDeptCost(List<Map<String, Object>> listVo) throws DataAccessException;

}
