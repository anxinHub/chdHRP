package com.chd.hrp.pac.service.fkht.guarantee;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkht.guarantee.PactLetterFKHTEntity;

public interface PactLetterFKHTService {

	PactLetterFKHTEntity queryPactLetterByLetterCode(Map<String, Object> mapVo);

	String queryPactLetterFKHT(Map<String, Object> page);

	String addPactLetterFKHT(Map<String, Object> mapVo);

	String updatePactLetterFKHT(Map<String, Object> mapVo);

	String deletePactLetterFKHT(List<PactLetterFKHTEntity> listVo);

	String checkPactLetterFKHT(List<PactLetterFKHTEntity> listVo, String state);

	String queryPactBankDetailInfo(Map<String, Object> mapVo);

	String queryPactFKHTSelectForLetter(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactLetterFKHTPrint(Map<String, Object> entityMap);

}
