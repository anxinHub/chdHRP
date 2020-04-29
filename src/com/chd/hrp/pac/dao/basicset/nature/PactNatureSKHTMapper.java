package com.chd.hrp.pac.dao.basicset.nature;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.nature.PactNatureEntity;

public interface PactNatureSKHTMapper extends SqlMapper {

	void deleteAllBatch(List<PactNatureEntity> list);

	PactNatureEntity querySKHTNatureByCode(Map<String, Object> mapVo);

	PactNatureEntity querySKHTNatureByName(Map<String, Object> mapVo);

}
