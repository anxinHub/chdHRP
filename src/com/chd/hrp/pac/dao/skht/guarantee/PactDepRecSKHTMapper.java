package com.chd.hrp.pac.dao.skht.guarantee;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.guarantee.PactDepRecSKHTEntity;

public interface PactDepRecSKHTMapper extends SqlMapper {

	void deleteAllBatch(List<PactDepRecSKHTEntity> listVo);

	void updateState(Map<String, Object> map);

	String queryMaxId(Map<String, Object> mapVo);

	void updatePactDepRecSKHT(Map<String, Object> mapVo);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	List<Map<String, Object>> queryForPrint(Map<String, Object> entityMap);

	List<PactDepRecSKHTEntity> queryPactRecDetSKHTForEdit(Map<String, Object> page);

}
