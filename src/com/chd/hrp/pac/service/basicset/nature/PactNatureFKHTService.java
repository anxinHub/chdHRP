package com.chd.hrp.pac.service.basicset.nature;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.nature.PactNatureEntity;

public interface PactNatureFKHTService {

	String queryFKHTNature(Map<String, Object> page);

	String addFKHTNature(Map<String, Object> page);

	String deleteFKHTNature(List<PactNatureEntity> list);

	PactNatureEntity queryFKHTNatureByCode(Map<String, Object> mapVo);

	String updateFKHTNature(Map<String, Object> mapVo);


}
