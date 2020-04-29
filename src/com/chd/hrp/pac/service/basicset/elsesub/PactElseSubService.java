package com.chd.hrp.pac.service.basicset.elsesub;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.elsesub.PactElseSubDictEntity;

public interface PactElseSubService {

	String queryPactElseSub(Map<String, Object> page);

	String addPactElseSub(Map<String, Object> page);

	PactElseSubDictEntity queryPactElseSubByCode(Map<String, Object> mapVo);

	String deletePactElseSub(List<PactElseSubDictEntity> listVo);

	String updatePactElseSub(Map<String, Object> mapVo);



}
