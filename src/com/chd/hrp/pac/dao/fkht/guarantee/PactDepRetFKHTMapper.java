package com.chd.hrp.pac.dao.fkht.guarantee;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.guarantee.PactDepRetFKHTEntity;

public interface PactDepRetFKHTMapper extends SqlMapper {


	void updateState(Map<String, Object> map);

	void updatePactDepRecFKHT(Map<String, Object> mapVo);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	void deleteAllBatch(List<PactDepRetFKHTEntity> listVo);

	List<Map<String, Object>> queryForPrint(Map<String, Object> entityMap);

	Map<String, Object> queryPactFKHTForRet(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactFKHTSelectForRet(Map<String, Object> mapVo);

}
