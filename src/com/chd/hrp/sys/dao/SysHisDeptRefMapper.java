package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface SysHisDeptRefMapper extends SqlMapper{
	
	public int addSysHisDeptRef(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> querySysHisDeptRef(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> querySysHisDeptRef(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public Map<String, Object> querySysHisDeptRefByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchSysHisDeptRef(List<Map<String,Object>> list)throws DataAccessException;
}
