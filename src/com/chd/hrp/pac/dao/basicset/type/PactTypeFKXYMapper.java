package com.chd.hrp.pac.dao.basicset.type;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.type.PactTypeFKXYEntity;

public interface PactTypeFKXYMapper extends SqlMapper{

	void deleteAllBatch(List<PactTypeFKXYEntity> list);

	PactTypeFKXYEntity queryPactTypeFKXYByCode(Map<String, Object> mapVo);

	PactTypeFKXYEntity queryPactTypeFKXYByName(Map<String, Object> mapVo);

	int qeuryPactTypeCode(Map<String, Object> mapVo);

}
