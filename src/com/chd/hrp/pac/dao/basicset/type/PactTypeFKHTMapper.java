package com.chd.hrp.pac.dao.basicset.type;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.type.PactTypeFKHTEntity;

public interface PactTypeFKHTMapper extends SqlMapper{

	void deleteAllBatch(List<PactTypeFKHTEntity> list);

	PactTypeFKHTEntity queryPactTypeFKHTByCode(Map<String, Object> mapVo);

	PactTypeFKHTEntity queryPactTypeFKHTByName(Map<String, Object> mapVo);

	int qeuryPactTypeCode(Map<String, Object> mapVo);

}
