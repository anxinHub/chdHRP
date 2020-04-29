package com.chd.hrp.ass.dao.summary;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssSummaryMapper extends SqlMapper{

	List<Map<String, Object>> queryAssSummary(Map<String, Object> entityMap,
			RowBounds rowBounds);

	List<Map<String, Object>> queryAssSummary(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssSummaryMainPrint(
			Map<String, Object> entityMap);

}
