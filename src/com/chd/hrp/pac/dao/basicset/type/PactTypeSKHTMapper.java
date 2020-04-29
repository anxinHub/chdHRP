package com.chd.hrp.pac.dao.basicset.type;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.type.PactTypeSKHTEntity;

public interface PactTypeSKHTMapper extends SqlMapper{

	void deleteAllBatch(List<PactTypeSKHTEntity> list);

	PactTypeSKHTEntity queryPactTypesSKHTByCode(Map<String, Object> mapVo);

	PactTypeSKHTEntity queryPactTypesSKHTByName(Map<String, Object> mapVo);

	int qeuryPactTypeCode(Map<String, Object> mapVo);
}
