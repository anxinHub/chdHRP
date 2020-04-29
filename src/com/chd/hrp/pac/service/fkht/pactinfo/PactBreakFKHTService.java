package com.chd.hrp.pac.service.fkht.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkht.breach.PactBreakFKHTEntity;

public interface PactBreakFKHTService {

	PactBreakFKHTEntity queryPactBreakByBreakCode(Map<String, Object> mapVo);

	String queryPactBreakFKHT(Map<String, Object> page);

	String addPactBreakFKHT(Map<String, Object> mapVo);

	String updatePactBreakFKHT(Map<String, Object> mapVo);

	String deletePactBreakFKHT(List<PactBreakFKHTEntity> listVo);

	String checkPactBreakFKHT(List<PactBreakFKHTEntity> listVo, String state);

}
