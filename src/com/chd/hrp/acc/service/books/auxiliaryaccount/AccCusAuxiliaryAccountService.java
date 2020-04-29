package com.chd.hrp.acc.service.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccCusAuxiliaryAccountService {
	public String collectCusSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;
	
	public String collectAccCusSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccSubjCusGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectAccSubjCusDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectCusEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//客户科目总账     普通打印
	public List<Map<String, Object>> collectCusSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//客户科目总账     模板打印
	public Map<String, Object> collectCusSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;
	
	//客户科目明细账     普通打印
	public List<Map<String, Object>> collectAccCusSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//客户科目明细账       模板打印
	public Map<String, Object> collectAccCusSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目客户总账     普通打印
	public List<Map<String, Object>> collectAccSubjCusGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目客户总账       模板打印
	public Map<String, Object> collectAccSubjCusGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目客户明细账     普通打印
	public List<Map<String, Object>> collectAccSubjCusDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目客户明细账     模板打印
	public Map<String, Object> collectAccSubjCusDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//客户余额表     普通打印
	public List<Map<String, Object>> collectCusEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//客户余额表      模板打印
	public Map<String, Object> collectCusEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
