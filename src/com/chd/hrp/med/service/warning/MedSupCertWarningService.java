package com.chd.hrp.med.service.warning;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MedSupCertWarningService {
	/**
	 * 查询材料效期预警
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedSupCertWarning(Map<String,Object> entityMap)throws DataAccessException;
}
