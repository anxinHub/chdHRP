package com.chd.hrp.acc.service.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface GroupAccHosAuxiliaryAccountService {
	public String collectGroupAccHosSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位科目总账  表格打印
	public List<Map<String, Object>> collectGroupAccHosSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位科目总账  模板打印
	public  Map<String, Object>  collectGroupAccHosSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccHosSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位科目明细账  表格打印
	public List<Map<String, Object>> collectGroupAccHosSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位科目明细账  模板打印
	public Map<String, Object> collectGroupAccHosSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccSubjHosGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目单位总账  表格打印
	public List<Map<String, Object>> collectGroupAccSubjHosGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//科目单位总账  模板打印
	public  Map<String, Object>  collectGroupAccSubjHosGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;
	
	public String collectGroupAccSubjHosDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目单位明细账  表格打印
	public List<Map<String, Object>> collectGroupAccSubjHosDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//科目单位明细账  模板打印
	public  Map<String, Object>  collectGroupAccSubjHosDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccHosEndOs(Map<String,Object> entityMap) throws DataAccessException;
	
	//单位余额表  表格打印
	public List<Map<String, Object>> collectGroupAccHosEndOsPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位余额表  模板打印
	public  Map<String, Object>  collectGroupAccHosEndOsPrintDate(Map<String, Object> entityMap)throws DataAccessException;
}
