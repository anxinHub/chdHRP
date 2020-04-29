package com.chd.hrp.pac.service.fkxy.logistics;

import java.util.List;
import java.util.Map;

public interface PactLogisticsProtocolService {

	String queryPactProtocalSummaryFKXY(Map<String, Object> mapVo);
	
	List<Map<String,Object>> queryPactProtocalSummaryFKXYPrint(Map<String, Object> mapVo);

	String queryPactProtocolDetailFKXY(Map<String, Object> mapVo);
	
	List<Map<String,Object>> queryPactProtocolDetailFKXYPrint(Map<String, Object> mapVo);


}
