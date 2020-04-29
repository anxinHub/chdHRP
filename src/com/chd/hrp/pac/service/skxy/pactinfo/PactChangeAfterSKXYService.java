package com.chd.hrp.pac.service.skxy.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactDetSKXYEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;

public interface PactChangeAfterSKXYService {
	
	String addPactSKXYC(Map<String, Object> mapVo,String pact_type_code);

	String deletePactSKXYC(List<Map<String, Object>> listVo);
	
	void deleteChangeAndCopy(List<Map<String, Object>> listMap, String pact_type_code);
	
	String checkPactMainSKXYC(List<Map<String, Object>> listVo, String check, String is_init);
	
	PactMainSKXYEntity queryPactSKXYCByChangeCode(Map<String, Object> mapVo);
	
	String updatePactSKXYC(Map<String, Object> mapVo);
	
	String deletePactDetSKXYC(List<PactDetSKXYEntity> listVo);
	
}
