package com.chd.hrp.pac.dao.skht.guarantee;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.guarantee.PactLetterSKHTEntity;

public interface PactLetterSKHTMapper extends SqlMapper {


	void updateState(Map<String, Object> map);

	void updatePactDepRecSKHT(Map<String, Object> mapVo);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	void deleteAllBatch(List<PactLetterSKHTEntity> listVo);

	Map<String, Object> queryPactBankDetailInfo(Map<String, Object> mapVo);

	List<Map<String, Object>> queryForPrint(Map<String, Object> entityMap);


}
