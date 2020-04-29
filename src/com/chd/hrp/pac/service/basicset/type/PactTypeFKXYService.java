package com.chd.hrp.pac.service.basicset.type;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.type.PactTypeFKXYEntity;

public interface PactTypeFKXYService {

	String queryPactTypeFKXY(Map<String, Object> mapVo);

	String deletePactTypeFKXY(List<PactTypeFKXYEntity> listVo);

	String addPactTypeFKXY(Map<String, Object> mapVo);

	PactTypeFKXYEntity queryPactTypeFKXYByCode(Map<String, Object> mapVo);

	String updatePactTypeFKXY(Map<String, Object> mapVo);

	int qeuryPactTypeCode(Map<String, Object> mapVo);

}
