package com.chd.hrp.pac.dao.fkht.breach;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.breach.PactSPFKHTEntity;

public interface PactSPFKHTMapper extends SqlMapper {

	void deleteAllBatch(List<PactSPFKHTEntity> listVo);

	void updateState(Map<String, Object> map);

	List<Map<String, Object>> queryForPrint(Map<String, Object> entityMap);

}
