package com.chd.hrp.ass.dao.tongJiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssAgeAnalyzeMapper  extends SqlMapper {

	List<Map<String, Object>> queryAssCardUseYearOrLifeYear(Map<String, Object> mapVo);
	
	
	List<Map<String, Object>> queryAssCardUseYearOrLifeYear(Map<String, Object> mapVo,RowBounds rowBounds );


	List<Map<String, Object>> queryAssAgeAnalyePrint(
			Map<String, Object> entityMap);

}
