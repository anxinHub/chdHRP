package com.chd.hrp.mat.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 
public interface HosSupProductMapper extends SqlMapper{

	public List<Map<String,Object>> queryHosSupProduct(Map<String, Object> entityMap) throws DataAccessException;

	public void addHosSupProduct(Map<String, Object> entityMap) throws DataAccessException;

	public void deleteHosSupProductBatch(List<Map<String, Object>> entityList) throws DataAccessException;
 
	public void deleteHosSupProduct(Map<String, Object> entityMap) throws DataAccessException;

	public Long queryHosSupProductSeq();

	public void addHosSupProductBatch(List<Map<String, Object>> entityList) throws DataAccessException;
			
}
