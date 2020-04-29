package com.chd.hrp.acc.service.vouch;

import java.util.List;
import java.util.Map;

public interface AccVouchDifferService {

	String queryAccDifferQuery(Map<String, Object> page);
	
	List<Map<String,Object>> queryAccDifferQueryPrint(Map<String, Object> page);

}
