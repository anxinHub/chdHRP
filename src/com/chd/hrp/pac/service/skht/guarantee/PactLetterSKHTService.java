package com.chd.hrp.pac.service.skht.guarantee;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skht.guarantee.PactLetterSKHTEntity;

public interface PactLetterSKHTService {

	PactLetterSKHTEntity queryPactLetterByLetterCode(Map<String, Object> mapVo);

	String queryPactLetterSKHT(Map<String, Object> page);

	String addPactLetterSKHT(Map<String, Object> mapVo);

	String updatePactLetterSKHT(Map<String, Object> mapVo);

	String deletePactLetterSKHT(List<PactLetterSKHTEntity> listVo);

	String checkPactLetterSKHT(List<PactLetterSKHTEntity> listVo, String state);

	String queryPactBankDetailInfo(Map<String, Object> mapVo);

	String queryPactSKHTSelectForLetter(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactLetterSKHTPrint(Map<String, Object> entityMap);

}
