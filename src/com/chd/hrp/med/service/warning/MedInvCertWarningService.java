package com.chd.hrp.med.service.warning;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MedInvCertWarningService {
	/**
	 * 查询材料效期预警
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInvCertWarning(Map<String,Object> entityMap)throws DataAccessException;
}
