package com.chd.hrp.pac.service.skht.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skht.pactinfo.PactDetSKHTEntity;

public interface PactDetSKHTService {
	String queryPactDetSKHT(Map<String, Object> page);

	String deletePactDetSKHT(List<PactDetSKHTEntity> listVo);
	
}
