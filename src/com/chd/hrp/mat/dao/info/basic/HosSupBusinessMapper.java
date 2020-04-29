package com.chd.hrp.mat.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 
public interface HosSupBusinessMapper extends SqlMapper{
	
	public List<Map<String,Object>> queryHosSupBusiness(Map<String,Object> entityMap) throws DataAccessException;
	
	public int addHosSupBusiness(Map<String,Object> entityMap) throws DataAccessException;

	public int deleteHosSupBusinessBatch(List<Map<String, Object>> entityList) throws DataAccessException;
 
	public int deleteHosSupBusiness(Map<String, Object> entityMap) throws DataAccessException;

	public Long queryHosSupBusinessSeq();

	public int addHosSupBusinessBatch(List<Map<String, Object>> bankList);
	
}
