package com.chd.hrp.acc.service.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccDeptAuxiliaryAccountService {
	public String collectDeptSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccDeptSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccSubjDeptGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectAccSubjDeptDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectAccDeptBalance(Map<String,Object> entityMap) throws DataAccessException;
	
	//部门科目总账  普通打印
	public List<Map<String, Object>> collectDeptSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//部门科目总账  模板打印
	public  Map<String, Object>  collectDeptSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//部门科目明细账  普通打印
	public List<Map<String, Object>> collectAccDeptSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//部门科目明细账 模板打印
	public Map<String, Object> collectAccDeptSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目部门总账  普通打印
	public List<Map<String, Object>> collectAccSubjDeptGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目部门总账  模板打印
	public Map<String, Object> collectAccSubjDeptGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目部门明细账  普通打印
	public List<Map<String, Object>> collectAccSubjDeptDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目部门明细账  模板打印
	public Map<String, Object> collectAccSubjDeptDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//部门余额表  普通打印
	public List<Map<String, Object>> collectAccDeptBalancePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//部门余额表  模板打印
	public Map<String, Object> collectAccDeptBalancePrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
