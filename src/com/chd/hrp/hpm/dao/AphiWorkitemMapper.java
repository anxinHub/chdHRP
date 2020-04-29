package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiWorkitem;

/**
 * alfred
 */

public interface AphiWorkitemMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addWorkitem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchWorkitem(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiWorkitem> queryWorkitem(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiWorkitem> queryWorkitem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiWorkitem queryWorkitemByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteWorkitem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateWorkitem(Map<String, Object> entityMap) throws DataAccessException;
}
