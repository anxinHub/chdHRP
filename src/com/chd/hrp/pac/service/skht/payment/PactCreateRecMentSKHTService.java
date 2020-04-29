package com.chd.hrp.pac.service.skht.payment;

import java.util.List;
import java.util.Map;

public interface PactCreateRecMentSKHTService {

	String queryCreatPactRecMainSKHT(Map<String, Object> mapVo);

	String addCreatPactRecMainSKHT(List<Map<String, Object>> listVo);

	List<Map<String, Object>> queryCreatPactRecMainSKHTPrint(Map<String, Object> mapVo);

}
