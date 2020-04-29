package com.chd.hrp.ass.service.repair.asslocation;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;

public interface AssLocationDeptService extends SqlService{

	Map<String, Object> queryAssLocationDeptByCode(Map<String, Object> mapVo);

	String queryHosDeptTree(Map<String, Object> mapVo);

	String queryAssLocationDept(Map<String, Object> mapVo);

	String deleteAssLocationDeptBatch(List<Map<String, Object>> listVo);

}
