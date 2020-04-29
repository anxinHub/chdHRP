package com.chd.hrp.pac.service.skht.breach;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skht.breach.PactSPSKHTEntity;

public interface PactSPSKHTService {

	PactSPSKHTEntity queryPactSPBySPCode(Map<String, Object> mapVo);

	String queryPactSPSKHT(Map<String, Object> page);

	String addPactSPSKHT(Map<String, Object> mapVo);

	String updatePactSPSKHT(Map<String, Object> mapVo);

	String deletePactSPSKHT(List<PactSPSKHTEntity> listVo);

	String checkPactSPSKHT(List<PactSPSKHTEntity> listVo, String state);

	List<Map<String, Object>> queryPactSPSKHTPrint(Map<String, Object> entityMap);

}
