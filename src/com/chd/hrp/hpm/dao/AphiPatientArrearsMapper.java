package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AphiPatientArrearsMapper extends SqlMapper {

	/**
	 * 查询
	 * 
	 * @return
	 */
	public Map<String, Object> queryAphiDeptTargerByTargetCode(Map<String, Object> entityMap)
			throws DataAccessException;

	public void deleteBatchPatientArrears(Map<String, Object> deleteMap);

	public void addBatchPatientArrears(List<Map<String, Object>> addList);
}
