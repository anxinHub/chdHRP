package com.chd.hrp.ass.service.endaccount;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface InassClosingAccountService {
	public String queryInassYearMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String, Object> queryInassCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	
	public String collectInassDepreALL(Map<String,Object> map)throws DataAccessException;
	
	public String collectInassDelDepreALL(Map<String,Object> map)throws DataAccessException;

	public Map<String, Object> queryInassSysYearMonth(Map<String, Object> mapVo)throws DataAccessException;
	
}
