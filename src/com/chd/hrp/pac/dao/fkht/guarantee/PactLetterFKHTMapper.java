package com.chd.hrp.pac.dao.fkht.guarantee;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.guarantee.PactLetterFKHTEntity;

public interface PactLetterFKHTMapper extends SqlMapper {


	void updateState(Map<String, Object> map);

	void updatePactDepRecFKHT(Map<String, Object> mapVo);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	void deleteAllBatch(List<PactLetterFKHTEntity> listVo);

	Map<String, Object> queryPactBankDetailInfo(Map<String, Object> mapVo);

	List<Map<String, Object>> queryForPrint(Map<String, Object> entityMap);


}
