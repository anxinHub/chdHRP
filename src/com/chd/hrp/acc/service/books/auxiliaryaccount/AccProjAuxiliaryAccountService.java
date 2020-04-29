package com.chd.hrp.acc.service.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccProjAuxiliaryAccountService {
	public String collectProjSubjGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;

	public String collectAccProjSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccSubjProjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectAccSubjProjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectProjEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//项目科目总账 普通打印
	public  List<Map<String, Object>> collectProjSubjGeneralLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	//项目科目总账 模板打印
	public Map<String, Object> collectProjSubjGeneralLedgerPrintDate(Map<String,Object> entityMap)throws DataAccessException;

	//项目科目明细账 普通打印
	public List<Map<String, Object>> collectAccProjSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//项目科目明细账 模板打印
	public Map<String, Object> collectAccProjSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目项目总账 普通打印
	public  List<Map<String, Object>> collectAccSubjProjGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目项目总账 模板打印
	public Map<String, Object> collectAccSubjProjGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目项目明细账 普通打印
	public List<Map<String, Object>> collectAccSubjProjDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目项目明细账 模板打印
	public Map<String, Object> collectAccSubjProjDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//项目余额表 普通打印
	public List<Map<String, Object>> collectProjEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//项目余额表 模板打印
	public Map<String, Object> collectProjEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
