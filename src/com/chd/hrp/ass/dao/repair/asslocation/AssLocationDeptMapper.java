package com.chd.hrp.ass.dao.repair.asslocation;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AssLocationDeptMapper extends SqlMapper{

	int addLocationDpt(List<Map<String, Object>> listVo);

	int deleteAssLocationDeptBatch(List<Map<String, Object>> listVo);

	Map<String, Object> queryAssLocationDeptByCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAssLocationDept(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHosDeptTree(Map<String, Object> mapVo);


	List<String> queryDeptIdBySuperId(Map<String, Object> entityMap);

	void updateassLocationDept(Map<String, Object> entityMap);

}
