package com.chd.hrp.mat.service.eva;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatEvaSchemeService {

	public Map<String, Object> queryMatEvaScheme(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryMatEvaSchemeDetail(Map<String, Object> mapVo) throws DataAccessException;

	public String saveMatEvaScheme(Map<String, Object> mapVo) throws DataAccessException;

	public String addMatEvaSchemeDetail(Map<String, Object> mapVo) throws DataAccessException;

}
