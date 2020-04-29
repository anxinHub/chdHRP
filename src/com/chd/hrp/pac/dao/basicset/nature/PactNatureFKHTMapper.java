package com.chd.hrp.pac.dao.basicset.nature;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.nature.PactNatureEntity;

public interface PactNatureFKHTMapper extends SqlMapper{

	void deleteAllBatch(List<PactNatureEntity> list);


	PactNatureEntity queryFKHTNatureByCode(Map<String, Object> mapVo);


	PactNatureEntity queryFKHTNatureByName(Map<String, Object> mapVo);


	List<Map<String, Object>> querySelcetFKHTNature(Map<String, Object> mapVo);

}
