package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiItemIncreasePercConf;

/**
 * alfred
 */

public interface AphiItemIncreasePercConfMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchItemIncreasePercConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItemIncreasePercConf> queryItemIncreasePercConf(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItemIncreasePercConf> queryItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiItemIncreasePercConf queryItemIncreasePercConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteBatchItemIncreasePercConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;
}
