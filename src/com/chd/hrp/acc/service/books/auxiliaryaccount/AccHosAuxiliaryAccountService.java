package com.chd.hrp.acc.service.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccHosAuxiliaryAccountService {
	public String collectHosSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位科目总账  表格打印
	public List<Map<String, Object>> collectHosSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位科目总账  模板打印
	public  Map<String, Object>  collectHosSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccHosSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位科目明细账  表格打印
	public List<Map<String, Object>> collectAccHosSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位科目明细账  模板打印
	public Map<String, Object> collectAccHosSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccSubjHosGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目单位总账  表格打印
	public List<Map<String, Object>> collectAccSubjHosGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//科目单位总账  模板打印
	public  Map<String, Object>  collectAccSubjHosGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;
	
	public String collectAccSubjHosDetailLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目单位明细账  表格打印
	public List<Map<String, Object>> collectAccSubjHosDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//科目单位明细账  模板打印
	public  Map<String, Object>  collectAccSubjHosDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	public String collectHosEndOs(Map<String,Object> entityMap) throws DataAccessException;
	
	//单位余额表  表格打印
	public List<Map<String, Object>> collectHosEndOsPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//单位余额表  模板打印
	public  Map<String, Object>  collectHosEndOsPrintDate(Map<String, Object> entityMap)throws DataAccessException;
}
