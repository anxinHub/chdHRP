package com.chd.hrp.hr.dao.sc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sc.HrPageDesign;

public interface HrPageDesignMapper extends SqlMapper{

	public int addHrPageDesign(Map<String, Object> entityMap) throws DataAccessException;
	
	public int updateHrPageDesign(Map<String, Object> entityMap) throws DataAccessException;
	
	public int deleteHrPageDesign(Map<String, Object> entityMap) throws DataAccessException;
	
	public int queryHrPageDesignMaxSortNo(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryHrPageDesignTree(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HrPageDesign> queryHrPageDesignExport(Map<String, Object> entityMap) throws DataAccessException;
	
	public HrPageDesign queryHrPageDesignByCode(Map<String, Object> entityMap) throws DataAccessException;
	
}
