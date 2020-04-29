package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptEmpMap;

public interface AphiDeptEmpMapService {

	String queryDeptEmpMap(Map<String, Object> entityMap) throws DataAccessException;

	String addDeptEmpMap(Map<String, Object> entityMap) throws DataAccessException;

	AphiDeptEmpMap queryDeptEmpMapByCode(Map<String, Object> entityMap) throws DataAccessException;

	String updateDeptEmpMap(Map<String, Object> entityMap) throws DataAccessException;

	String deleteDeptEmpMap(Map<String, Object> entityMap, String checkIds) throws DataAccessException;

	String queryHpmItem(Map<String, Object> entityMap) throws DataAccessException;

	String queryHpmEmpItem(Map<String, Object> entityMap) throws DataAccessException;

	String saveDeptEmpMap(Map<String, Object> entityMap) throws DataAccessException;
	
	

}
