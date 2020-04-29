package com.chd.hrp.hip.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hip.entity.HipDataJob;

public interface HipDataJobService {

	String queryDataJob(Map<String, Object> map) throws DataAccessException;
	
	String addDataJob(Map<String, Object> map) throws DataAccessException;
	
	String updateDataJob(Map<String, Object> map) throws DataAccessException;
	
	String deleteDataJob(List<Map<String, Object>> listVo) throws DataAccessException;
	
	String startDataJob(List<String> listVo,String uri)throws DataAccessException;
	
	String stopDataJob(List<String> listVo)throws DataAccessException;
	
	String queryDataType(Map<String, Object> map) throws DataAccessException;
	
	void synDataJob(String uri)throws DataAccessException;
	
	HipDataJob queryDataJobById(Map<String, Object> map)throws DataAccessException;
}
