package com.chd.hrp.mat.service.warning;

import java.text.ParseException;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatVendorWarningService {
	/**
	 * 查询材料效期预警
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatVendorWarning(Map<String,Object> entityMap)throws DataAccessException;
	
}
