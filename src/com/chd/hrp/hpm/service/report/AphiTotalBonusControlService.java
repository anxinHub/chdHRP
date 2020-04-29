package com.chd.hrp.hpm.service.report;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiTotalBonusControlService {
	public String queryTotalBonusControlMainPage(Map<String, Object> entityMap) throws DataAccessException;
}
