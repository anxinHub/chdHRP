package com.chd.hrp.ass.dao.tongJiReports;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AssRepairCollectMapper  extends SqlMapper {

	List<Map<String, Object>> queryAssRepairCollect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAssRepairCollectPrint(
			Map<String, Object> entityMap);


}
