package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptCost;

public interface AphiDeptCostMapper extends SqlMapper{

	public List<AphiDeptCost> queryDeptCostMain(Map<String, Object> entityMap) throws DataAccessException;

	public List<AphiDeptCost> queryDeptCostMain(Map<String, Object> entityMap,
			RowBounds rowBounds) throws DataAccessException;

	public void deleteBatchDeptCostMaping(
			List<Map<String, Object>> dataAddedBatch) throws DataAccessException;

	public void addBatchPrmDeptMaping(List<Map<String, Object>> dataAddedBatch) throws DataAccessException;

}
