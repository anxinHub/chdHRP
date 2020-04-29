package com.chd.hrp.ass.service.repair.repairdistr;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chd.base.SqlService;
public interface AssRepairDistrService extends SqlService{


	String queryAssRepairByState(Map<String, Object> mapVo);
	
	String backAssRepair(Map<String, Object> mapVo);

	Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo);

	String queryImgUrlByRepCode(Map<String, Object> mapVo);

	String updateRepairDistrState(Map<String, Object> mapVo);


}
