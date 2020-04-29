package com.chd.hrp.acc.dao.autovouch.his;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccBalDetailI;

public interface AccBalDetailIMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryAccBalDetailI(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccBalDetailI(Map<String, Object> entityMap) throws DataAccessException;
}	
