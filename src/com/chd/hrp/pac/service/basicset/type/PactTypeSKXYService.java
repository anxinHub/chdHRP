package com.chd.hrp.pac.service.basicset.type;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.type.PactTypeSKXYEntity;

public interface PactTypeSKXYService {

	String queryPactTypeSKXY(Map<String, Object> mapVo);

	String deletePactTypeSKXY(List<PactTypeSKXYEntity> listVo);

	String addPactTypeSKXY(Map<String, Object> mapVo);

	PactTypeSKXYEntity queryPactTypeSKXYByCode(Map<String, Object> mapVo);

	String updatePactTypeSKXY(Map<String, Object> mapVo);

	int qeuryPactTypeCode(Map<String, Object> mapVo);
}
