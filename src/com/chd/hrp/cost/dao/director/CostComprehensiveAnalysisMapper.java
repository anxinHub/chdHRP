package com.chd.hrp.cost.dao.director;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface CostComprehensiveAnalysisMapper extends SqlMapper{

    public List<Map<String, Object>> queryCostDepartmentOperation(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostDepartmentOperation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

    public List<Map<String, Object>> queryCostGeneralMessage(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostGeneralMessage(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostGeneralMessageDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostGeneralMessageDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostGeneralMessageMed(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostGeneralMessageMed(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostGeneralMessageMedDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostGeneralMessageMedDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostGeneralMessage_t1(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostGeneralMessage_t2(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostGeneralMessage_t3(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostGeneralMessageProfit(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostGeneralMessageProfit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostGeneralMessageProfitClinic(Map<String,Object> entityMap) throws DataAccessException;
		
    public List<Map<String, Object>> queryCostGeneralMessageProfitClinic(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

    public List<Map<String, Object>> queryCostGeneralMessageProfitClinicCost(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostGeneralMessageProfitClinicCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    public List<Map<String, Object>> queryCostGeneralMessageProfitInhos(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostGeneralMessageProfitInhos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    public List<Map<String, Object>> queryCostGeneralMessageProfitInhosCost(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostGeneralMessageProfitInhosCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    public List<Map<String, Object>> queryCostGeneralMessageProfitMedical(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostGeneralMessageProfitMedical(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

    public List<Map<String, Object>> queryCostDirect(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostDirect(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
    public List<Map<String, Object>> queryCostDirectIncomeKind(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryCostDirectIncomeKind(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncome(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
	public List<Map<String, Object>> queryMultiIncome(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeByDeptDir(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeByDeptDir(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeTotalCost(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeTotalCost(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeChangeCostDir(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeChangeCostDir(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeDirectCostDir(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeDirectCostDir(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeByChargeType(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryMultiIncomeByChargeType(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryCostDepartmentOperationPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryCostDirectPrint(Map<String, Object> entityMap)throws DataAccessException;

}
