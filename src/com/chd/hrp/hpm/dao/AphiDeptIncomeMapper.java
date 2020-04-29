package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptIncome;

public interface AphiDeptIncomeMapper extends SqlMapper{

	public List<AphiDeptIncome> queryDeptIncomeMain(Map<String, Object> entityMap) throws DataAccessException;

	public List<AphiDeptIncome> queryDeptIncomeMain(
			Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public void deleteBatchDeptIncomeMaping(
			List<Map<String, Object>> dataAddedBatch) throws DataAccessException;

	public void addBatchPrmDeptMaping(List<Map<String, Object>> dataAddedBatch) throws DataAccessException;

}
