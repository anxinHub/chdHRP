package com.chd.hrp.htc.service.info.basic;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.info.basic.HtcProDeptDict;

public interface HtcProDeptDictService {

	public String addHtcProDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public String synchroHtcProDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcProDept(Map<String,Object> entityMap) throws DataAccessException; 
	
	public HtcProDeptDict queryHtcProDeptByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcProDept(List<Map<String,Object>> list) throws DataAccessException;
	
	public String updateHtcProDept(Map<String,Object> entityMap) throws DataAccessException;
}
