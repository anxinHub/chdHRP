package com.chd.hrp.pac.service.skht.guarantee.performance;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skht.guarantee.PactDepRecSKHTEntity;

public interface PactDepRecSKHTService {

	String queryPactDepRecSKHT(Map<String, Object> mapVo);

	String addPactDepRecSKHT(Map<String, Object> mapVo);

	String deletePactDepRecSKHT(List<PactDepRecSKHTEntity> listVo);

	String checkPactDepRecSKHT(List<String> listVo, String state);

	String queryPactSKHT(Map<String, Object> mapVo);

	PactDepRecSKHTEntity queryPactDepRecByRecCode(Map<String, Object> mapVo);

	String updatePactDepRecSKHT(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactDepRecSKHTPrint(Map<String, Object> entityMap);

}
