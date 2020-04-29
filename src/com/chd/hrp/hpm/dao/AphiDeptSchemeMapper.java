package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptScheme;

/**
 * alfred
 */

public interface AphiDeptSchemeMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchDeptScheme(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptScheme> queryDeptScheme(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 审核使用查询
	 */
	public List<Map<String, Object>> queryDeptSchemeBySchemeSeq(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 审核使用查询
	 */
	public List<Map<String, Object>> queryDeptSchemeBySchemeSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptScheme> queryDeptScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptScheme> queryDeptSchemeFast(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptScheme queryDeptSchemeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateDeptScheme(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public int deleteBatchDeptScheme(List<Map<String, Object>> entityMap) throws DataAccessException;

}
