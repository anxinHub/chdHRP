package com.chd.hrp.acc.dao.tell;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccBankCheckTell;

public interface AccBankCheckTellMapper extends SqlMapper {
	
	/**
	 * @Description 
	 *银行对账单<BR> 查询AccBankCheckTell
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/	
	public List<AccBankCheckTell> queryAccBankCheckTell(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	public List<AccBankCheckTell> queryAccBankCheckTellOld(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 银行对账单<BR> 查询AccBankCheckTell
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/	
	public List<AccBankCheckTell> queryAccBankCheckTell(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AccBankCheckTell> queryAccBankCheckTellPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AccBankCheckTell> printAccBankCheckTell(Map<String, Object> entityMap)throws DataAccessException;
	
}
