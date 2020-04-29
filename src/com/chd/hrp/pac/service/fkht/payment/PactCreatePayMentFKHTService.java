package com.chd.hrp.pac.service.fkht.payment;

import java.util.List;
import java.util.Map;

public interface PactCreatePayMentFKHTService {

	String queryCreatPactPayMainFKHT(Map<String, Object> mapVo);

	String addCreatPactPayMainFKHT(List<Map<String, Object>> listVo);

	List<Map<String, Object>> queryCreatPactPayMainFKHTPrint(Map<String, Object> mapVo);

}
