package com.chd.hrp.acc.service.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccZcheckAuxiliaryAccountService {
	
	/**
	 * 查询核算项科目总账
	 * */
	public String collectAccZcheckGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询核算项科目明细账
	 * */
	public String collectAccZcheckDetailLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项总账
	 * */
	public String collectAccSubjZcheckGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项明细账
	 * */
	public String collectAccSubjZcheckDetailLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询核算项余额表
	 * */
	public String collectAccZcheckEndOs(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 打印核算项科目总账
	 * */
	public List<Map<String, Object>> collectAccZcheckGeneralLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 打印核算项科目明细账
	 * */
	public List<Map<String, Object>> collectAccZcheckDetailLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 打印科目核算项总账
	 * */
	public List<Map<String, Object>> collectAccSubjZcheckGeneralLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 打印科目核算项明细账
	 * */
	public List<Map<String, Object>> collectAccSubjZcheckDetailLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> collectAccSubjZcheckEndOsPrint(Map<String,Object> entityMap)throws DataAccessException;
	
}
