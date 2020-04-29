package com.chd.hrp.pac.service.basicset.type;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.type.PactTypeSKHTEntity;

public interface PactTypeSKHTService {

	String queryPactTypeSKHT(Map<String, Object> mapVo);

	String deletePactTypeSKHT(List<PactTypeSKHTEntity> listVo);

	String addPactTypeSKHT(Map<String, Object> mapVo);

	PactTypeSKHTEntity queryPactTypesSKHTByCode(Map<String, Object> mapVo);

	String updatePactTypeSKHT(Map<String, Object> mapVo);

	int qeuryPactTypeCode(Map<String, Object> mapVo);
}
