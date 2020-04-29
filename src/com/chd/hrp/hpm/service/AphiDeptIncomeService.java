package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiDeptIncomeService {

	public String queryDeptIncomeMain(Map<String, Object> page) throws DataAccessException;

	public String deleteBatchDeptIncomeMaping(
			List<Map<String, Object>> dataAddedBatch) throws DataAccessException;

	public String addBatchDeptIncomeMaping(
			List<Map<String, Object>> dataAddedBatch) throws DataAccessException;

	public String deleteHpmDeptIncome(List<Map<String, Object>> listVo) throws DataAccessException;


}
