package com.chd.hrp.acc.service.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccEmpAuxiliaryAccountService {
	
	public String collectEmpSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccEmpSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccSubjEmpGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectAccSubjEmpDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectEmpEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//职工科目总账 普通打印
	public List<Map<String, Object>> collectEmpSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//职工科目总账 模板打印
	public Map<String, Object> collectEmpSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//职工科目明细账 普通打印
	public List<Map<String, Object>> collectAccEmpSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//职工科目明细账 模板打印
	public Map<String, Object> collectAccEmpSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目职工总账 普通打印
	public List<Map<String, Object>>  collectAccSubjEmpGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目职工总账  模板打印
	public Map<String, Object> collectAccSubjEmpGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目职工明细账 普通打印
	public List<Map<String, Object>>  collectAccSubjEmpDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目职工明细账 模板打印
	public Map<String, Object> collectAccSubjEmpDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//职工余额表  普通打印
	public List<Map<String, Object>> collectEmpEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//职工余额表  普通打印
	public Map<String, Object> collectEmpEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
