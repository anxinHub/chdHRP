package com.chd.hrp.acc.service.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface GroupAccCusAuxiliaryAccountService {
	public String collectGroupAccCusSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;
	
	public String collectGroupAccCusSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccSubjCusGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectGroupAccSubjCusDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectGroupAccCusEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//客户科目总账     普通打印
	public List<Map<String, Object>> collectGroupAccCusSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//客户科目总账     模板打印
	public Map<String, Object> collectGroupAccCusSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;
	
	//客户科目明细账     普通打印
	public List<Map<String, Object>> collectGroupAccCusSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//客户科目明细账       模板打印
	public Map<String, Object> collectGroupAccCusSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目客户总账     普通打印
	public List<Map<String, Object>> collectGroupAccSubjCusGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目客户总账       模板打印
	public Map<String, Object> collectGroupAccSubjCusGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目客户明细账     普通打印
	public List<Map<String, Object>> collectGroupAccSubjCusDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目客户明细账     模板打印
	public Map<String, Object> collectGroupAccSubjCusDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//客户余额表     普通打印
	public List<Map<String, Object>> collectGroupAccCusEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//客户余额表      模板打印
	public Map<String, Object> collectGroupAccCusEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
