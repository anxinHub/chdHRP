package com.chd.hrp.ass.dao.repair.assfaulttype;

import java.util.List;
import java.util.Map;
import com.chd.base.SqlMapper;

public interface AssFaultTypeMapper extends SqlMapper{

	List<Map<String, Object>> queryAssFaultTypeTree(Map<String, Object> mapVo);

	Map<String, Object> queryAssFaultTypeByCode(Map<String, Object> entityMap);

	void addFaultType(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssFaultType(Map<String, Object> mapVo);

	void deleteAssFaultTypeBatch(List<Map> listVo);

	Map<String, Object> queryAssFaultTypeSuperIsExists(Map<String, Object> entityMap);

	int extendsBySuperCode(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssRepUserFaultBySuperCod(Map<String, Object> entityMap);

	List<String> queryAssRepairByFauCode(Map<String, Object> mapVo);

	int deleteAssRepUserFaultTypeBatch(List<Map> listVo);

}
