package com.chd.hrp.hpm.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiDeptBonusCalculationService {
	/**
	 * 
	 */
	public String queryDeptBonusCalculation(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询奖金查询的表头
	 */
	public String queryDeptBonusCalculationGrid(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 奖金查询 打印
	 */
	public List<Map<String,Object>> queryDeptBonusCalculationPrint(Map<String, Object> entityMap) throws DataAccessException;

}
