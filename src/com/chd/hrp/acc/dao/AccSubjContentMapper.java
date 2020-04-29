package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccSubjContent;

public interface AccSubjContentMapper extends SqlMapper {
	
	public List<Map<String, Object>> queryAccSubjContent(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccSubjContent(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public AccSubjContent queryAccSubjContentByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public int addAccSubjContent(Map<String,Object> entityMap) throws DataAccessException;
	
	public int addBatchAccSubjContent(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	public int deleteAccSubjContent(Map<String,Object> entityMap) throws DataAccessException;
	
	public int deleteBatchAccSubjContent(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	public int updateAccSubjContent(Map<String,Object> entityMap) throws DataAccessException;
	
	public int updateBatchAccSubjContent(List<Map<String,Object>> entityMap) throws DataAccessException;
}
