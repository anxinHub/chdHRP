package com.chd.hrp.acc.dao.tell;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccUnitBankTell;

public interface AccUnitBankTellMapper extends SqlMapper { 
	/**
	 * @Description  
	 * 单位银行账<BR> 查询AccUnitBankTell
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/	public List<AccUnitBankTell> queryAccUnitBankTell(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 单位银行账<BR> 查询AccUnitBankTell
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/	public List<AccUnitBankTell> queryAccUnitBankTell(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccUnitBankTellPrint(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 单位银行张 出纳账查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccUnitBankTell> queryAccUnitBankTellByTell(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	public List<AccUnitBankTell> queryAccUnitBankTellByTell(Map<String,Object> entityMap)throws DataAccessException;
	
	}
