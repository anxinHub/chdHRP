package com.chd.hrp.budg.service.base.costdutydept;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface BudgCostDutyDeptService  extends SqlService{

	/**
	 * 继承
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String copyBudgCostDutyDept(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 归口科室名称下拉框 部门字典属性表 部门字典属性表 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAccDeptAttr(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询部门字典属性表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAccDeptAttrData(Map<String,Object> entityMap) throws DataAccessException;

	Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException;

	Map<String, Object> queryAccDeptAttrExit(Map<String, Object> mapVo)throws DataAccessException;

	int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException;


}
