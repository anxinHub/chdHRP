package com.chd.hrp.acc.service.vouch;

import java.util.Map;

public interface AccDifferService {

	Map<String, Object> queryAccDifferItemByCode(Map<String, Object> mapVo);

	String queryAccDifferItem(Map<String, Object> page);

	String addAccDifferItem(Map<String, Object> mapVo);

	String deleteAccDifferItem(String mapVo);

	String updateAccDifferItem(Map<String, Object> mapVo);

	String queryAccDifferType(Map<String, Object> mapVo);

	String addAccDifferType(Map<String, Object> mapVo);

	String deleteAccDifferType(Map<String, Object> mapVo);

	Map<String, Object> queryAccDifferTypeByCode(Map<String, Object> mapVo);

	String updateAccDifferType(Map<String, Object> mapVo);

	String queryDifferItemTree(Map<String, Object> mapVo);

}
