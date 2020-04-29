package com.chd.hrp.ass.dao.card;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssCardBaseMapper extends SqlMapper{
	public Map<String, Object> queryAssCardInitByCardNo(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<Map<String, Object>> queryAssCardView(Map<String,Object> entityMap) throws DataAccessException;
}
