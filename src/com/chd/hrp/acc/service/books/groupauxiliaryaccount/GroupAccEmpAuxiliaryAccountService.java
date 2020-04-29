package com.chd.hrp.acc.service.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface GroupAccEmpAuxiliaryAccountService {
	
	public String collectGroupAccEmpSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccEmpSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccSubjEmpGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectGroupAccSubjEmpDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectGroupAccEmpEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//职工科目总账 普通打印
	public List<Map<String, Object>> collectGroupAccEmpSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//职工科目总账 模板打印
	public Map<String, Object> collectGroupAccEmpSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//职工科目明细账 普通打印
	public List<Map<String, Object>> collectGroupAccEmpSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//职工科目明细账 模板打印
	public Map<String, Object> collectGroupAccEmpSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目职工总账 普通打印
	public List<Map<String, Object>>  collectGroupAccSubjEmpGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目职工总账  模板打印
	public Map<String, Object> collectGroupAccSubjEmpGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目职工明细账 普通打印
	public List<Map<String, Object>>  collectGroupAccSubjEmpDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目职工明细账 模板打印
	public Map<String, Object> collectGroupAccSubjEmpDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//职工余额表  普通打印
	public List<Map<String, Object>> collectGroupAccEmpEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//职工余额表  普通打印
	public Map<String, Object> collectGroupAccEmpEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
