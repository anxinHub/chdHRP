package com.chd.hrp.prm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.PrmDeptFormulaMethod;

public interface PrmDeptFormuLaMethodMapper extends SqlMapper{
	
	public PrmDeptFormulaMethod queryPrmDeptFormuLaMethod(Map<String, Object> entityMap)throws DataAccessException;
	 /**
     * 保存计算公式
     * @param entityMap
     * @throws DataAccessException
     */
	public void savePrmDeptFormula(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryPrmDeptFormulaByCode(Map<String, Object> entityMap)throws DataAccessException;

	public void updatePrmDeptFormula(Map<String, Object> entityMap)throws DataAccessException;
	
	public int deleteBatchDeptFormuLaMethod(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public void deleteDeptFormuLaMethod(Map<String, Object> goalMap)throws DataAccessException;
	
	public List<PrmDeptFormulaMethod> queryDeptFormulaMethod(Map<String, Object> map)throws DataAccessException;
	
	public void addBatchDeptFormuLaMethod(List<Map<String, Object>> deptGoalKpiFMList)throws DataAccessException;
}
