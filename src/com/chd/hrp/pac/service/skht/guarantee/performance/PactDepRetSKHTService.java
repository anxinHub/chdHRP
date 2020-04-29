package com.chd.hrp.pac.service.skht.guarantee.performance;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skht.guarantee.PactDepRetSKHTEntity;

public interface PactDepRetSKHTService {

	PactDepRetSKHTEntity queryPactDepRetByRetCode(Map<String, Object> mapVo);

	String queryPactDepRetSKHT(Map<String, Object> page);

	String addPactDepRetSKHT(Map<String, Object> mapVo);

	String updatePactDepRetSKHT(Map<String, Object> mapVo);

	String deletePactDepRetSKHT(List<PactDepRetSKHTEntity> listVo);

	String checkPactDepRetSKHT(List<String> listVo, String state);

	List<Map<String, Object>> queryPactDepRetSKHTPrint(Map<String, Object> entityMap);

	String queryPactSKHTForRet(Map<String, Object> mapVo);

	String queryPactSKHTSelectForRet(Map<String, Object> mapVo);

}
