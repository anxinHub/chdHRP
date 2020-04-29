package com.chd.hrp.acc.service.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccSupAuxiliaryAccountService {
	
	public String collectSupSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccSupSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccSubjSupGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectAccSubjSupDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectSupEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//供应商科目总账  普通打印
	public List<Map<String, Object>> collectSupSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//供应商科目总账  模板打印
	public Map<String, Object> collectSupSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//供应商科目明细账   普通打印
	public List<Map<String, Object>> collectAccSupSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//供应商科目明细账   模板打印
	public Map<String, Object> collectAccSupSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目供应商总账  普通打印
	public List<Map<String, Object>> collectAccSubjSupGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目供应商总账  模板打印
	public Map<String, Object> collectAccSubjSupGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目供应商明细账   普通打印
	public List<Map<String, Object>> collectAccSubjSupDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目供应商明细账   模板打印
	public Map<String, Object> collectAccSubjSupDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//供应商余额表   普通打印
	public List<Map<String, Object>> collectSupEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//供应商余额表   模板打印
	public Map<String, Object> collectSupEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
