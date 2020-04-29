package com.chd.hrp.pac.service.fkht.guarantee.performance;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkht.guarantee.PactDepRetFKHTEntity;

public interface PactDepRetFKHTService {

	PactDepRetFKHTEntity queryPactDepRetByRetCode(Map<String, Object> mapVo);

	String queryPactDepRetFKHT(Map<String, Object> page);

	String addPactDepRetFKHT(Map<String, Object> mapVo);

	String updatePactDepRetFKHT(Map<String, Object> mapVo);

	String deletePactDepRetFKHT(List<PactDepRetFKHTEntity> listVo);

	String checkPactDepRetFKHT(List<String> listVo, String state);

	List<Map<String, Object>> queryPactDepRetFKHTPrint(Map<String, Object> entityMap);

	String queryPactFKHTForRet(Map<String, Object> mapVo);

	String queryPactFKHTSelectForRet(Map<String, Object> mapVo);

}
