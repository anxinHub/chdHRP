package com.chd.hrp.ass.dao.repair.asslocation;

import java.util.List;
import java.util.Map;


import com.chd.base.SqlMapper;

public interface AssLocationMapper extends SqlMapper{

	Map<String, Object> queryAssLocationByCode(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssLocationTree(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAssLocation(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAssLocationSuperAll(Map<String, Object> entityMap);
	
	public int addLocation(Map<String, Object> entityMap);

	void updateLocNameAll(Map<String, Object> entityMap);

	void deleteAssLocationBatch(List<Map> listVo);

	Integer queryAssExists(Map map);

	Map<String, Object> querySuperLocationByLocCode(Map<String, Object> entityMap);

	int deleteAssLocationDeptBatch(List<Map> listVo);


}
