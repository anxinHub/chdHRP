/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.autovouch.his;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HisAccPreMapper extends SqlMapper {

	/**
	 * 住院
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccPreI(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 住院
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccPreI(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 住院
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccPreDetailI(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 住院
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccPreDetailI(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 门诊
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccPreO(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 门诊
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccPreO(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 门诊
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccPreDetailO(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 门诊
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccPreDetailO(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryHisAccPreIByType(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryHisAccPreOByType(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryHisAccPreIByType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryHisAccPreOByType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int queryHisAccType(Map<String, Object> entityMap) throws DataAccessException;
	

}
