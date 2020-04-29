package com.chd.hrp.acc.service.vouch;

import java.util.Map;

public interface AccDifferSubjService {

	String queryAccDifferSubj(Map<String, Object> page);

	String addAccDifferSubj(Map<String, Object> mapVo);

	String deleteAccDifferSubj(String mapVo);

	String queryAccDifferSubjForAdd(Map<String, Object> page);

}
