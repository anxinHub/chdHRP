package com.chd.hrp.budg.dao.base.costdutydept;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description: 支出预算归口设置 
 * @Table: BUDG_COST_DUTY_DEPT
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgCostDutyDeptMapper extends SqlMapper{
	public List<Map<String, Object>> queryAccDeptAttr(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccDeptAttrData(Map<String, Object> entityMap)throws DataAccessException;

	public int copyBudgCostDutyDept(Map<String, Object> entityMap)throws DataAccessException;

	public Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException;

	public Map<String, Object> queryAccDeptAttrExit(Map<String, Object> mapVo) throws DataAccessException;

	public int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException;

}
