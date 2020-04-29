package com.chd.hrp.acc.service.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface GroupAccSupAuxiliaryAccountService {
	
	public String collectGroupAccSupSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccSupSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccSubjSupGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectGroupAccSubjSupDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectGroupAccSupEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//供应商科目总账  普通打印
	public List<Map<String, Object>> collectGroupAccSupSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//供应商科目总账  模板打印
	public Map<String, Object> collectGroupAccSupSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//供应商科目明细账   普通打印
	public List<Map<String, Object>> collectGroupAccSupSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//供应商科目明细账   模板打印
	public Map<String, Object> collectGroupAccSupSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目供应商总账  普通打印
	public List<Map<String, Object>> collectGroupAccSubjSupGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目供应商总账  模板打印
	public Map<String, Object> collectGroupAccSubjSupGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目供应商明细账   普通打印
	public List<Map<String, Object>> collectGroupAccSubjSupDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目供应商明细账   模板打印
	public Map<String, Object> collectGroupAccSubjSupDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//供应商余额表   普通打印
	public List<Map<String, Object>> collectGroupAccSupEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//供应商余额表   模板打印
	public Map<String, Object> collectGroupAccSupEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
