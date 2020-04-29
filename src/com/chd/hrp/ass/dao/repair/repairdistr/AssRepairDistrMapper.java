package com.chd.hrp.ass.dao.repair.repairdistr;

import java.util.List;
import java.util.Map;



import com.chd.base.SqlMapper;

public interface AssRepairDistrMapper extends SqlMapper{

	List<Map<String, Object>> queryAssRepairByState(Map<String, Object> mapVo);
	
	List<String> queryAssRepairExist(Map<String, Object> mapVo);

	Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryImgUrlByRepCode(Map<String, Object> mapVo);

	int addAssRepairTask(Map<String, Object> repDataMap);
	
	int backAssRepair(Map<String, Object> repDataMap);

	void updateUserWorkByUserId(Map<String, Object> repDataMap);

	void updateAssRepairSend(Map<String, Object> repDataMap);

	Map<String, Object> queryUserWorkByUserId(Map<String, Object> repDataMap);

	int addUserWork(Map<String, Object> repDataMap);

	 


}
