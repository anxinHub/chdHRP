package com.chd.hrp.pac.dao.skht.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.pactinfo.PactDetSourSKHTEntity;

public interface PactDetSourSKHTMapper extends SqlMapper{


	void deleteAllBatch(List<PactDetSourSKHTEntity> list);

	void deleteByPactCode(Map<String, Object> entityMap);
	
	void deleteByDetailId(List<Map<String, Object>> list);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

}
