package com.chd.hrp.hpm.service.report;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiDistributionBonusComparativeService {
	
	public String queryDistributionBonusComparative(Map<String, Object> entityMap) throws DataAccessException;
	
}
