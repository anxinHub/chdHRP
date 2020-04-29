package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccVouch;

public interface AccAutoVouchMaintainMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryAccAutoVouch(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccAutoVouch(Map<String, Object> entityMap) throws DataAccessException;
	
	public String deleteAccAutoVouch(Map<String,Object> map)throws DataAccessException;
}
