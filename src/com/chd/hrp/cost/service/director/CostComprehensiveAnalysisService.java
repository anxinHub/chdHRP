package com.chd.hrp.cost.service.director;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostComprehensiveAnalysisService {

	public String queryCostDepartmentOperation(Map<String, Object> entityMap)throws DataAccessException;
	
    public String queryCostGeneralMessage(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessageDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessageMed(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessageMedDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessage_t1(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessage_t2(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessage_t3(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessageProfit(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessageProfitClinic(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessageProfitClinicCost(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessageProfitInhos(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessageProfitInhosCost(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralMessageProfitMedical(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostDirect(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostDirectIncomeKind(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryMultiIncome(Map<String,Object> entityMap) throws DataAccessException;

	public String queryMultiIncomeByDeptDir(Map<String, Object> entityMap)throws DataAccessException;

	public String queryMultiIncomeTotalCost(Map<String, Object> entityMap)throws DataAccessException;

	public String queryMultiIncomeChangeCostDir(Map<String, Object> entityMap)throws DataAccessException;

	public String queryMultiIncomeDirectCostDir(Map<String, Object> entityMap)throws DataAccessException;

	public String queryMultiIncomeByChargeType(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryCostDepartmentOperationPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostDirectPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
