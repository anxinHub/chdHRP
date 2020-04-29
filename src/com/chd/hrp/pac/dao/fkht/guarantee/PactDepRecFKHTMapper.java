package com.chd.hrp.pac.dao.fkht.guarantee;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.guarantee.PactDepRecFKHTEntity;

public interface PactDepRecFKHTMapper extends SqlMapper {

	void deleteAllBatch(List<PactDepRecFKHTEntity> listVo);

	void updateState(Map<String, Object> map);

	void updatePactDepRecFKHT(Map<String, Object> mapVo);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	List<Map<String, Object>> queryForPrint(Map<String, Object> entityMap);

}
