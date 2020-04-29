package com.chd.hrp.mat.dao.outsum;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 *@author Fan Yang
 *@date   2020年4月21日
 *@email  yangfan1105@dhcc.com.cn
 */
public interface MatOutSumMapper extends SqlMapper {

	public List<Map<String, Object>> queryOutSumSingleSum(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> queryDeptStoreCross(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> queryALLMatTypes(Map<String, Object> map) throws DataAccessException;
	
}
