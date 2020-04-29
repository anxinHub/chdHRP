package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AccMedRateMapper extends SqlMapper{

	List<Map<String, Object>> queryMedRate(Map<String, Object> entityMap);

	List<Map<String, Object>> queryMedRate(Map<String, Object> entityMap, RowBounds rowBounds);

}
