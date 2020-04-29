package com.chd.hrp.pac.service.fkht.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkht.pactinfo.PactDetFKHTEntity;

public interface PactDetFKHTService {
	String queryPactDetFKHT(Map<String, Object> page);

	String deletePactDetFKHT(List<PactDetFKHTEntity> listVo);
	
}
