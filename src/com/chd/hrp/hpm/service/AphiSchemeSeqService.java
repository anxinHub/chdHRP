package com.chd.hrp.hpm.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiSchemeSeqService {
	
	/**
	 * @Description 查询表头<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryHpmSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 审核<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String auditHpmSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询表头<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String querySchemeSeqGrid(Map<String, Object> entityMap) throws DataAccessException;

}
