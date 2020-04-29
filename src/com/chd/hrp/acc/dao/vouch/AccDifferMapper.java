package com.chd.hrp.acc.dao.vouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AccDifferMapper extends SqlMapper{

	public List<Map<String, Object>> queryAccDifferItem(Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryAccDifferItem(Map<String, Object> entityMap, RowBounds rowBounds);

	public Map<String, Object> queryAccDifferItemByCode(Map<String, Object> entityMap);

	public void addAccDifferItem(Map<String, Object> entityMap);

	public void updateAccDifferItem(Map<String, Object> entityMap);

	public void deleteAccDifferItem(List<Map<String, Object>> list);

	public List<Map<String, Object>> queryAccDifferType(Map<String, Object> entityMap);

	public void addAccDifferType(Map<String, Object> entityMap);

	public void deleteAccDifferType(Map<String, Object> entityMap);

	public Map<String, Object> queryAccDifferTypeByCode(Map<String, Object> entityMap);

	public void updateAccDifferType(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDifferItemTree(Map<String, Object> mapVo);


}
