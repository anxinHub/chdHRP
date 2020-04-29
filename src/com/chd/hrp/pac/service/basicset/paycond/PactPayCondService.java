package com.chd.hrp.pac.service.basicset.paycond;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.paycond.PactPayCondEntity;

public interface PactPayCondService {

	String queryPactPayCond(Map<String, Object> page);

	String addPactPayCond(Map<String, Object> page);

	PactPayCondEntity queryPactPayCondByCode(Map<String, Object> mapVo);

	String deletePactPayCond(List<PactPayCondEntity> mapVo);

	String updatePactPayCond(Map<String, Object> mapVo);

}
