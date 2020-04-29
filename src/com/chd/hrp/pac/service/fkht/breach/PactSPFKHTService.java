package com.chd.hrp.pac.service.fkht.breach;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkht.breach.PactSPFKHTEntity;

public interface PactSPFKHTService {

	PactSPFKHTEntity queryPactSPBySPCode(Map<String, Object> mapVo);

	String queryPactSPFKHT(Map<String, Object> page);

	String addPactSPFKHT(Map<String, Object> mapVo);

	String updatePactSPFKHT(Map<String, Object> mapVo);

	String deletePactSPFKHT(List<PactSPFKHTEntity> listVo);

	String checkPactSPFKHT(List<PactSPFKHTEntity> listVo, String state);

	List<Map<String, Object>> queryPactSPFKHTPrint(Map<String, Object> entityMap);

}
