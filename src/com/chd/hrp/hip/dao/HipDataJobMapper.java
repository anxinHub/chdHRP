package com.chd.hrp.hip.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hip.entity.HipDataJob;

public interface HipDataJobMapper extends SqlMapper {

	public List<HipDataJob> queryDataJobByPage(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int addDataJob(Map<String,Object> entityMap) throws DataAccessException;
	
	public int updateJob(Map<String,Object> entityMap) throws DataAccessException;
	
	public int deleteDataJob(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryDataType(Map<String,Object> entityMap) throws DataAccessException;
	
	public int queryMaxId(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HipDataJob> queryDataJob(Map<String,Object> entityMap) throws DataAccessException;

}
