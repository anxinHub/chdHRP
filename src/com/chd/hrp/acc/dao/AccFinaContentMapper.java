package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccFinaContent;

public interface AccFinaContentMapper extends SqlMapper {
	
	public List<Map<String, Object>> queryAccFinaContent(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccFinaContent(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public AccFinaContent queryAccFinaContentByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public int addAccFinaContent(Map<String,Object> entityMap) throws DataAccessException;
	
	public int addBatchAccFinaContent(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	public int deleteAccFinaContent(Map<String,Object> entityMap) throws DataAccessException;
	
	public int deleteBatchAccFinaContent(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	public int updateAccFinaContent(Map<String,Object> entityMap) throws DataAccessException;
	
	public int updateBatchAccFinaContent(List<Map<String,Object>> entityMap) throws DataAccessException;
	/**
	 * 查询财政补助内容 是否正在被引用
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsUsed(Map<String, Object> item) throws DataAccessException;
}
