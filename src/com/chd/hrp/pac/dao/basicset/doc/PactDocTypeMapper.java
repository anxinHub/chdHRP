package com.chd.hrp.pac.dao.basicset.doc;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocTypeEntity;

public interface PactDocTypeMapper extends SqlMapper{

	void deleteAllBatch(List<PactDocTypeEntity> list);

	PactDocTypeEntity queryPactDocTypeByCode(Map<String, Object> mapVo);

	PactDocTypeEntity queryPactDocTypeByName(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactDocTypeSelect(Map<String, Object> mapVo);
}
