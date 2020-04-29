package com.chd.hrp.ass.dao.deptreport;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssDeptAccountReportMapper extends SqlMapper{  
	List<Map<String,Object>> query(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String,Object>> query(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	List<Map<String,Object>> queryPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String,Object>> queryDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String,Object>> queryDetail(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
}
  