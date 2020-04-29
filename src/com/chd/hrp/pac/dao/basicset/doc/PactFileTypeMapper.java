package com.chd.hrp.pac.dao.basicset.doc;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactFileTypeEntity;

public interface PactFileTypeMapper extends SqlMapper{

	void deleteAllBatch(List<PactFileTypeEntity> list);

	PactFileTypeEntity queryPactFileTypeByCode(Map<String, Object> mapVo);

	PactFileTypeEntity queryPactFileTypeByName(Map<String, Object> mapVo);

	List<PactFileTypeEntity> queryPactFileTypeForExec(Map<String, Object> page);
}
