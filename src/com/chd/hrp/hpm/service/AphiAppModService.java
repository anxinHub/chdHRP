package com.chd.hrp.hpm.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiAppMod;

public interface AphiAppModService {
	
	/**
	 * 
	 */
	public AphiAppMod queryAppModByCode(Map<String, Object> mapVo) throws DataAccessException;
}
