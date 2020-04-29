package com.chd.hrp.hpm.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiSchemeConf;

public interface AphiSchemeConfService {
	/**
	 * 
	 */
	public String queryHpmSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addHpmSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createHpmSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateHpmSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteHpmSchemeConf(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSchemeConf queryHpmSchemeConfByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryHpmSchemeConfForDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryHpmSchemeConfGridForDept(Map<String, Object> entityMap) throws DataAccessException;

}
