package com.chd.hrp.ass.service.repair.asslocation;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.chd.base.SqlService;
public interface AssLocationService extends SqlService{

	String queryAssLocationTree(Map<String, Object> mapVo);

	String queryAssLocation(Map<String, Object> mapVo);

	public Map<String, Object> queryAssLocationByCode(Map<String, Object> mapVo);

	String deleteAssLocationBatch(List<Map> listVo);


}
