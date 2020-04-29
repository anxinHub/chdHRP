package com.chd.hrp.ass.dao.tongJiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssInAndOutSummaryMapper extends SqlMapper {  
	
	public List<Map<String, Object>> queryAssInAndOutSummary(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAssInAndOutSummary(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;
	
	public List<Map<String, Object>> queryAssInAndOutSummaryPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAssInDetail(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> queryAssOutDetail(Map<String, Object> mapVo) throws DataAccessException;
}
