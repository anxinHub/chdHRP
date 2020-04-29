package com.chd.hrp.pac.service.basicset.type;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.type.PactTypeFKHTEntity;

public interface PactTypeFKHTService {

	String queryPactTypeFKHT(Map<String, Object> mapVo);

	String deletePactTypeFKHT(List<PactTypeFKHTEntity> listVo);

	String addPactTypeFKHT(Map<String, Object> mapVo);

	String updatePactTypeFKHT(Map<String, Object> mapVo);

	PactTypeFKHTEntity queryPactTypeFKHTByCode(Map<String, Object> mapVo);

	int qeuryPactTypeCode(Map<String, Object> mapVo);

}
