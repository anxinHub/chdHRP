package com.chd.hrp.pac.service.skht.breach;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skht.breach.PactBreakSKHTEntity;

public interface PactBreakSKHTService {

	PactBreakSKHTEntity queryPactBreakByBreakCode(Map<String, Object> mapVo);

	String queryPactBreakSKHT(Map<String, Object> page);

	String addPactBreakSKHT(Map<String, Object> mapVo);

	String updatePactBreakSKHT(Map<String, Object> mapVo);

	String deletePactBreakSKHT(List<PactBreakSKHTEntity> listVo);

	String checkPactBreakSKHT(List<PactBreakSKHTEntity> listVo, String state);

	List<Map<String, Object>> queryPactBreakSKHTPrint(Map<String, Object> entityMap);

}
