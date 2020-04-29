package com.chd.hrp.acc.dao.autovouch.acccoodeptcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AccCoopCostMapper extends SqlMapper {

	List<Map<String, Object>> queryCoopCostMaker(Map<String, Object> mapVo, RowBounds rowBounds);

	List<Map<String, Object>> queryAccCoopProjDict(Map<String, Object> mapVo, RowBounds rowBounds);

	String queryMaxNo(Map<String, Object> mapVo);

	void addAccCoopCost(Map<String, Object> mapVo);

	void updateAccCoopCost(Map<String, Object> mapVo);

	Integer isCreateVouch(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccCoopCostPrint(Map<String, Object> mapVo);

	void deleteAccCoopCost(List<Map<String, Object>> delList);

	void deleteAccCoopCostBatch(List<Map<String, Object>> delList);

	// 暂时使用，以后替换 成徐鑫的代码，
	List<Map<String, Object>> queryAccCccpProjDetail(Map<String, Object> mapVo);

	Map<String, Object> queryByCodePrint(Map<String, Object> entityMap);
}
