package com.chd.hrp.pac.dao.cmitype;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface COSTBUSISOURECDICTMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryCOSTBUSISOURECDICT(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
