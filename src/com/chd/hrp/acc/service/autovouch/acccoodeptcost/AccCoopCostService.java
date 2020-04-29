package com.chd.hrp.acc.service.autovouch.acccoodeptcost;

import java.util.List;
import java.util.Map;

public interface AccCoopCostService {

	String queryAccCoopCost(Map<String, Object> page);

	Map<String, Object> queryAccCoopCostByCode(Map<String, Object> mapVo);

	String queryCoopCostMaker(Map<String, Object> mapVo);

	String queryAccCoopProjDict(Map<String, Object> mapVo);

	String queryAccCoopCostDetail(Map<String, Object> mapVo);

	String queryAccCoopObj(Map<String, Object> mapVo);

	String saveAccCoopCost(Map<String, Object> mapVo);

	String auditAccCoopCost(Map<String, Object> mapVo);

	String deleteAccCoopCost(Map<String, Object> mapVo);

	String collectAccCoopCost(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccCoopCostPrint(Map<String, Object> mapVo);

	Map<String, Object> queryAccCoopCostDetailPrint(Map<String, Object> mapVo);

	String queryAccCoopCostDetailCount(Map<String, Object> mapVo);

}
