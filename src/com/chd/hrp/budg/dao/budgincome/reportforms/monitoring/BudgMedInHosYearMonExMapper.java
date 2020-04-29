package com.chd.hrp.budg.dao.budgincome.reportforms.monitoring;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface BudgMedInHosYearMonExMapper extends SqlMapper{

	int queryExecuteDataExist(Map<String, Object> entityMap);

	List<Map<String, Object>> query(Map<String, Object> entityMap);

	List<Map<String, Object>> query(Map<String, Object> entityMap,
			RowBounds rowBounds);

	int queryBudgDataExist(Map<String, Object> entityMap);

}
