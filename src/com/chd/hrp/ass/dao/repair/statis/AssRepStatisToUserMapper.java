package com.chd.hrp.ass.dao.repair.statis;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AssRepStatisToUserMapper extends SqlMapper{

	List<Map<String, Object>> queryRepTeamTree(Map<String, Object> mapVo);

	List<Map<String, Object>> queryRepCountByRepUserId(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAssRepReportCentreCenter(Map<String, Object> mapVo);
	
	List<Map<String, Object>> queryAssRepReportCenter(Map<String, Object> mapVo); 

	List<Map<String, Object>> queryAssRepUnfinishedCenter(Map<String, Object> mapVo);
	
	Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTimeLineRender(Map<String, Object> mapVo);

	Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryImgUrlByRepCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryRepCountByRepDeptId(Map<String, Object> mapVo);

	List<Map<String, Object>> queryRepCountByRepCardNo(Map<String, Object> mapVo);

}
