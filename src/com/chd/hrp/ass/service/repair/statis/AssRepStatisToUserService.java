package com.chd.hrp.ass.service.repair.statis;

import java.util.Map;

import com.chd.base.SqlService;

public interface AssRepStatisToUserService  extends SqlService{

	public String queryRepTeamTree(Map<String, Object> mapVo);

	public String queryRepCountByRepUserId(Map<String, Object> mapVo);

	public String queryAssRepReportCentreCenter(Map<String, Object> mapVo);
	public String queryAssRepReportCenter(Map<String, Object> mapVo); 
	public String queryAssRepUnfinishedCenter(Map<String, Object> mapVo);
	public Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo);

	public String queryTimeLineRender(Map<String, Object> mapVo);

	public Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo);

	public String queryImgUrlByRepCode(Map<String, Object> mapVo);

	public String queryRepCountByRepDeptId(Map<String, Object> mapVo);

	public String queryRepCountByRepCardNo(Map<String, Object> mapVo);

}
