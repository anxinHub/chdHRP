package com.chd.hrp.acc.dao.books.groupauxiliaryaccount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface GroupAccZcheckAuxiliaryAccountMapper extends SqlMapper {
	
	/**
	 * 查询核算项科目总账
	 * */
	public  List<Map<String, Object>> collectGroupAccZcheckGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询核算项科目明细账
	 * */
	public List<Map<String, Object>> collectGroupAccZcheckDetailLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项总账
	 * */
	public List<Map<String, Object>> collectGroupAccSubjZcheckGeneralLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项明细账
	 * */
	public  List<Map<String, Object>>  collectGroupAccSubjZcheckDetailLedger(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询科目方向,下面计算需要用到，其实也可以写sql块一起执行
	public int queryGroupAccSubjDire(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询科目核算项余额表
	 * */
	public List<Map<String,Object>> collectGroupAccZcheckEndOs(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
