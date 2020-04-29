package com.chd.hrp.htc.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HtcDeptDictMapper extends SqlMapper{
	
	
	
	public int addHtcDeptAttrDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public int updateHtcDeptAttrDict(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<?> queryHtcDeptDictByTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询DeptDict分页
	 * @param  entityMap RowBounds
	 * @return List<DeptDict>
	 * @throws DataAccessException
	*/
	
	public List<Map<String,Object>> queryHtcDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryHtcDeptDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
    public Map<String,Object> queryHtcDeptAttrByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String,Object> queryHtcDeptDictByCode(Map<String,Object> entityMap) throws DataAccessException;
}
