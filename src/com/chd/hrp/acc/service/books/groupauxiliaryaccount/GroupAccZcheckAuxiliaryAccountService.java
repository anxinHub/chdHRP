package com.chd.hrp.acc.service.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface GroupAccZcheckAuxiliaryAccountService {
	
	/**
	 * 查询核算项科目总账
	 * */
	public String collectGroupAccZcheckGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询核算项科目明细账
	 * */
	public String collectGroupAccZcheckDetailLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项总账
	 * */
	public String collectGroupAccSubjZcheckGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项明细账
	 * */
	public String collectGroupAccSubjZcheckDetailLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询核算项余额表
	 * */
	public String collectGroupAccZcheckEndOs(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 打印核算项科目总账
	 * */
	public List<Map<String, Object>> collectGroupAccZcheckGeneralLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 打印核算项科目明细账
	 * */
	public List<Map<String, Object>> collectGroupAccZcheckDetailLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 打印科目核算项总账
	 * */
	public List<Map<String, Object>> collectGroupAccSubjZcheckGeneralLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 打印科目核算项明细账
	 * */
	public List<Map<String, Object>> collectGroupAccSubjZcheckDetailLedgerPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> collectGroupAccSubjZcheckEndOsPrint(Map<String,Object> entityMap)throws DataAccessException;
	
}
