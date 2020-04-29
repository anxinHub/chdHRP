package com.chd.hrp.pac.dao.basicset.elsesub;


import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.elsesub.PactElseSubDictEntity;

public interface PactElseSubDictMapper extends SqlMapper{

	PactElseSubDictEntity queryPactElseSubByCode(Map<String, Object> mapVo);

	void addNew(Map<String, Object> mapVo);

	void deleteAllBatch(Map<String, Object> map);

}
