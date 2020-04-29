package com.chd.hrp.pac.service.fkxy.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;

public interface PactChangeAfterFKXYService {
	
	String addPactFKXYC(Map<String, Object> mapVo,String pact_type_code);

	String deletePactFKXYC(List<Map<String, Object>> listVo);
	
	void deleteChangeAndCopy(List<Map<String, Object>> listMap, String pact_type_code);
	
	String checkPactMainFKXYC(List<Map<String, Object>> listVo, String check, String is_init);
	
	PactMainFKXYEntity queryPactFKXYCByChangeCode(Map<String, Object> mapVo);
	
	String updatePactFKXYC(Map<String, Object> mapVo);
	
	String deletePactDetFKXYC(List<PactDetFKXYEntity> listVo);
	
}
