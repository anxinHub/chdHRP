package com.chd.hrp.acc.service.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface GroupAccDeptAuxiliaryAccountService {
	public String collectGroupAccDeptSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccDeptSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccSubjDeptGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectGroupAccSubjDeptDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectGroupAccDeptBalance(Map<String,Object> entityMap) throws DataAccessException;
	
	//部门科目总账  普通打印
	public List<Map<String, Object>> collectGroupAccDeptSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//部门科目总账  模板打印
	public  Map<String, Object>  collectGroupAccDeptSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//部门科目明细账  普通打印
	public List<Map<String, Object>> collectGroupAccDeptSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//部门科目明细账 模板打印
	public Map<String, Object> collectGroupAccDeptSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目部门总账  普通打印
	public List<Map<String, Object>> collectGroupAccSubjDeptGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目部门总账  模板打印
	public Map<String, Object> collectGroupAccSubjDeptGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目部门明细账  普通打印
	public List<Map<String, Object>> collectGroupAccSubjDeptDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目部门明细账  模板打印
	public Map<String, Object> collectGroupAccSubjDeptDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//部门余额表  普通打印
	public List<Map<String, Object>> collectGroupAccDeptBalancePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//部门余额表  模板打印
	public Map<String, Object> collectGroupAccDeptBalancePrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
