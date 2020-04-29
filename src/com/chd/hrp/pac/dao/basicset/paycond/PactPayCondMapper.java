package com.chd.hrp.pac.dao.basicset.paycond;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.paycond.PactPayCondEntity;

public interface PactPayCondMapper extends SqlMapper{

	void deleteAllBatch(List<PactPayCondEntity> list);

	PactPayCondEntity queryPactPayCondByCode(Map<String, Object> mapVo);

	PactPayCondEntity queryPactPayCondByName(Map<String, Object> mapVo);

}
