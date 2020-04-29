package com.chd.hrp.acc.service.accAly;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface AccAlyFacService  extends SqlService {
	
	public List<Map<String, Object>> queryFacPrintDate(Map<String, Object> entityMap) throws DataAccessException;

}
