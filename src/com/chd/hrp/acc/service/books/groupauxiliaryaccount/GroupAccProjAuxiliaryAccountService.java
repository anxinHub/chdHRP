package com.chd.hrp.acc.service.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface GroupAccProjAuxiliaryAccountService {
	public String collectGroupAccProjSubjGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;

	public String collectGroupAccProjSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccSubjProjGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectGroupAccSubjProjDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectGroupAccProjEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//项目科目总账 普通打印
	public  List<Map<String, Object>> collectGroupAccProjSubjGeneralLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	//项目科目总账 模板打印
	public Map<String, Object> collectGroupAccProjSubjGeneralLedgerPrintDate(Map<String,Object> entityMap)throws DataAccessException;

	//项目科目明细账 普通打印
	public List<Map<String, Object>> collectGroupAccProjSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//项目科目明细账 模板打印
	public Map<String, Object> collectGroupAccProjSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目项目总账 普通打印
	public  List<Map<String, Object>> collectGroupAccSubjProjGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目项目总账 模板打印
	public Map<String, Object> collectGroupAccSubjProjGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目项目明细账 普通打印
	public List<Map<String, Object>> collectGroupAccSubjProjDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目项目明细账 模板打印
	public Map<String, Object> collectGroupAccSubjProjDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//项目余额表 普通打印
	public List<Map<String, Object>> collectGroupAccProjEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//项目余额表 模板打印
	public Map<String, Object> collectGroupAccProjEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
