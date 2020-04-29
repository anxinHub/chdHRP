package com.chd.hrp.pac.service.fkht.guarantee.performance;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkht.guarantee.PactDepRecFKHTEntity;

public interface PactDepRecFKHTService {

	String queryPactDepRecFKHT(Map<String, Object> mapVo);

	String addPactDepRecFKHT(Map<String, Object> mapVo);

	String deletePactDepRecFKHT(List<PactDepRecFKHTEntity> listVo);

	String checkPactDepRecFKHT(List<String> listVo, String state);

	String queryPactFKHT(Map<String, Object> mapVo);

	PactDepRecFKHTEntity queryPactDepRecByRecCode(Map<String, Object> mapVo);

	String updatePactDepRecFKHT(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactDepRecFKHTPrint(Map<String, Object> entityMap);

}
