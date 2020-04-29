package com.chd.hrp.acc.dao.vouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AccDifferSubjMapper extends SqlMapper {

	public Integer queryExistInAccDiffSubjSet(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAccDifferSubjForAdd(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAccDifferSubjForAdd(Map<String, Object> entityMap, RowBounds rowBounds);

}
