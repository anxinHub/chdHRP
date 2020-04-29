package com.chd.hrp.acc.service.tell;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccUnitBankTell;

public interface AccUnitBankTellService {
	
	/**
	 * @Description 
	 * 单位银行账<BR> 查询AccUnitBankTell
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	
	public String queryAccUnitBankTell(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 单位银行账<BR> 打印queryAccUnitBankTellPrint
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	
	public List<Map<String, Object>> queryAccUnitBankTellPrint(Map<String,Object> entityMap)throws DataAccessException;
}
