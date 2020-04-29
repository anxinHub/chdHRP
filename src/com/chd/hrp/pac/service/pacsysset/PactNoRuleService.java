package com.chd.hrp.pac.service.pacsysset;

import java.util.Map;

public interface PactNoRuleService {

	public String queryAllPactNoRule(Map<String, Object> map);

	public String updatePactNoRule(Map<String, Object> mapVo);

	public String getNo(String tableCode, Map<String, Object> mapVo);
}
