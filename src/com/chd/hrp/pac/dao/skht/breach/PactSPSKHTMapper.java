package com.chd.hrp.pac.dao.skht.breach;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.breach.PactSPSKHTEntity;

public interface PactSPSKHTMapper extends SqlMapper {

	void deleteAllBatch(List<PactSPSKHTEntity> listVo);

	void updateState(Map<String, Object> map);

	List<Map<String, Object>> queryForPrint(Map<String, Object> entityMap);

}
