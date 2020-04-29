package com.chd.hrp.acc.service.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccStoreAuxiliaryAccountService {
	
	public String collectStoreSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccStoreSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectAccSubjStoreGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectAccSubjStoreDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectStoreEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//库房科目总账  普通打印
	public List<Map<String, Object>> collectStoreSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//库房科目总账  模板打印
	public Map<String, Object> collectStoreSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//库房科目明细账  普通打印
	public List<Map<String, Object>> collectAccStoreSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//库房科目明细账  模板打印
	public Map<String, Object> collectAccStoreSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目库房总账  普通打印
	public List<Map<String, Object>> collectAccSubjStoreGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目库房总账   模板打印
	public Map<String, Object> collectAccSubjStoreGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目库房明细账  普通打印
	public List<Map<String, Object>> collectAccSubjStoreDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目库房明细账  模板打印
	public Map<String, Object> collectAccSubjStoreDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//库房余额表  普通打印
	public List<Map<String, Object>> collectStoreEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//库房余额表 模板打印
	public Map<String, Object> collectStoreEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
