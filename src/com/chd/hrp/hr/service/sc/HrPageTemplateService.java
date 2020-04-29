package com.chd.hrp.hr.service.sc;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrPageTemplateService {
	
	public String queryHrPageTemplate(Map<String, Object> entityMap) throws DataAccessException;
}
