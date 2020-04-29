package com.chd.hrp.pac.dao.basicset.type;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.type.PactTypeSKXYEntity;

public interface PactTypeSKXYMapper extends SqlMapper {

	void deleteAllBatch(List<PactTypeSKXYEntity> list);

	PactTypeSKXYEntity queryPactTypeSKXYByCode(Map<String, Object> mapVo);

	PactTypeSKXYEntity queryPactTypeSKXYByName(Map<String, Object> mapVo);

	int qeuryPactTypeCode(Map<String, Object> mapVo);
}
