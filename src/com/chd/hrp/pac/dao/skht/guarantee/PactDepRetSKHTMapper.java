package com.chd.hrp.pac.dao.skht.guarantee;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.guarantee.PactDepRetSKHTEntity;

public interface PactDepRetSKHTMapper extends SqlMapper {


	void updateState(Map<String, Object> map);

	String queryMaxId(Map<String, Object> mapVo);

	void updatePactDepRecSKHT(Map<String, Object> mapVo);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	void deleteAllBatch(List<PactDepRetSKHTEntity> listVo);

	List<Map<String, Object>> queryForPrint(Map<String, Object> entityMap);

	Map<String, Object> queryPactSKHTForRet(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactSKHTSelectForRet(Map<String, Object> mapVo);

}
