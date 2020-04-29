package com.chd.hrp.acc.dao.books.auxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface AccZcheckAuxiliaryAccountMapper extends SqlMapper {
	
	/**
	 * 查询核算项科目总账
	 * */
	public  List<Map<String, Object>> collectAccZcheckGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询核算项科目明细账
	 * */
	public List<Map<String, Object>> collectAccZcheckDetailLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项总账
	 * */
	public List<Map<String, Object>> collectAccSubjZcheckGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项明细账
	 * */
	public  List<Map<String, Object>>  collectAccSubjZcheckDetailLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询科目方向,下面计算需要用到，其实也可以写sql块一起执行
	public int querySubjDire(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项余额表
	 * */
	public List<Map<String,Object>> collectAccZcheckEndOs(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
