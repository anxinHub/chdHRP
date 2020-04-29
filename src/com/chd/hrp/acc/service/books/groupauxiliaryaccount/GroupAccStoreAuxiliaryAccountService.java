package com.chd.hrp.acc.service.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface GroupAccStoreAuxiliaryAccountService {
	
	public String collectGroupAccStoreSubjGeneralLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccStoreSubjDetailLedger(Map<String, Object> entityMap)throws DataAccessException;

	public String collectGroupAccSubjStoreGeneralLedger(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectGroupAccSubjStoreDetailLedger(Map<String,Object> entityMap) throws DataAccessException;

	public String collectGroupAccStoreEndOs(Map<String,Object> entityMap) throws DataAccessException;

	//库房科目总账  普通打印
	public List<Map<String, Object>> collectGroupAccStoreSubjGeneralLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//库房科目总账  模板打印
	public Map<String, Object> collectGroupAccStoreSubjGeneralLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//库房科目明细账  普通打印
	public List<Map<String, Object>> collectGroupAccStoreSubjDetailLedgerPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	//库房科目明细账  模板打印
	public Map<String, Object> collectGroupAccStoreSubjDetailLedgerPrintDate(Map<String, Object> entityMap)throws DataAccessException;

	//科目库房总账  普通打印
	public List<Map<String, Object>> collectGroupAccSubjStoreGeneralLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目库房总账   模板打印
	public Map<String, Object> collectGroupAccSubjStoreGeneralLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目库房明细账  普通打印
	public List<Map<String, Object>> collectGroupAccSubjStoreDetailLedgerPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目库房明细账  模板打印
	public Map<String, Object> collectGroupAccSubjStoreDetailLedgerPrintDate(Map<String,Object> entityMap) throws DataAccessException;

	//库房余额表  普通打印
	public List<Map<String, Object>> collectGroupAccStoreEndOsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	//库房余额表 模板打印
	public Map<String, Object> collectGroupStoreEndOsPrintDate(Map<String,Object> entityMap) throws DataAccessException;

}
