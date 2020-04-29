package com.chd.hrp.hr.service.sc;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sc.HrPageDesign;


public interface HrPageDesignService {
	
	public String queryHrPageDesignTree(Map<String, Object> entityMap) throws DataAccessException;
	
	public HrPageDesign queryHrPageDesignByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHrPageDesignExport(Map<String, Object> entityMap) throws DataAccessException;
	
	public String addHrPageDesign(Map<String, Object> entityMap) throws DataAccessException;
	
	public String updateHrPageDesign(Map<String, Object> entityMap) throws DataAccessException;
	
	public String deleteHrPageDesign(Map<String, Object> entityMap) throws DataAccessException;
	
	public String saveHrPageDesign(Map<String, Object> entityMap) throws DataAccessException;
}
