package com.chd.hrp.pac.service.basicset.nature;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.nature.PactNatureEntity;

public interface PactNatureSKHTService {

	String querySKHTNature(Map<String, Object> page);

	String addSKHTNature(Map<String, Object> page);

	String deleteSKHTNature(List<PactNatureEntity> list);

	PactNatureEntity querySKHTNatureByCode(Map<String, Object> mapVo);

	String updateSKHTNature(Map<String, Object> mapVo);

}
