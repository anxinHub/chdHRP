package com.chd.hrp.acc.dao.autovouch.acccoodeptcost;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AccCoopCostDetailMapper extends SqlMapper {

	List<Map<String, Object>> queryAccCoopCostDetail(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccCoopObj(Map<String, Object> mapVo);

	void addAccCoopCostDetail(List<Map<String, Object>> detailList);

	void deleteAccCoopCostDetail(List<Map<String, Object>> delList);

	void deleteAccCoopCostDetailBatch(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccCoopCostDetailPrint(Map<String, Object> mapVo);

}
